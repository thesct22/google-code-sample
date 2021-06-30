package com.google;

import java.util.HashMap;

/**
 * A class used to view and change and store the flagged states of videos.
 */
public class FlaggedVideos {
    private HashMap<String,String> flagged=new HashMap<>();
    
    void flagvideo(String videoId){
        flagged.put(videoId, "Not supplied");
    }
    
    void flagvideo(String videoId, String reason){
        flagged.put(videoId, reason);
    }

    void allowvideo(String videoId){
        flagged.remove(videoId);
    }
    
    String findflagged(String videoId){
        return flagged.get(videoId);
    }
}
