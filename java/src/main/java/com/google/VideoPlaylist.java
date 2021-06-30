package com.google;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/** A class used to represent a Playlist */
class VideoPlaylist {
    VideoPlaylist(){
        playlists=new HashMap<>();
    }
    private static HashMap<String, Video> playlists;
    
    //add new playlist with no songs initially
    public void addPlaylist(String name){
        this.playlists.put(name, null);

    }
    //get the playlist
    public String getPlaylist(String name){
        for (Map.Entry<String, Video> mapElement : this.playlists.entrySet()) {
            String key =mapElement.getKey().toString().toLowerCase();
            // System.out.println(key);
            // System.out.println(name);
            if(key.equalsIgnoreCase(name.toLowerCase())){
                // System.out.println("qwertty");
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
}
