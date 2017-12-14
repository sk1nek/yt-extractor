package me.mjaroszewicz.extractor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.jsoup.parser.Parser;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;


public class YoutubeExtractor {

    private final static String BASE_URL = "https://www.youtube.com";

    private final static String JSON_PLAYER_CONFIG_REGEX = "ytplayer.config\\s*=\\s*(\\{.*?\\});";


    public String extract(String videoId){
        try{
            String url = BASE_URL + "/watch?v=" + videoId;
            String pageContent = downloadPage(url);

            String playerConfigJson = Util.matchGroup(JSON_PLAYER_CONFIG_REGEX, pageContent, 1);

            if(playerConfigJson.equals(""))
                throw new Exception("Could not fetch config");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(playerConfigJson);
            PlayerConfig pc = mapPlayerConfig(obj);


            String playerUrl = formatPlayerUrl(pc);

            String encodedUrlMap = pc.getPlayerArgs().getUrlEncodedFmtStreamMap();

            ArrayList<String> streamUrlData = new ArrayList<>(Arrays.stream(encodedUrlMap.split(",")).filter(p -> !p.isEmpty()).collect(Collectors.toList()));

            for(String s: streamUrlData){
                HashMap<String, String> tags = Util.compatParseMap(Parser.unescapeEntities(s, true));

                Integer itag = Integer.parseInt(tags.get("itag"));

                if(Itag.isSupported(itag)){
                    Itag itagItem = Itag.getItag(itag);
                    String streamUrl = tags.get("url");
                    String signature = tags.get("s");
                    if(signature!= null){
                        String playerCode = Util.getContentByUrl(playerUrl).replace("\n", "");
                        streamUrl = streamUrl + "&signature=" + JsUtil.decryptSignature(signature, JsUtil.loadDecryptionCode(playerCode));

                    }
                    if(streamUrl != null){
                        return streamUrl;
                    }
                }

            }
        }catch(Throwable t){
            t.printStackTrace();
        }

        return "";
    }

    HashMap<String, String> parseStreams(PlayerConfig pc, String playerUrl){
        return new HashMap<>();
    }

    private String formatPlayerUrl(PlayerConfig pc){
        String url = pc.getAssets();

        if(url.startsWith("//"))
            url = "https:" + url;
        if(!url.startsWith(BASE_URL))
            url = BASE_URL + url;

        return url;
    }

    private String downloadPage(String url){

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
            return response.body();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }


    private Args mapArgs(JsonNode obj){
        JsonNode argsNode = obj.get("args");
        Args args = new Args();
        args.setTitle(argsNode.get("title").asText());
        args.setUrlEncodedFmtStreamMap(argsNode.get("url_encoded_fmt_stream_map").asText());

        return args;
    }

    private String mapAssets(JsonNode obj){
        return obj.get("assets").get("js").asText();
    }

    private PlayerConfig mapPlayerConfig(JsonNode obj){
        PlayerConfig ret = new PlayerConfig();

        Args args = mapArgs(obj);
        String assets = mapAssets(obj);

        ret.setPlayerArgs(args);
        ret.setAssets(assets);

        return ret;
    }




}
