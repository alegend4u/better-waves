package com.example.better_waves.ui.main;


import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.better_waves.MainActivity;

import org.w3c.dom.Text;

import static java.lang.Math.floor;


public class SeekBarUpdater extends Thread {
    MediaPlayer player;
    SeekBar seekBar;

    @Override
    public void run() {
        this.player = MainActivity.player;
        this.seekBar = MainActivity.seekBar;
        while(player != null && !player.isPlaying()){} // Required to wait till playing starts
        int currentPosition = player.getCurrentPosition();
        final int total = player.getDuration();
        seekBar.setMax(total);

        TextView currentTime = MainActivity.currentTime;
        TextView totalTime = MainActivity.totalTime;
        totalTime.post(new Runnable() {
               @Override
               public void run() {
                   MainActivity.totalTime.setText(milliToMinutes(total));
               }
           }
        );

        while (player != null && player.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(200);
                currentPosition = player.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            }
            final String current = milliToMinutes(currentPosition);

            currentTime.post(new Runnable() {
                @Override
                public void run() {
                    MainActivity.currentTime.setText(current);
                }
            });
            seekBar.setProgress(currentPosition);
        }
        if (!(currentPosition < total)) {
            System.out.println("Reseting player.");
            MainActivity.resetPlayer();
        }
    }

    private String milliToMinutes(int time){
        String result;
        time = (int)floor(time /1000);
        int hours = (int)floor(time/3600);
        int min = (int)floor(time/60);
        int sec = time % 60;
        String minutes = String.format("%02d", min);
        String seconds = String.format("%02d", sec);
        result = minutes + ":" + seconds;
        if (hours > 0){
            result = hours + ":" + result;
        }
        return result;
    }
}