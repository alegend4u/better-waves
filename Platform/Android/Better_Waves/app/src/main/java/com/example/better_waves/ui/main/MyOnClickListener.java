package com.example.better_waves.ui.main;


import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

import com.example.better_waves.MainActivity;

public class MyOnClickListener implements View.OnClickListener {
    private int song_id;
    private Song song;

    MyOnClickListener(Song song) {
        this.song = song;
        this.song_id = song.getId();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Play Song " + song_id, Toast.LENGTH_SHORT).show();
        MainActivity.setCurrentSong(song);
        MainActivity.playSong(v, song_id);
    }
}