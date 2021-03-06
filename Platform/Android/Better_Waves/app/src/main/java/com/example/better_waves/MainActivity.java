package com.example.better_waves;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.better_waves.ui.main.SectionsPagerAdapter;
import com.example.better_waves.ui.main.SeekBarUpdater;
import com.example.better_waves.ui.main.Song;
import com.example.better_waves.ui.main.UserToken;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    public static MediaPlayer player;
    private static FloatingActionButton fab;
    public static SeekBar seekBar;
    public static TextView currentSongTitle;
    public static String defaultSongTitle;

    public static TextView currentTime;
    public static TextView totalTime;
    static String emptyTime;

    private static String stream_base_url;


    private static Drawable playImage;
    private static Drawable pauseImage;

    private static SeekBarUpdater seekBarUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        stream_base_url = context.getResources().getString(R.string.base_url) + "stream/";

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.view_pager);
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fab = findViewById(R.id.play_pause);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (player.isPlaying()) {
                pause(view);
            } else {
                play(view);
            }
            }
        });

        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

//                seekBarHint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                if (fromTouch){
                    seekBar.setProgress(progress);
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                    mediaPlayer.seekTo(seekBar.getProgress());
//                }
            }
        });

        playImage = ContextCompat.getDrawable(MainActivity.this, android.R.drawable.ic_media_play);
        pauseImage = ContextCompat.getDrawable(MainActivity.this, android.R.drawable.ic_media_pause);

        currentTime = findViewById(R.id.currentDuration);
        totalTime = findViewById(R.id.totalDuration);
        emptyTime = currentTime.getText().toString();
        currentSongTitle = findViewById(R.id.currentSong);
        defaultSongTitle = currentSongTitle.getText().toString();
    }

    public static void pause(View v) {
        fab.setImageDrawable(MainActivity.playImage);
        player.pause();
    }

    public static void play(View v) {
        fab.setImageDrawable(pauseImage);
        player.start();
        seekBarUpdater = new SeekBarUpdater();
        seekBarUpdater.start();
    }

    public static void playSong(View view, int song_id) {
        if (player == null) {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        resetPlayer();

        String url = stream_base_url + song_id;
        Uri uri = Uri.parse(url);

        try {
            player.setDataSource(MainActivity.context, uri, UserToken.getHeaderToken());
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            player.prepareAsync();
            play(view);
        } catch (IllegalArgumentException | SecurityException | IOException e) {
            Toast.makeText(MainActivity.context, "Error occurred playing song",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void resetPlayer(){
        player.stop();
        player.reset();
        seekBar.setProgress(0);
        fab.setImageDrawable(playImage);
        currentTime.post(new Runnable() {
            @Override
            public void run() {
                currentTime.setText(emptyTime);
                totalTime.setText(emptyTime);
            }
        });
    }

    public static void setCurrentSong(Song song){
        currentSongTitle.setText(song.getTitle());
    }
}
