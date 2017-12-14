package me.mjaroszewicz.extractor;

public enum  StreamParams {
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

    private StreamParams(final String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }



}
