package me.mjaroszewicz.extractor;

import java.util.Map;

public class Itag {

    enum Params {
        FORMAT_v3GPP("v3GPP"),
        FORMAT_MPEG4("MPEG4"),
        FORMAT_WEBM("WEBM"),
        RESOLUTION_144p("144p"),
        RESOLUTION_240p("240p"),
        RESOLUTION_360p("360p"),
        RESOLUTION_480p("480p"),
        RESOLUTION_720p("720p"),
        RESOLUTION_720p60("720p60"),
        RESOLUTION_1080p("1080p"),
        RESOLUTION_1080p60("1080p60"),
        RESOLUTION_1440p("1440p"),
        RESOLUTION_1440p60("1440p60"),
        RESOLUTION_2160p("2160p"),
        RESOLUTION_2160p60("2160p60");

        private final String text;

        Params(final String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return text;
        }

    }


    private int id;

    private String format;

    private String resolution;

    Itag(int id, Params format, Params resolution) {
        this.id = id;
        this.format = format.text;
        this.resolution = resolution.text;
    }

    private static Map<Integer, Itag> map = Map.ofEntries(
            Map.entry(17, new Itag(17, Params.FORMAT_v3GPP, Params.RESOLUTION_144p)),
            Map.entry(18, new Itag(18, Params.FORMAT_MPEG4, Params.RESOLUTION_360p)),
            Map.entry(34, new Itag(18, Params.FORMAT_MPEG4, Params.RESOLUTION_360p)),
            Map.entry(35, new Itag(18, Params.FORMAT_MPEG4, Params.RESOLUTION_480p)),
            Map.entry(36, new Itag(36, Params.FORMAT_v3GPP, Params.RESOLUTION_240p)),
            Map.entry(59, new Itag(59, Params.FORMAT_MPEG4, Params.RESOLUTION_480p)),
            Map.entry(78, new Itag(78, Params.FORMAT_MPEG4, Params.RESOLUTION_480p)),
            Map.entry(22, new Itag(22, Params.FORMAT_MPEG4, Params.RESOLUTION_720p)),
            Map.entry(37, new Itag(37, Params.FORMAT_MPEG4, Params.RESOLUTION_1080p)),
            Map.entry(38, new Itag(38, Params.FORMAT_MPEG4, Params.RESOLUTION_1080p)),
            Map.entry(43, new Itag(43, Params.FORMAT_WEBM, Params.RESOLUTION_360p)),
            Map.entry(44, new Itag(44, Params.FORMAT_WEBM, Params.RESOLUTION_480p)),
            Map.entry(45, new Itag(45, Params.FORMAT_WEBM, Params.RESOLUTION_720p)),
            Map.entry(46, new Itag(46, Params.FORMAT_WEBM, Params.RESOLUTION_1080p)),
            Map.entry(160, new Itag(160, Params.FORMAT_MPEG4, Params.RESOLUTION_144p)),
            Map.entry(133, new Itag(133, Params.FORMAT_MPEG4, Params.RESOLUTION_240p)),
            Map.entry(134, new Itag(134, Params.FORMAT_MPEG4, Params.RESOLUTION_360p)),
            Map.entry(135, new Itag(135, Params.FORMAT_MPEG4, Params.RESOLUTION_480p)),
            Map.entry(212, new Itag(212, Params.FORMAT_MPEG4, Params.RESOLUTION_480p)),
            Map.entry(136, new Itag(136, Params.FORMAT_MPEG4, Params.RESOLUTION_720p)),
            Map.entry(298, new Itag(298, Params.FORMAT_MPEG4, Params.RESOLUTION_720p60)),
            Map.entry(137, new Itag(137, Params.FORMAT_MPEG4, Params.RESOLUTION_1080p)),
            Map.entry(299, new Itag(299, Params.FORMAT_MPEG4, Params.RESOLUTION_1080p60)),
            Map.entry(266, new Itag(266, Params.FORMAT_MPEG4, Params.RESOLUTION_2160p)),
            Map.entry(278, new Itag(278, Params.FORMAT_WEBM, Params.RESOLUTION_144p)),
            Map.entry(242, new Itag(242, Params.FORMAT_WEBM, Params.RESOLUTION_240p)),
            Map.entry(243, new Itag(243, Params.FORMAT_WEBM, Params.RESOLUTION_360p)),
            Map.entry(244, new Itag(244, Params.FORMAT_WEBM, Params.RESOLUTION_480p)),
            Map.entry(245, new Itag(245, Params.FORMAT_WEBM, Params.RESOLUTION_480p)),
            Map.entry(246, new Itag(246, Params.FORMAT_WEBM, Params.RESOLUTION_480p)),
            Map.entry(247, new Itag(247, Params.FORMAT_WEBM, Params.RESOLUTION_720p)),
            Map.entry(248, new Itag(248, Params.FORMAT_WEBM, Params.RESOLUTION_1080p)),
            Map.entry(271, new Itag(271, Params.FORMAT_WEBM, Params.RESOLUTION_1440p)),
            Map.entry(272, new Itag(272, Params.FORMAT_WEBM, Params.RESOLUTION_2160p)),
            Map.entry(302, new Itag(302, Params.FORMAT_WEBM, Params.RESOLUTION_720p60)),
            Map.entry(303, new Itag(303, Params.FORMAT_WEBM, Params.RESOLUTION_1080p60)),
            Map.entry(308, new Itag(308, Params.FORMAT_WEBM, Params.RESOLUTION_1440p60)),
            Map.entry(313, new Itag(313, Params.FORMAT_WEBM, Params.RESOLUTION_2160p)),
            Map.entry(315, new Itag(315, Params.FORMAT_WEBM, Params.RESOLUTION_2160p60)));


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public static boolean isSupported(Integer key){
        return map.containsKey(key);
    }

    public static Itag getItag(Integer key){
        return map.get(key);
    }


}
