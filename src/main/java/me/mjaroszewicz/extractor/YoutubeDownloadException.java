package me.mjaroszewicz.extractor;

public class YoutubeDownloadException extends Exception {

    private String message;

    public YoutubeDownloadException(String msg){
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
