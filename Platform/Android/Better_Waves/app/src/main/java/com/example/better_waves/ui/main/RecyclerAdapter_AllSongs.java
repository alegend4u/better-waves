package com.example.better_waves.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.Toast;

import com.example.better_waves.MainActivity;
import com.example.better_waves.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter_AllSongs extends RecyclerView.Adapter<RecyclerAdapter_AllSongs.MyViewHolder> {

    Context mContext;
    List<Song> songs;

    public RecyclerAdapter_AllSongs(Context mContext, List<Song> songs) {
        this.mContext = mContext;
        this.songs = songs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_song, viewGroup, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Song song = songs.get(position);
        myViewHolder.song_id = song.getId();
        myViewHolder.song_name.setText(song.getTitle());
        myViewHolder.album_name.setText(song.getAlbum());
        myViewHolder.artist_name.setText(song.getArtist());

        MyOnClickListener listener = new MyOnClickListener(song.getId());
        myViewHolder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MyViewHolder extends ViewHolder{
        public int song_id;
        private TextView song_name;
        private TextView album_name;
        private TextView artist_name;

        public MyViewHolder(View itemView){
            super(itemView);
            song_name = (TextView) itemView.findViewById(R.id.song_name);
            album_name = (TextView) itemView.findViewById(R.id.song_album);
            artist_name = (TextView) itemView.findViewById(R.id.song_artist);

        }
    }
}
