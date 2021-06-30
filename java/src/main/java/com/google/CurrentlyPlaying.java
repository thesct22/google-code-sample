package com.google;

public class CurrentlyPlaying {
    private CurrentlyPlaying(){
        state=-1;
        playing=null;
    }
    private static Video playing;
    private static int state=-1; //-1 for stopped, 1 for playing, 0 for paused
    public static void changeState(int tostate){
        state=tostate;
    }
    public static int currentState(){
        return state;
    }
    public static Video currentVideo(){
        return playing;
    }
    public static int changeVideo(Video toplay){
        playing=toplay;
        state=1;
        return state;
    }
}
