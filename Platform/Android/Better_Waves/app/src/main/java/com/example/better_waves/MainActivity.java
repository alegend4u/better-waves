package com.example.better_waves;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.better_waves.ui.main.MyOnClickListener;
import com.example.better_waves.ui.main.RecyclerAdapter_AllSongs;
import com.example.better_waves.ui.main.SectionsPagerAdapter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity  {
    private Context context;
    private MusicPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        SectionsPagerAdapter pagerAdapter =  new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void pause(View v){
        player = MyOnClickListener.getPlayer();
        if(player.mplayer.isPlaying()) {
            player.pauseSong(v);
        }
    }

    public void play(View v){
        player = MyOnClickListener.getPlayer();
        if(player.mplayer.isPlaying() && v.equals(findViewById(R.id.play))){
            return;
        } else {
            player.mplayer.start();
        }
    }

}