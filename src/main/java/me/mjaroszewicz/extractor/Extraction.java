package me.mjaroszewicz.extractor;

import java.util.HashMap;

public class Extraction {

    private String title;

    private HashMap<String, String> streams;

    public Extraction(String title, HashMap<String, String> streams) {
        this.title = title;
        this.streams = streams;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<String, String> getStreams() {
        return streams;
    }

    public void setStreams(HashMap<String, String> streams) {
        this.streams = streams;
    }
}
