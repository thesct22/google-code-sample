package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private CurrentlyPlaying currentlyplaying = new CurrentlyPlaying();
  private VideoPlaylist playlist = new VideoPlaylist();
  private FlaggedVideos flaggedvideos= new FlaggedVideos();
  
  Comparator<Video> compareByTitle = (Video o1, Video o2) -> o1.getTitle().compareTo( o2.getTitle() );
  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }


  //PART 1


  public void showAllVideos() {
    List<Video> allvideos=videoLibrary.getVideos();

    //sort by title
    Collections.sort(allvideos, compareByTitle );

    System.out.println("Here's a list of all available videos:");
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
      System.out.println("Playing video: "+toplay.getTitle());
    }

    //if a song is currently being played or paused
    else if(currentlyplaying.currentState()==1||currentlyplaying.currentState()==0){
      System.out.println("Stopping video: "+currentlyplaying.currentVideo().getTitle());
      System.out.println("Playing video: "+toplay.getTitle());
    }

    currentlyplaying.changeVideo(toplay);
  }

  public void stopVideo() {
  
    //if nothing is palaying currentstate is -1 (stopped)
    if(currentlyplaying.currentState()==-1){
      System.out.println("Cannot stop video: No video is currently playing");
    }  

    //if a song is currently played or paused
    else if(currentlyplaying.currentState()==1||currentlyplaying.currentState()==0){
      System.out.println("Stopping video: "+currentlyplaying.currentVideo().getTitle());
      currentlyplaying.changeVideo(null);
      currentlyplaying.changeState(-1);
    }
  }

  public void playRandomVideo() {
    //get all videos
    List<Video> allvideos=videoLibrary.getVideos();
    
    //random value generator
    Random random=new Random();
    int index = random.nextInt(allvideos.size());

    //get a random video
    Video toplay=allvideos.get(index);

    if(currentlyplaying.currentState()==-1){
      System.out.println("Playing video: "+toplay.getTitle());
    }

    //if a song is currently being played or paused
    else if(currentlyplaying.currentState()==1||currentlyplaying.currentState()==0){
      System.out.println("Stopping video: "+currentlyplaying.currentVideo().getTitle());
      System.out.println("Playing video: "+toplay.getTitle());
    }

    currentlyplaying.changeVideo(toplay);
  }

  public void pauseVideo() {
    //if nothing is palaying currentstate is -1 (stopped)
    if(currentlyplaying.currentState()==-1){
      System.out.println("Cannot pause video: No video is currently playing");
    }  

    //if something is playing (currentstate is 1) pause it
    else if(currentlyplaying.currentState()==1){
      System.out.println("Pausing video: "+currentlyplaying.currentVideo().getTitle());
      currentlyplaying.changeState(0);
    }

    //if paused already say that
    else if(currentlyplaying.currentState()==0){
      System.out.println("Video already paused: "+currentlyplaying.currentVideo().getTitle());
    }  

  }

  public void continueVideo() {

    //if stopped cannot continue
    if(currentlyplaying.currentState()==-1){
      System.out.println("Cannot continue video: No video is currently playing");
    }  

    //if something is playing (currentstate is 1) cannot continue again
    else if(currentlyplaying.currentState()==1){
      System.out.println("Cannot continue video: Video is not paused");
    }

    //if paused continue
    else if(currentlyplaying.currentState()==0){
      System.out.println("Continuing video: "+currentlyplaying.currentVideo().getTitle());
      currentlyplaying.changeState(1);
    }  
  }

  public void showPlaying() {
    //if stopped cannot show playing video
    if(currentlyplaying.currentState()==-1){
      System.out.println("No video is currently playing");
    }  

    //if something is playing show the deets
    else if(currentlyplaying.currentState()==1){
      Video currentvideo=currentlyplaying.currentVideo();

      System.out.print("Currently playing: ");
      System.out.print(currentvideo.getTitle()+" (");
      System.out.print(currentvideo.getVideoId()+") [");

      String tags="";

      for(String j:currentvideo.getTags()){
        //to get all tags
        tags+=j;
        tags+=" ";
      }
      System.out.println(tags.trim()+"]");
    }

    //if paused show all deets and also say its paused
    else if(currentlyplaying.currentState()==0){
      Video currentvideo=currentlyplaying.currentVideo();

      System.out.print("Currently playing: ");
      System.out.print(currentvideo.getTitle()+" (");
      System.out.print(currentvideo.getVideoId()+") [");

      String tags="";

      for(String j:currentvideo.getTags()){
        //to get all tags
        tags+=j;
        tags+=" ";
      }
      System.out.println(tags.trim()+"] - PAUSED");
    }  
  }


  //PART 2


  public void createPlaylist(String playlistName) {
    //check if present already
    if(playlist.getPlaylist(playlistName)!=null)
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    
      //if not present then add to list
    else{
      playlist.addPlaylist(playlistName);
      System.out.println("Successfully created new playlist: "+playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {

    String toplaylist=playlist.getPlaylist(playlistName);

    if(toplaylist==null){
      System.out.println("Cannot add video to "+playlistName+": Playlist does not exist");
      return;
    }

    Video toplay=videoLibrary.getVideo(videoId);

    //if video not found
    if(toplay==null){
      System.out.println("Cannot add video to "+playlistName+": Video does not exist");
      return;
    }

    if(playlist.findVideoinPL(videoId, toplaylist)!=null){
      System.out.println("Cannot add video to "+playlistName+": Video already added");
    }
    else{
      playlist.addVideoToPlaylist(toplaylist, toplay);
      System.out.println("Added video to "+playlistName+": "+toplay.getTitle());
    }

  }

  public void showAllPlaylists() {

    List<String> allplaylists=playlist.getPlaylists();
    if(allplaylists.isEmpty()){
      System.out.println("No playlists exist yet");
      return;
    }
    System.out.println("Showing all playlists:");
    Collections.sort(allplaylists);
    for (String i: allplaylists){
      System.out.println(i);
    }

  }

  public void showPlaylist(String playlistName) {
    String toplaylist=playlist.getPlaylist(playlistName);

    if(toplaylist==null){
      System.out.println("Cannot show playlist "+playlistName+": Playlist does not exist");
      return;
    }

    System.out.println("Showing playlist: "+playlistName);

    ArrayList<Video> listofvideos=playlist.getVideos(toplaylist);
    if(listofvideos.isEmpty()){
      System.out.println("  No videos here yet");
      return;
    }

    for (Video i: listofvideos){
      String tags="";

      for(String j:i.getTags()){
        //to get all tags
        tags+=j;
        tags+=" ";
      }

      System.out.println("  "+i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    String toplaylist=playlist.getPlaylist(playlistName);

    if(toplaylist==null){
      System.out.println("Cannot remove video from "+playlistName+": Playlist does not exist");
      return;
    }

    Video toplay=videoLibrary.getVideo(videoId);

    //if video not found
    if(toplay==null){
      System.out.println("Cannot remove video from "+playlistName+": Video does not exist");
      return;
    }

    if(playlist.findVideoinPL(videoId, toplaylist)==null){
      System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
    }
    else{
      playlist.removeVideoFromPlaylist(toplaylist, toplay);
      System.out.println("Removed video from "+playlistName+": "+toplay.getTitle());
    }
  }

  public void clearPlaylist(String playlistName) {
    String toplaylist=playlist.getPlaylist(playlistName);

    if(toplaylist==null){
      System.out.println("Cannot clear playlist "+playlistName+": Playlist does not exist");
      return;
    }
    playlist.addPlaylist(toplaylist);
    System.out.println("Successfully removed all videos from "+playlistName);
  }

  public void deletePlaylist(String playlistName) {
    String toplaylist=playlist.getPlaylist(playlistName);

    if(toplaylist==null){
      System.out.println("Cannot delete playlist "+playlistName+": Playlist does not exist");
      return;
    }
    playlist.deletePlaylist(toplaylist);
    System.out.println("Deleted playlist: "+playlistName);
  }


  //PART 3


  public void searchVideos(String searchTerm) {
    List<Video> allvideos=videoLibrary.getVideos();
    List<Video> resultvideos=new LinkedList<>();
    for(Video i:allvideos){
      if(i.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
        resultvideos.add(i);
      }
    }
    allvideos=resultvideos;

    if(allvideos.isEmpty()){
      System.out.println("No search results for "+searchTerm);
      return;
    }
    //sort by title
    Collections.sort(allvideos, compareByTitle );

    System.out.println("Here are the results for "+searchTerm+":");
    int counter=0;
    for (Video i: allvideos){
      counter++;
      String tags="";

      for(String j:i.getTags()){
        //to get all tags
        tags+=j;
        tags+=" ";
      }

      System.out.println("  "+counter+") "+i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");
    }  
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    var scanner = new Scanner(System.in);
    String str=scanner.nextLine();
    try{
      int num = Integer.parseInt(str);
      if(num>counter||num<1)
        throw new NumberFormatException("wrong number");
      Video toplay=allvideos.get(num-1);
      playVideo(toplay.getVideoId());

    } catch (NumberFormatException e) {}
  }

  public void searchVideosWithTag(String videotag) {
    if(videotag.charAt(0)!='#'){
      System.out.println("No search results for "+videotag);
      return;
    }
    String videoTag=videotag.substring(1);
    List<Video> allvideos=videoLibrary.getVideos();
    List<Video> resultvideos=new LinkedList<>();
    for(Video i:allvideos){
      for(String j:i.getTags()){
        if(j.toLowerCase().contains(videoTag.toLowerCase())){
          resultvideos.add(i);
        }
      }
    }
    allvideos=resultvideos;

    if(allvideos.isEmpty()){
      System.out.println("No search results for "+videotag);
      return;
    }
    //sort by title
    Collections.sort(allvideos, compareByTitle );

    System.out.println("Here are the results for "+videotag+":");
    int counter=0;
    for (Video i: allvideos){
      counter++;
      String tags="";

      for(String j:i.getTags()){
        //to get all tags
        tags+=j;
        tags+=" ";
      }

      System.out.println("  "+counter+") "+i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");
    }  
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    var scanner = new Scanner(System.in);
    String str=scanner.nextLine();
    try{
      int num = Integer.parseInt(str);
      if(num>counter||num<1)
        throw new NumberFormatException("wrong number");
      Video toplay=allvideos.get(num-1);
      playVideo(toplay.getVideoId());

    } catch (NumberFormatException e) {}  }

  public void flagVideo(String videoId) {
    Video toflag=videoLibrary.getVideo(videoId);

    //if video not found
    if(toflag==null){
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }
    if(flaggedvideos.findflagged(videoId)==null){
      flaggedvideos.flagvideo(videoId);
      System.out.print("Successfully flagged video: "+toflag.getTitle());
      System.out.println(" (reason: "+flaggedvideos.findflagged(videoId)+ ")");
    }
    else{
      System.out.println("Cannot flag video: Video is already flagged");
    }
    
  }

  public void flagVideo(String videoId, String reason) {
    Video toflag=videoLibrary.getVideo(videoId);

    //if video not found
    if(toflag==null){
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }
    
    if(flaggedvideos.findflagged(videoId)==null){
        flaggedvideos.flagvideo(videoId,reason);
        System.out.print("Successfully flagged video: "+toflag.getTitle());
        System.out.println(" (reason: "+flaggedvideos.findflagged(videoId)+ ")");
    }
    else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}