package com.google;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/** A class used to represent a Playlist */
class VideoPlaylist {
    VideoPlaylist(){
        playlists=new LinkedHashMap<>();
    }
    private static LinkedHashMap<String, LinkedHashMap<String,Video>> playlists;
    
    //add new playlist with no songs initially
    public void addPlaylist(String name){
        LinkedHashMap<String,Video>emptyone=new LinkedHashMap<>();
        emptyone.put(null,null);
        playlists.put(name,null);

    }

    public void deletePlaylist(String name){
        playlists.remove(name);
    }

    //get the playlist
    String getPlaylist(String name){
        for (Map.Entry<String, LinkedHashMap<String,Video>> mapElement : playlists.entrySet()) {
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

    //get playlists length
    int playlistLength(String name){
        LinkedHashMap<String,Video> listofVideos=playlists.get(name);
        if(listofVideos==null)
            return 0;
        return listofVideos.size();

    }

    //find video in playlist
    Video findVideoinPL(String videoID, String playlistname){
        LinkedHashMap<String,Video> listofVideos=playlists.get(playlistname);
        if(listofVideos==null)
            return null;
        return listofVideos.get(videoID);
    }
    void addVideoToPlaylist(String playlistname, Video video){
        LinkedHashMap<String,Video> listofVideos=playlists.get(playlistname);
        if(listofVideos==null){
            listofVideos=new LinkedHashMap<>();
        }
        listofVideos.put(video.getVideoId(),video);
        playlists.put(playlistname, listofVideos);
    }
    ArrayList<Video> getVideos(String playlistname) {
        LinkedHashMap<String,Video> listofVideos=playlists.get(playlistname);
        if(listofVideos==null)
            return new ArrayList<>();
        ArrayList<Video> raw= new ArrayList<>(listofVideos.values());
        raw.removeIf(Objects::isNull);
        return raw;
    }
    void removeVideoFromPlaylist(String toplaylist, Video toplay){
        LinkedHashMap<String,Video> listofVideos=playlists.get(toplaylist);
        if(listofVideos==null){
            listofVideos=new LinkedHashMap<>();
        }
        listofVideos.remove(toplay.getVideoId());
        playlists.put(toplaylist, listofVideos);
    }
}
