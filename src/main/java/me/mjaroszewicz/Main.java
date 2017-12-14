package me.mjaroszewicz;

import me.mjaroszewicz.extractor.Extraction;
import me.mjaroszewicz.extractor.YoutubeExtractionException;
import me.mjaroszewicz.extractor.YoutubeExtractor;

public class Main {

    public static void main(String[] args) {
        YoutubeExtractor extractor = new YoutubeExtractor();
        try{
            Extraction extraction = extractor.extract("659pppwniXA&list=RD659pppwniXA");
            System.out.println(extraction);

        }catch(YoutubeExtractionException ytex){
            ytex.printStackTrace();
        }
    }
}
