package me.mjaroszewicz.extractor;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.util.stream.Collectors;

public class YoutubeDownloader {

    /**
     * @param ex Extraction object containing stream data.
     * @param format - String representation of video format. there are only three supported: "v3GPP", "WEBM", "MPEG4".
     * @param resolution - String containing resolution, "480p", "720p60" etc..
     * @return byte array representation of video
     * @throws YoutubeDownloadException
     */
    public static byte[] downloadVideoBytes(Extraction ex, String format, String resolution) throws YoutubeDownloadException{

        String streamUrl =
                ex.getStreams()
                .entrySet()
                .stream()
                .filter(p ->
                        p.getValue().getResolution().equals(resolution) &&
                                p.getValue().getFormat().equals(format))
                .collect(Collectors.toList()).get(0).getKey();

        if(streamUrl == null)
            throw new YoutubeDownloadException("Unsupported format or resolution");

        byte[] ret;

        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(streamUrl)).GET().build();
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandler.asByteArray());
            ret = response.body();
        }catch(Throwable t){
            throw new YoutubeDownloadException(t.getMessage());
        }

        return ret;
    }
}
