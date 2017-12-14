package me.mjaroszewicz;

import me.mjaroszewicz.extractor.Extraction;
import me.mjaroszewicz.extractor.YoutubeExtractor;

public class Main {

    public static void main(String[] args) {
        YoutubeExtractor extractor = new YoutubeExtractor();
        Extraction ex = extractor.extract("aQZDyyIyQMA");
        System.out.println(ex.getTitle());
        ex.getStreams().forEach((k, v) -> System.out.println(v.getResolution() + ": " + v.getFormat() + " url: " + k));
    }
}
