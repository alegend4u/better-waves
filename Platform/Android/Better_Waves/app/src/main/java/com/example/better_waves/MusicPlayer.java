package com.example.better_waves;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;

import java.io.IOException;

public class MusicPlayer implements MediaPlayer.OnPreparedListener {
    Context context;
    static MediaPlayer mplayer;
    String stream_base_url;


    public MusicPlayer(Context context) {
        this.context = context;
        this.stream_base_url = context.getResources().getString(R.string.base_url) + "stream/";
    }

    public void pauseSong(View view) {
        mplayer.pause();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public void playSong(View view, int song_id) {
        if (mplayer == null) {
            mplayer = new MediaPlayer();
            mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        if(mplayer.isPlaying()){
            mplayer.stop();
//            mplayer.release();
            mplayer.reset();
        }
        String url = stream_base_url + String.valueOf(song_id);

        try {
            mplayer.setDataSource(url);
            mplayer.setOnPreparedListener(this);
            mplayer.prepareAsync();
            mplayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
