package me.mjaroszewicz.extractor;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

/**
 * Javascript helper class used for signature decryption via use of javascript.
 * Uses Mozilla Rhino engine.
 */
class JsUtil {

    static String loadDecryptionCode(String playerCode){
        String decryptionFunctionName = Util.matchGroup("([\"\\'])signature\\1\\s*,\\s*([a-zA-Z0-9$]+)\\(", playerCode, 2);
        String callerFunction = "function decrypt(a){return XJ(a);}";

        String functionPattern = "("
                + decryptionFunctionName
                + "=function\\([a-zA-Z0-9_]+\\)\\{.+?\\})";

        String decryptionFunction = "var "+ Util.matchGroup(functionPattern, playerCode, 1) + ";";
        String helperObjectName = Util.matchGroup(";([A-Za-z0-9_\\$]{2})\\...\\(", decryptionFunction, 1);

        String helperPattern = "(var " + helperObjectName.replaceAll("\\$", "\\$") + "=\\{.+?\\}\\};)";
        String helperObject = Util.matchGroup(helperPattern, playerCode, 1);

        callerFunction = callerFunction.replace("$$", decryptionFunctionName);
        return helperObject + decryptionFunction + callerFunction;
    }

    static String decryptSignature(String encrypted, String code){
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        String result = "";

        try{
            ScriptableObject scope = context.initStandardObjects();
            context.evaluateString(scope, code, "decryptionCode", 1, null);
            Function decryptionFunction = (Function) scope.get("decrypt", scope);
            result = (String) decryptionFunction.call(context, scope, scope, new String[]{encrypted});
        }catch(Throwable t){
            t.printStackTrace();
        }finally {
            Context.exit();
        }

        return result;
    }
}
