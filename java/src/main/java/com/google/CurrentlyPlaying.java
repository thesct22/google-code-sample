package com.google;

public class CurrentlyPlaying {
    CurrentlyPlaying(){
        playing=null;
        state=-1;
    }
    private  Video playing;
    private  int state=-1; //-1 for stopped, 1 for playing, 0 for paused
    public  void changeState(int tostate){
        state=tostate;
    }
    public  int currentState(){
        return state;
    }
    public  Video currentVideo(){
        return playing;
    }
    public  int changeVideo(Video toplay){
        playing=toplay;
        state=1;
        return state;
    }
}
