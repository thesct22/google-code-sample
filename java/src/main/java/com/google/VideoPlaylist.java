package com.google;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/** A class used to represent a Playlist */
class VideoPlaylist {
    VideoPlaylist(){
        playlists=new HashMap<>();
    }
    private static HashMap<String, HashMap<String,Video>> playlists;
    
    //add new playlist with no songs initially
    public void addPlaylist(String name){
        HashMap<String,Video>emptyone=new HashMap<>();
        emptyone.put(null,null);
        playlists.put(name,null);

    }

    //get the playlist
    String getPlaylist(String name){
        for (Map.Entry<String, HashMap<String,Video>> mapElement : playlists.entrySet()) {
            String key =mapElement.getKey().toLowerCase();

            if(key.equalsIgnoreCase(name.toLowerCase())){
                return mapElement.getKey();
            }
        }
        return null;
    }

    //get all playlists
    ArrayList<String> getPlaylists() {
        Set<String> keySet = playlists.keySet();
        ArrayList<String> listOfKeys = new ArrayList<String>(keySet);
        return listOfKeys;
    }

    //find video in playlist
    Video findVideoinPL(String videoID, String playlistname){
        HashMap<String,Video> listofVideos=playlists.get(playlistname);
        if(listofVideos==null)
            return null;
        return listofVideos.get(videoID);
    }
    void addVideoToPlaylist(String playlistname, Video video){
        HashMap<String,Video> listofVideos=playlists.get(playlistname);
        if(listofVideos==null){
            listofVideos=new HashMap<>();
        }
        listofVideos.put(video.getVideoId(),video);
        playlists.put(playlistname, listofVideos);
    }
    ArrayList<Video> getVideos(String playlistname) {
        HashMap<String,Video> listofVideos=playlists.get(playlistname);
        if(listofVideos==null)
            return new ArrayList<>();
        ArrayList<Video> raw= new ArrayList<>(listofVideos.values());
        raw.removeIf(Objects::isNull);
        return raw;
    }
}
