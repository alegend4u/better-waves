package com.example.better_waves.ui.main;


import android.view.View;
import android.widget.Toast;

import com.example.better_waves.MusicPlayer;

public class MyOnClickListener implements View.OnClickListener {
    int song_id;
    static MusicPlayer player;

    public MyOnClickListener(int song_id) {
        this.song_id = song_id;
    }

    public static MusicPlayer getPlayer() {
        return player;
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(v.getContext(), "Play Song " + String.valueOf(song_id), Toast.LENGTH_SHORT).show();
        player = new MusicPlayer(v.getContext());
        player.playSong(v, song_id);
    }
}