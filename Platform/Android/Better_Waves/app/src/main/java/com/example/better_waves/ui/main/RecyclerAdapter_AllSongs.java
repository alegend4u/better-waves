package com.example.better_waves.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.example.better_waves.R;

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
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.song_name.setText(songs.get(position).getTitle());
        myViewHolder.album_name.setText(songs.get(position).getAlbum());
        myViewHolder.artist_name.setText(songs.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MyViewHolder extends ViewHolder{

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
