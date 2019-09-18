package com.example.better_waves;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.better_waves.ui.main.RecyclerAdapter_AllSongs;
import com.example.better_waves.ui.main.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_AllSongs extends Fragment {

    private View v;
    private RecyclerView rv;
    private List<Song> songs;
    String base_url;

    public Fragment_AllSongs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.all_songs, container, false);
        rv = (RecyclerView) v.findViewById(R.id.songs_list);
        RecyclerAdapter_AllSongs recyclerAdapter = new RecyclerAdapter_AllSongs(getContext(), songs);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context;

        songs = new ArrayList<>();
        context = getContext();

        base_url = context.getResources().getString(R.string.base_url);
        String url = base_url + "songs";

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        final RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String jsonObject = new String(response);
                            JsonArray ja = new JsonParser().parse(jsonObject).getAsJsonArray();
                            for (JsonElement je : ja) {
                                JsonObject jo = je.getAsJsonObject();
                                String url = jo.get("url").getAsString();
                                int id = Fragment_AllSongs.getIdFromUrl(url);
                                assert id != -1;
                                final String title = jo.get("title").getAsString();
                                final String genre = jo.get("genre").getAsString();
                                String album_url = jo.get("album").getAsString();
                                String artist_url = jo.get("artist").getAsString();
//                                System.out.println("Added: " + title);
                                songs.add(new Song(id, title, album_url, artist_url, genre));
                            }

                            rv = (RecyclerView) v.findViewById(R.id.songs_list);
                            RecyclerAdapter_AllSongs recyclerAdapter = new RecyclerAdapter_AllSongs(getContext(), songs);
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv.setAdapter(recyclerAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public static int getIdFromUrl(String url) {
        String[] slices = url.split("/");
        for (String slice : slices) {
            try {
                return Integer.parseInt(slice);
            } catch (NumberFormatException nfe) {
                continue;
            }
        }
        return -1;
    }
}
