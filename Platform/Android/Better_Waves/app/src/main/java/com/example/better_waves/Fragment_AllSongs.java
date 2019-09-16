package com.example.better_waves;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.better_waves.ui.main.RecyclerAdapter_AllSongs;
import com.example.better_waves.ui.main.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_AllSongs extends Fragment {

    private View v;
    private RecyclerView rv;
    private List<Song> songs;

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

        songs = new ArrayList<>();
        songs.add(new Song("Shape of you", "shapes", "Ed Sheeran"));
        songs.add(new Song("In the end", "Living Things", "Linkin Park"));
        songs.add(new Song("Lost in the echo", "Living Things", "Linkin Park"));
        songs.add(new Song("Burn it down", "Meteora", "Linkin Park"));
    }
}
