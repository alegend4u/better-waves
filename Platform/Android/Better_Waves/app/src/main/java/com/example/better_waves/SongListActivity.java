//package com.example.better_waves;
//
//import android.media.AudioManager;
//import android.support.design.widget.TabItem;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ListView;
//import android.media.MediaPlayer;
//import android.widget.SeekBar;
//import android.content.Context;
//import android.widget.TextView;
//
//import com.android.volley.*;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.better_waves.ui.main.MyCustomAdapter;
//import com.example.better_waves.ui.main.songs;
//
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.google.gson.*;
//
//public class SongListActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
//    private Context context;
//    String base_url;
//    MediaPlayer mplayer;
//    List<songs> songslist;
//
//    public void playSong(View view) {
//        String url = base_url + "/stream/1";
//        if (mplayer == null)
//            mplayer = new MediaPlayer();
//        mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        //Try to play music/audio from url
//        try {
//            mplayer.setDataSource(url);
//            mplayer.setOnPreparedListener(this);
//            mplayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void pauseSong(View view) {
//        mplayer.pause();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_song_list);
//        context = this;
//        base_url = context.getResources().getString(R.string.base_url);
//        //Fetch the songs
////        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);
////        String url = base_url + "/stream/1";
////        GsonBuilder builder = new GsonBuilder();
////        final Gson gson = builder.create();
////        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        //JSONObject jsonObject = null;
////                        String jsonObject = null;
////                        try {
////                            jsonObject = new String(response);
////
////                            List<songs> song1 = Arrays.asList(gson.fromJson(jsonObject/*.getJSONObject("data").toString()*/, songs[].class));
////                            //String x = gson.toJson(song);
////                            //t1.setText(x);
////                            songslist = song1;
////                            ArrayList<String> Title = new ArrayList<String>();
////                            for (int i = 0; i < songslist.size(); i++) {
////                                Title.add(songslist.get(i).getTitle());
////                            }
////                            ListView songListView = (ListView) findViewById(R.id.listview);
////
////                            //ArrayList<String> song = new ArrayList<String>(Arrays.asList("111,222,333,444,555,666".split(",")));
////                            //song.add("O SAKI SAKI");
////                            //song.add("shape of You");
////
////                            //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,song);
////                            //ArrayAdapter<String> list = new ArrayList<String>(Arrays.asList("111,222,333,444,555,666".split(",")));
////                            songListView.setAdapter(new MyCustomAdapter(Title, context));
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    }
////                }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////            }
////        });
////
////        queue.add(stringRequest);
//    }
//
//    @Override
//    public void onPrepared(MediaPlayer mediaPlayer) {
//        mediaPlayer.start();
//    }
//}