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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.better_waves.ui.main.Album;
import com.example.better_waves.ui.main.Artist;
import com.example.better_waves.ui.main.RecyclerAdapter_Albums;
import com.example.better_waves.ui.main.RecyclerAdapter_Artists;
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
public class Fragment_Artists extends Fragment {

    private View v;
    private RecyclerView rv;
    private List<Artist> artists;
    String base_url;


    public Fragment_Artists() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.artists, container, false);
        rv = (RecyclerView) v.findViewById(R.id.artist_list);
        RecyclerAdapter_Artists recyclerAdapter = new RecyclerAdapter_Artists(getContext(), artists);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Context context;

        artists = new ArrayList<>();
        context = getContext();

        base_url = context.getResources().getString(R.string.base_url);
        String url = base_url + "artists";
        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String jsonObject = new String(response);
                            JsonArray ja = new JsonParser().parse(jsonObject).getAsJsonArray();
                            for (JsonElement je : ja) {
                                JsonObject jo = je.getAsJsonObject();
                                final String title = jo.get("title").getAsString();

                                final Artist a = new Artist(title);
                                artists.add(a);
                            }

                            rv = (RecyclerView) v.findViewById(R.id.artist_list);
                            RecyclerAdapter_Artists recyclerAdapter = new RecyclerAdapter_Artists(getContext(), artists);
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
