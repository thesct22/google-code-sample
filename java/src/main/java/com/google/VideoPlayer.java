package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private static CurrentlyPlaying currentlyplaying;
  
  Comparator<Video> compareByTitle = (Video o1, Video o2) -> o1.getTitle().compareTo( o2.getTitle() );
  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> allvideos=videoLibrary.getVideos();

    //sort by title
    Collections.sort(allvideos, compareByTitle );

    for (Video i: allvideos){
      String tags="";

      for(String j:i.getTags()){
        //to get all tags
        tags+=j;
        tags+=" ";
      }

      System.out.println(i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");
    }
  }

  public void playVideo(String videoId) {
    Video toplay=videoLibrary.getVideo(videoId);

    //if video not found
    if(toplay==null){
      System.out.println("Cannot play video: Video does not exist");
      return;
    }

    //if nothing is palaying currentstate is -1 (stopped)
    if(currentlyplaying.currentState()==-1){
      System.out.println("Playing Video: "+toplay.getTitle());
    }

    //if a song is currently being played
    else if(currentlyplaying.currentState()==1){
      System.out.println("Stopping Video: "+currentlyplaying.currentVideo().getTitle());
      System.out.println("Playing Video: "+toplay.getTitle());
    }

    currentlyplaying.changeVideo(toplay);
  }

  public void stopVideo() {
  
    //if nothing is palaying currentstate is -1 (stopped)
    if(currentlyplaying.currentState()==-1){
      System.out.println("Cannot stop video: No video is currently playing");
    }  
    else if(currentlyplaying.currentState()==1){
      System.out.println("Stopping Video: "+currentlyplaying.currentVideo().getTitle());
      currentlyplaying.changeState(-1);
    }
  }

  public void playRandomVideo() {
    System.out.println("playRandomVideo needs implementation");
  }

  public void pauseVideo() {
    System.out.println("pauseVideo needs implementation");
  }

  public void continueVideo() {
    System.out.println("continueVideo needs implementation");
  }

  public void showPlaying() {
    System.out.println("showPlaying needs implementation");
  }

  public void createPlaylist(String playlistName) {
    System.out.println("createPlaylist needs implementation");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}