package me.mjaroszewicz.extractor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.parser.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.stream.Collectors;


public class YoutubeExtractor {

    private final static String BASE_URL = "https://www.youtube.com";

    //regex pattern of player config, which itself is located in one of the <script> tags
    private final static String JSON_PLAYER_CONFIG_REGEX = "ytplayer.config\\s*=\\s*(\\{.*?\\});";

    public Extraction extract(String videoId) throws YoutubeExtractionException{

        //to be returned
        Extraction ret = new Extraction();

        try{
            String url = BASE_URL + "/watch?v=" + videoId;
            String pageContent = Util.getContentByUrl(url);

            //extract serialized config
            String playerConfigJson = Util.matchGroup(JSON_PLAYER_CONFIG_REGEX, pageContent, 1);

            if(playerConfigJson.equals(""))
                throw new YoutubeExtractionException("Could not fetch config");

            //deserializing player config
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode configNode = objectMapper.readTree(playerConfigJson);
            PlayerConfig playerConfig = mapPlayerConfig(configNode);

            String playerUrl = formatPlayerUrl(playerConfig);

            //Passing title to returned object
            ret.setTitle(playerConfig.getPlayerArgs().getTitle());

            String encodedUrlMap = playerConfig.getPlayerArgs().getUrlEncodedFmtStreamMap();

            //stream url data to be parsed
            ArrayList<String> streamUrlDataArray = new ArrayList<>(Arrays.stream(encodedUrlMap.split(",")).filter(p -> !p.isEmpty()).collect(Collectors.toList()));

            //stream collections
            HashMap<String, Itag> streams = new HashMap<>();

            streamUrlDataArray.parallelStream().forEach(p->{
                try{
                    HashMap<String, String> tags = Util.parseCompatTypeMap(Parser.unescapeEntities(p, true));

                    Integer itag = Integer.parseInt(tags.get("itag"));

                    if(Itag.isSupported(itag)){
                        Itag itagItem = Itag.getItag(itag);
                        String streamUrl = tags.get("url");
                        String signature = tags.get("s");

                        //decrypt signature
                        if(signature!= null){
                            String playerCode = Util.getContentByUrl(playerUrl).replace("\n", "");
                            streamUrl = streamUrl + "&signature=" + JsUtil.decryptSignature(signature, JsUtil.loadDecryptionCode(playerCode));
                        }

                        //collect stream url
                        if(streamUrl != null){
                            streams.put(streamUrl, itagItem);
                        }
                    }
                }catch(Throwable t){
                    t.printStackTrace();
                }
            });

            //Passing URL collection to returned object
            ret.setStreams(streams);
        }catch(Throwable t){
            t.printStackTrace();
            throw new YoutubeExtractionException(t.getMessage());
        }

        return ret;
    }

    private String formatPlayerUrl(PlayerConfig pc){
        String url = pc.getAssets();

        if(url.startsWith("//"))
            url = "https:" + url;
        if(!url.startsWith(BASE_URL))
            url = BASE_URL + url;

        return url;
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
