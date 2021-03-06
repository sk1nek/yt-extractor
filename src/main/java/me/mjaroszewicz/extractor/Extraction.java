package me.mjaroszewicz.extractor;

import java.util.HashMap;

public class Extraction {

    private String title;

    private HashMap<String, Itag> streams;

    Extraction(){}

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public HashMap<String, Itag> getStreams() {
        return streams;
    }

    void setStreams(HashMap<String, Itag> streams) {
        this.streams = streams;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Extraction: Title - " + title + ", urls: \n");

        streams.forEach((k,v) -> stringBuilder.append("Format: ").append(v.getFormat()).append(" Resolution: ").append(v.getResolution()).append(" Url: ").append(k).append("\n"));
        return stringBuilder.toString();
    }
}
