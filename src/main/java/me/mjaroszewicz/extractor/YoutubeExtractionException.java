package me.mjaroszewicz.extractor;

public class YoutubeExtractionException extends Exception {

    private final String message;

    YoutubeExtractionException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
