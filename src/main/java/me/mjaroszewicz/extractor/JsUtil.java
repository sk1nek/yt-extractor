package me.mjaroszewicz.extractor;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

/**
 * Javascript helper class used for signature decryption via use of javascript.
 * Uses Mozilla Rhino engine.
 */
class JsUtil {

    /**
     * @param playerCode JavaScript player code
     * @return Decryption function represented as String
     */
    static String loadDecryptionCode(String playerCode) throws YoutubeExtractionException{

        String decryptionFunctionName = Util.matchGroup("([\"\\'])signature\\1\\s*,\\s*([a-zA-Z0-9$]+)\\(", playerCode, 2);
        String callerFunction = "function decrypt(a){return "+ decryptionFunctionName +"(a);}";
        String functionPattern = ("("
                + decryptionFunctionName.replace("$", "\\$")
                + "=function\\([a-zA-Z0-9_]+\\)\\{.+?\\})");

        String decryptionFunction = "var "+ Util.matchGroup(functionPattern, playerCode, 1) + ";";

        if(decryptionFunction.equals("var null;"))
            throw new YoutubeExtractionException("Could not find decryption function in player code");

        String helperObjectName = Util.matchGroup(";([A-Za-z0-9_\\$]{2})\\...\\(", decryptionFunction, 1);

        String helperPattern = "(var " + helperObjectName.replaceAll("\\$", "\\$") + "=\\{.+?\\}\\};)";
        String helperObject = Util.matchGroup(helperPattern, playerCode, 1);

        callerFunction = callerFunction.replace("$$", decryptionFunctionName);
        return helperObject + decryptionFunction + callerFunction;
    }

    /**
     *
     * @param encrypted String containing encrypted signature
     * @param code JavaScript code of decryption function
     * @return Decrypted stream signature
     */
    static String decryptSignature(String encrypted, String code) throws YoutubeExtractionException{
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        String result;

        try{
            ScriptableObject scope = context.initStandardObjects();
            context.evaluateString(scope, code, "decryptionCode", 1, null);
            Function decryptionFunction = (Function) scope.get("decrypt", scope);
            result = (String) decryptionFunction.call(context, scope, scope, new String[]{encrypted}); //calling decryption function
        }catch(Throwable t){
            t.printStackTrace();
            throw new YoutubeExtractionException(t.getMessage());
        }finally {
            Context.exit();
        }

        return result;
    }
}
