package com.google;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Objects;

/** A class used to represent a Playlist */
class VideoPlaylist {
    VideoPlaylist(){

        //linkedhashmap serves better since it remebers the order of insertion
        playlists=new LinkedHashMap<>();
    }
    private static LinkedHashMap<String, LinkedHashMap<String,Video>> playlists;
    
    //add new playlist with no songs initially
    public void addPlaylist(String name){
        LinkedHashMap<String,Video>emptyone=new LinkedHashMap<>();
        emptyone.put(null,null);
        playlists.put(name,null);

    }

    //delete a playlist
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
        return new ArrayList<>(keySet);
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

    //add a video to the given playlist
    void addVideoToPlaylist(String playlistname, Video video){
        LinkedHashMap<String,Video> listofVideos=playlists.get(playlistname);
        if(listofVideos==null){
            listofVideos=new LinkedHashMap<>();
        }

        //first get the necessary hashmap from outer hashmap, edit it and put it back
        listofVideos.put(video.getVideoId(),video);
        playlists.put(playlistname, listofVideos);
    }

    //get a list of videos in a playlist
    ArrayList<Video> getVideos(String playlistname) {
        LinkedHashMap<String,Video> listofVideos=playlists.get(playlistname);
        
        //if list of videos for the playlist is null returns an empty arraylist
        if(listofVideos==null)
            return new ArrayList<>();
        
        //to remove null elements if any
        ArrayList<Video> raw= new ArrayList<>(listofVideos.values());
        raw.removeIf(Objects::isNull);
        return raw;
    }

    //remove a gicen video from a playlist
    void removeVideoFromPlaylist(String toplaylist, Video toplay){
        LinkedHashMap<String,Video> listofVideos=playlists.get(toplaylist);
        if(listofVideos==null){
            listofVideos=new LinkedHashMap<>();
        }

        //first get the necessary hashmap from outer hashmap, edit it and put it back
        listofVideos.remove(toplay.getVideoId());
        playlists.put(toplaylist, listofVideos);
    }
}
