package com.example.better_waves;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.content.Context;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.better_waves.ui.main.MyCustomAdapter;
import com.example.better_waves.ui.main.songs;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.google.gson.*;

public class SongListActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    String base_url = "http://192.168.43.165:8000";
    MediaPlayer mplayer ;
    private Context context;
    List<songs> songslist;

    public void playSong(View view) {
        String url = base_url + "/stream/1";
        //mplayer = MediaPlayer.create(this, R.raw.heybaby);
        if(mplayer == null)
            mplayer = new MediaPlayer();
        // Set the media player audio stream type
        mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //Try to play music/audio from url
        try{
            mplayer.setDataSource(url);
            mplayer.setOnPreparedListener(this);
            mplayer.prepareAsync();
            //mplayer.prepare();

            // Start playing audio from http url
            //mplayer.start();

            // Inform user for audio streaming
           // Toast.makeText(mContext,"Playing",Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            // Catch the exception
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }
       /*    public void run() {

        int currentPosition = mplayer.getCurrentPosition();
        int total = mplayer.getDuration();


        while (mplayer != null && mplayer.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = mplayer.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }

            scrubber.setProgress(currentPosition);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
   }

    */
    public void pauseSong(View view) {
        mplayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        context = this;

        //Fetch the songs
        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);
        String url = base_url + "/stream/1";
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject jsonObject = null;
                        String jsonObject = null;
                        try {
                            jsonObject = new String(response);

                            List<songs> song1 = Arrays.asList(gson.fromJson(jsonObject/*.getJSONObject("data").toString()*/, songs[].class));
                            //String x = gson.toJson(song);
                            //t1.setText(x);
                             songslist = song1;
                            ArrayList<String> Title = new ArrayList<String>();
                            for (int i=0;i<songslist.size();i++){
                                Title.add(songslist.get(i).getTitle());
                            }
                            ListView songListView = (ListView) findViewById(R.id.listview);

                            //ArrayList<String> song = new ArrayList<String>(Arrays.asList("111,222,333,444,555,666".split(",")));
                            //song.add("O SAKI SAKI");
                            //song.add("shape of You");

                            //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,song);
                            //ArrayAdapter<String> list = new ArrayList<String>(Arrays.asList("111,222,333,444,555,666".split(",")));
                            songListView.setAdapter(new MyCustomAdapter(Title, context));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        //listView
        //mplayer = new MediaPlayer();



        /*final SeekBar scrubber = (SeekBar) findViewById(R.id.scrubber);
        scrubber.setMax(mplayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubber.setProgress(mplayer.getCurrentPosition());
            }
        }, 0, 2000);
        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mplayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}