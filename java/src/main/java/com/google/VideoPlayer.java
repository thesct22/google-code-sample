package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private CurrentlyPlaying currentlyplaying = new CurrentlyPlaying();
  private VideoPlaylist playlist = new VideoPlaylist();
  private FlaggedVideos flaggedvideos= new FlaggedVideos();
  
  //Comparator for sorting the videos by title
  Comparator<Video> compareByTitle = (Video o1, Video o2) -> o1.getTitle().compareTo( o2.getTitle() );
  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  //prints the number of videos in library
  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }


  //PART 1


  //shows all videos in the library
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

      System.out.print(i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");
      
      String flaggedOrNot=flaggedvideos.findflagged(i.getVideoId());
      if(flaggedOrNot!=null)
        System.out.println(" - FLAGGED (reason: "+ flaggedOrNot+")");
      else
        System.out.println();
    }
  }
  //play the video who's ID is given
  public void playVideo(String videoId) {
    
    Video toplay=videoLibrary.getVideo(videoId);
    //if video not found
    if(toplay==null){
      System.out.println("Cannot play video: Video does not exist");
      return;
    }

    String flaggedOrNot=flaggedvideos.findflagged(videoId);
    if(flaggedOrNot!=null){
      System.out.print("Cannot play video: Video is currently flagged");
      System.out.println(" (reason: "+flaggedOrNot+")");
      return;
    }

    //if nothing is palaying currentstate is -1 (stopped)
    if(currentlyplaying.currentState()==-1){
      System.out.println("Playing video: "+toplay.getTitle());
    }

    //if a video is currently being played or paused
    else if(currentlyplaying.currentState()==1||currentlyplaying.currentState()==0){
      System.out.println("Stopping video: "+currentlyplaying.currentVideo().getTitle());
      System.out.println("Playing video: "+toplay.getTitle());
    }

    currentlyplaying.changeVideo(toplay);
  }

  //to stop the currently played video
  public void stopVideo() {
  
    //if nothing is palaying currentstate is -1 (stopped)
    if(currentlyplaying.currentState()==-1){
      System.out.println("Cannot stop video: No video is currently playing");
    }  

    //if a video is currently played or paused
    else if(currentlyplaying.currentState()==1||currentlyplaying.currentState()==0){
      System.out.println("Stopping video: "+currentlyplaying.currentVideo().getTitle());
      currentlyplaying.changeVideo(null);
      currentlyplaying.changeState(-1);
    }
  }

  //to play a random video
  public void playRandomVideo() {
    //get all videos
    List<Video> allvideos=videoLibrary.getVideos();
    List<Video> allowedvideos=new LinkedList<>();
    

    //if the video is flagged exclude it
    for(Video i:allvideos){
      if(flaggedvideos.findflagged(i.getVideoId())==null){
        allowedvideos.add(i);
      }
    }
    
    //I'm too lazy to rename all the variables below, plus reduces the space
    allvideos=allowedvideos;

    //if no videos available print the same
    if(allvideos.size()==0){
      System.out.println("No videos available");
      return;
    }

    //random value generator
    Random random=new Random();
    int index = random.nextInt(allvideos.size());

    //get a random video
    Video toplay=allvideos.get(index);

    //if currently stopped
    if(currentlyplaying.currentState()==-1){
      System.out.println("Playing video: "+toplay.getTitle());
    }

    //if a video is currently being played or paused
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

      //to get all tags
      String tags="";
      for(String j:currentvideo.getTags()){ 
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

  //adding video to an existing playlist
  public void addVideoToPlaylist(String playlistName, String videoId) {

    //playlsit to add video to
    String toplaylist=playlist.getPlaylist(playlistName);
    
    //if playlist not found
    if(toplaylist==null){
      System.out.println("Cannot add video to "+playlistName+": Playlist does not exist");
      return;
    }

    //video to add
    Video toplay=videoLibrary.getVideo(videoId);

    //if video not found
    if(toplay==null){
      System.out.println("Cannot add video to "+playlistName+": Video does not exist");
      return;
    }

    //check if the video is flagged or not
    String flaggedOrNot=flaggedvideos.findflagged(videoId);
    if(flaggedOrNot!=null){
      System.out.print("Cannot add video to "+toplaylist+": Video is currently flagged");
      System.out.println(" (reason: "+flaggedOrNot+")");
      return;
    }

    //find if the video is in playlist already or not
    if(playlist.findVideoinPL(videoId, toplaylist)!=null){
      System.out.println("Cannot add video to "+playlistName+": Video already added");
    }
    else{
      playlist.addVideoToPlaylist(toplaylist, toplay);
      System.out.println("Added video to "+playlistName+": "+toplay.getTitle());
    }

  }

  //show the list of all playlists
  public void showAllPlaylists() {

    List<String> allplaylists=playlist.getPlaylists();
    if(allplaylists.isEmpty()){
      System.out.println("No playlists exist yet");
      return;
    }

    System.out.println("Showing all playlists:");

    //sort by playlist title
    Collections.sort(allplaylists);
    for (String i: allplaylists){
      System.out.println(i);
    }

  }

  //show all videos in a given playlist
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
      
      //to get all tags
      String tags="";
      for(String j:i.getTags()){
        tags+=j;
        tags+=" ";
      }

      System.out.print("  "+i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");

      //add 'FLAGGED' to flagged videos
      String flaggedOrNot=flaggedvideos.findflagged(i.getVideoId());
      if(flaggedOrNot!=null)
        System.out.println(" - FLAGGED (reason: "+ flaggedOrNot+")");
      else
        System.out.println();
    }
  }

  //remove video from given playlist
  public void removeFromPlaylist(String playlistName, String videoId) {
    
    //playlist to delete video from
    String toplaylist=playlist.getPlaylist(playlistName);
    if(toplaylist==null){
      System.out.println("Cannot remove video from "+playlistName+": Playlist does not exist");
      return;
    }

    //video to delete (too lazy to change the variable name)
    Video toplay=videoLibrary.getVideo(videoId);

    //if video not found
    if(toplay==null){
      System.out.println("Cannot remove video from "+playlistName+": Video does not exist");
      return;
    }

    //see if video is in playlist
    if(playlist.findVideoinPL(videoId, toplaylist)==null){
      System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
    }
    else{
      playlist.removeVideoFromPlaylist(toplaylist, toplay);
      System.out.println("Removed video from "+playlistName+": "+toplay.getTitle());
    }
  }

  //clear all videos in playlist
  public void clearPlaylist(String playlistName) {
    String toplaylist=playlist.getPlaylist(playlistName);

    if(toplaylist==null){
      System.out.println("Cannot clear playlist "+playlistName+": Playlist does not exist");
      return;
    }

    //if playlist is present just run add playlist again. Adding entry to hashmap again deletes previous entry
    playlist.addPlaylist(toplaylist);
    System.out.println("Successfully removed all videos from "+playlistName);
  }

  //to delete a playlist
  public void deletePlaylist(String playlistName) {
    String toplaylist=playlist.getPlaylist(playlistName);

    if(toplaylist==null){
      System.out.println("Cannot delete playlist "+playlistName+": Playlist does not exist");
      return;
    }

    //if playlists exist, delete
    playlist.deletePlaylist(toplaylist);
    System.out.println("Deleted playlist: "+playlistName);
  }


  //PART 3

  //search a video from keyword in title
  public void searchVideos(String searchTerm) {

    List<Video> allvideos=videoLibrary.getVideos();
    List<Video> resultvideos=new LinkedList<>();
    
    //if video is flagged dont add in shortlist
    //if video title contains the keyword shortlist it
    for(Video i:allvideos){
      String flaggedOrNot=flaggedvideos.findflagged(i.getVideoId());
      if(i.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) && flaggedOrNot==null){
        resultvideos.add(i);
      }
    }

    //too lazy to change variable name, and it helps in reducing space
    allvideos=resultvideos;

    //if new list is empty no videos with the search term found
    if(allvideos.isEmpty()){
      System.out.println("No search results for "+searchTerm);
      return;
    }

    //sort by title
    Collections.sort(allvideos, compareByTitle );

    System.out.println("Here are the results for "+searchTerm+":");
    
    //to index the results
    int counter=0;

    for (Video i: allvideos){
      
      counter++;
      
      //to get all tags
      String tags="";
      for(String j:i.getTags()){
        tags+=j;
        tags+=" ";
      }

      System.out.println("  "+counter+") "+i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");
    } 
    
    //ask if the user wants to play any of the videos found. Type in the index of the video to play
    //any any other entry is made dont play video
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    
    //takes in video index
    var scanner = new Scanner(System.in);
    String str=scanner.nextLine();
    
    //try if the entered value is a number between 1 and number of found videos
    //else throw exception
    try{

      int num = Integer.parseInt(str);
      if(num>counter||num<1)
        throw new NumberFormatException("wrong number");

      Video toplay=allvideos.get(num-1);
      playVideo(toplay.getVideoId());

    } catch (NumberFormatException e) {}
  }

  //search for videos with given tag
  public void searchVideosWithTag(String videotag) {

    //if the tag to search doesn't start with '#' return that no results found
    if(videotag.charAt(0)!='#'){
      System.out.println("No search results for "+videotag);
      return;
    }
    //remove # from tag for easier searching
    String videoTag=videotag.substring(1);

    List<Video> allvideos=videoLibrary.getVideos();
    List<Video> resultvideos=new LinkedList<>();

    //don't add to list if video is flagged
    //add to list if the tag contains the given search term
    for(Video i:allvideos){
      String flaggedOrNot=flaggedvideos.findflagged(i.getVideoId());
      for(String j:i.getTags()){
        if(j.toLowerCase().contains(videoTag.toLowerCase()) && flaggedOrNot==null){
          resultvideos.add(i);
        }
      }
    }

    //lazy to chage variable below
    allvideos=resultvideos;

    //if list empty, tag not found
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

      //to get all tags
      String tags="";
      for(String j:i.getTags()){
        tags+=j;
        tags+=" ";
      }

      System.out.println("  "+counter+") "+i.getTitle()+" ("+i.getVideoId()+") ["+tags.trim()+"]");
    }  

    //ask if the user wants to play any of the videos found. Type in the index of the video to play
    //any any other entry is made dont play video
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    
    //scans for the video index
    var scanner = new Scanner(System.in);
    String str=scanner.nextLine();
    scanner.close();

    //try if the entered value is a number between 1 and number of found videos
    //else throw exception
    try{

      int num = Integer.parseInt(str);
      if(num>counter||num<1)
        throw new NumberFormatException("wrong number");

      Video toplay=allvideos.get(num-1);
      playVideo(toplay.getVideoId());

    } catch (NumberFormatException e) {}  }


    //PART 4


  //flag a video with default reason
  public void flagVideo(String videoId) {

    Video toflag=videoLibrary.getVideo(videoId);

    //if video not found
    if(toflag==null){
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }

    //if the video is not already flagged flag it
    if(flaggedvideos.findflagged(videoId)==null){
      flaggedvideos.flagvideo(videoId);

      //if the flagged video is currently not stopped then stop it
      if(currentlyplaying.currentState()!=-1 && currentlyplaying.currentVideo().getVideoId().equals(videoId))
        stopVideo();

      System.out.print("Successfully flagged video: "+toflag.getTitle());
      System.out.println(" (reason: "+flaggedvideos.findflagged(videoId)+ ")");
    }

    //if the video is already flagged
    else{
      System.out.println("Cannot flag video: Video is already flagged");
    }
    
  }

  //flag video with user given reason
  public void flagVideo(String videoId, String reason) {
    Video toflag=videoLibrary.getVideo(videoId);

    //if video not found
    if(toflag==null){
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }

    //if the video is not already flagged flag it
    if(flaggedvideos.findflagged(videoId)==null){
        flaggedvideos.flagvideo(videoId,reason);

        //if the flagged video is currently not stopped then stop it
        if(currentlyplaying.currentState()!=-1 && currentlyplaying.currentVideo().getVideoId().equals(videoId))
          stopVideo();
        
        System.out.print("Successfully flagged video: "+toflag.getTitle());
        System.out.println(" (reason: "+flaggedvideos.findflagged(videoId)+ ")");
    }
    
    //if video flagged already say that
    else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
  }

  //to unflag (allow) a video
  public void allowVideo(String videoId) {
    Video toallow=videoLibrary.getVideo(videoId);

    //if video not found
    if(toallow==null){
      System.out.println("Cannot remove flag from video: Video does not exist");
      return;
    }

    //if the video is flagged already unflag it
    if(flaggedvideos.findflagged(videoId)!=null){
        flaggedvideos.allowvideo(videoId);
        System.out.println("Successfully removed flag from video: "+toallow.getTitle());
    }

    //if video is not flagged say you cannot unflag it
    else{
        System.out.println("Cannot remove flag from video: Video is not flagged");
      }
  }
}