package com.example.better_waves.ui.main;


import android.media.MediaPlayer;
import android.widget.SeekBar;

import com.example.better_waves.MainActivity;

public class SeekBarUpdater extends Thread {
    MediaPlayer player;
    SeekBar seekBar;

    @Override
    public void run() {
        this.player = MainActivity.player;
        this.seekBar = MainActivity.seekBar;
        while(player != null && !player.isPlaying()){}
        int currentPosition = player.getCurrentPosition();
        int total = player.getDuration();
        seekBar.setMax(total);

        while (player != null && player.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(0);
                currentPosition = player.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            }
            seekBar.setProgress(currentPosition);
        }
        if (!(currentPosition < total)) {
            System.out.println("Reseting player.");
            MainActivity.resetPlayer();
        }
    }
}