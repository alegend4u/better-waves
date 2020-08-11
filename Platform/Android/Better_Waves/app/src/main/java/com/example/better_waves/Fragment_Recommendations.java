package com.example.better_waves;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.better_waves.ui.main.RecyclerAdapter_Recommended;
import com.example.better_waves.ui.main.Song;
import com.example.better_waves.ui.main.UserToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Recommendations extends Fragment {

    private View v;
    private RecyclerView rv;
    private List<Song> songs;
    String base_url;
    //private ArrayList<String> ids = new ArrayList<String>();

    public Fragment_Recommendations() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recommendations, container, false);
        rv = (RecyclerView) v.findViewById(R.id.recommended_songs_list);
        RecyclerAdapter_Recommended recyclerAdapter = new RecyclerAdapter_Recommended(getContext(), songs);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context;

        songs = new ArrayList<>();
        context = getContext();

        base_url = context.getResources().getString(R.string.base_url);
        String url = base_url + "recommend";

        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String jsonObject = response;
                            JsonArray ja = new JsonParser().parse(jsonObject).getAsJsonArray();
                            for (JsonElement je : ja) {
                                JsonObject jo = je.getAsJsonObject();
                                String url = jo.get("url").getAsString();
                                int id = Fragment_AllSongs.getIdFromUrl(url);
                                final String title = jo.get("title").getAsString();
                                final String genre = jo.get("genre").getAsString();
                                final String album = jo.get("album_title").getAsString();
                                final String artist = jo.get("artist_title").getAsString();

                                final Song s = new Song(id, title, album, artist, genre);
                                songs.add(s);
                            }

                            rv = (RecyclerView) v.findViewById(R.id.recommended_songs_list);
                            RecyclerAdapter_Recommended recyclerAdapter = new RecyclerAdapter_Recommended(getContext(), songs);
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
        }){
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token " + UserToken.getToken());

                return headers;
            }
        };

        queue.add(stringRequest);
    }
}
