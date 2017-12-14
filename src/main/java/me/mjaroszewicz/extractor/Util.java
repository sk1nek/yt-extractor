package me.mjaroszewicz.extractor;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility static methods.
 */
class Util {

    /**
     *
     * @param pattern Regex pattern
     * @param input String to be processed
     * @param group Input subsequence number. For details, look at this -> {@link Matcher#group()}
     * @return Match if it was found, null otherwise
     */
    static String matchGroup(String pattern, String input, int group){
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(input);

        if(mat.find())
            return mat.group(group);
        else
            return null;
    }

    /**
     * @param input - Param string
     * @return Map of 'Compatibility tags' including itag id, quality, video encoding and url
     */
    static HashMap<String, String> parseCompatTypeMap(String input) throws YoutubeExtractionException{
        HashMap<String, String> ret = new HashMap<>();

        try{
            for (String arg : Arrays.stream(input.split("&")).dropWhile(String::isEmpty).collect(Collectors.toList())) {
                ArrayList<String> splitArg = new ArrayList<>(Arrays.stream(arg.split("=")).dropWhile(String::isEmpty).collect(Collectors.toList()));
                if(splitArg.size() > 1)
                    ret.put(splitArg.get(0), URLDecoder.decode(splitArg.get(1), "UTF-8"));
                else
                    ret.put(splitArg.get(0), "");
            }
        }catch(Throwable t){
            t.printStackTrace();
            throw new YoutubeExtractionException(t.getMessage());
        }

        return ret;
    }

    /**
     *
     * @param url URL
     * @return String representation of html body
     */
    static String getContentByUrl(String url) throws YoutubeExtractionException{
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI(url)).build();
            return httpClient.send(request, HttpResponse.BodyHandler.asString()).body();
        }catch(Throwable t){
            throw new YoutubeExtractionException("Could not fetch content from url: " + t.getMessage());
        }
    }


}
