package me.mjaroszewicz.extractor;

import java.util.HashMap;

public class Extraction {

    private String title;

    private HashMap<String, Itag> streams;

    public Extraction(){}

    public Extraction(String title, HashMap<String, Itag> streams) {
        this.title = title;
        this.streams = streams;
    }

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
