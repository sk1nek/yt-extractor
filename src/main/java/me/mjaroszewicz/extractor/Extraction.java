package me.mjaroszewicz.extractor;

import java.util.HashMap;

public class Extraction {

    private String title;

    private HashMap<String, Itag> streams;

    public Extraction(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<String, Itag> getStreams() {
        return streams;
    }

    public void setStreams(HashMap<String, Itag> streams) {
        this.streams = streams;
    }
}
