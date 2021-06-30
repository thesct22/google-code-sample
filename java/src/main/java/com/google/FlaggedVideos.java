package com.google;

import java.util.HashMap;

public class FlaggedVideos {
    private HashMap<String,String> flagged=new HashMap<>();
    void flagvideo(String videoId){
        flagged.put(videoId, "Not supplied");
    }
    void flagvideo(String videoId, String reason){
        flagged.put(videoId, reason);
    }
    String findflagged(String videoId){
        return flagged.get(videoId);
    }
    void allowvideo(String videoId){
        flagged.remove(videoId);
    }
}
