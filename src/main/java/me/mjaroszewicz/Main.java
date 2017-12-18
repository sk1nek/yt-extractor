package me.mjaroszewicz;

import me.mjaroszewicz.extractor.Extraction;
import me.mjaroszewicz.extractor.YoutubeDownloader;
import me.mjaroszewicz.extractor.YoutubeExtractionException;
import me.mjaroszewicz.extractor.YoutubeExtractor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) {
        YoutubeExtractor extractor = new YoutubeExtractor();
        try{
            Extraction extraction = extractor.extract("659pppwniXA");
            System.out.println(extraction);
            byte[] b = YoutubeDownloader.downloadVideoBytes(extraction, "MPEG4", "720p");
            Files.write(Paths.get("lol.mp4"), b, StandardOpenOption.CREATE);
        }catch(Throwable ytex){
            ytex.printStackTrace();
        }
    }
}
