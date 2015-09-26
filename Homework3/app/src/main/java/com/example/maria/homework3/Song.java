package com.example.maria.homework3;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.ImageButton;

/**
 * Created by Maria on 9/19/2015.
 */
public class Song {

    int[] songList;
    int index;
    MediaPlayer player;
    Context overIt;
    boolean isSeek;
    boolean isLooping;

    public Song (Context context){
        songList = GetSongResources.getSongResources();
        index = 0;
        overIt = context;
        isSeek = false;
        player = MediaPlayer.create(overIt, songList[index]);
        isLooping = false;
        play();
    }

    public void play(){
        player.start();
    }

    public void pause(){
        player.pause();
    }

    public int songIndex(){
        return index;
    }

    public int nextSongIndex(){
        int i;
        if(index == (songList.length - 1)){
            i = 0;
        }
        else {
            i = index + 1;
        }
        return i;
    }

    public void setSeek (boolean x){
        isSeek = x;
    }

    public void seek2(int num){
        player.seekTo(num);
    }

    public String timePassed(){
        int position = player.getCurrentPosition() / 1000;
        int min = position / 60;
        int sec = position % 60;
        return (String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    public String timeLeft(){
            int position = player.getCurrentPosition()/1000;
            int max = player.getDuration()/1000;
            int min = (max - position) / 60;
            int sec = (max - position) % 60;
            isOver(min, sec);
        return (String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    public int getPosition(){
        return player.getCurrentPosition();
    }

    public int getTime(){
        return player.getDuration();
    }

    public void isOver(int min, int sec){
        if (sec == 0 && min == 0) {
            if (isLooping)
                playThisOne(index);
            else
                playNext();
        }
    }

    public void isLooping(boolean x){
        isLooping = x;
    }

    public void playThisOne(int i){
        index = i;
        player.release();
        player = MediaPlayer.create(overIt, songList[index]);
        player.start();
    }

    public void playPrev(){
        if(index == 0)
            index = (songList.length - 1);
        else if (isLooping)
            playThisOne(index);
        else
            index--;
        player.release();
        player = MediaPlayer.create(overIt, songList[index]);
        player.start();
    }

    public void playNext(){
        if(index == (songList.length - 1))
            index = 0;
        else if (isLooping)
            playThisOne(index);
        else
            index ++;
        player.release();
        player = MediaPlayer.create(overIt, songList[index]);
        player.start();
    }



}
