package com.example.better_waves.ui.main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.better_waves.R;

import java.util.List;

public class RecyclerAdapter_Recommended extends RecyclerView.Adapter<RecyclerAdapter_Recommended.MyViewHolder> {

    Context mContext;
    List<Song> songs;

    public RecyclerAdapter_Recommended(Context mContext, List<Song> songs) {
        this.mContext = mContext;
        this.songs = songs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_recomended, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Song song = songs.get(position);
        myViewHolder.song_id = song.getId();
        myViewHolder.song_name.setText(song.getTitle());
        myViewHolder.album_name.setText(song.getAlbum());
        myViewHolder.artist_name.setText(song.getArtist());

        MyOnClickListener listener = new MyOnClickListener(song);
        myViewHolder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MyViewHolder extends ViewHolder {
        public int song_id;
        private TextView song_name;
        private TextView album_name;
        private TextView artist_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            song_name = (TextView) itemView.findViewById(R.id.recommended_song_name);
            album_name = (TextView) itemView.findViewById(R.id.recommended_song_album);
            artist_name = (TextView) itemView.findViewById(R.id.recommended_song_artist);

        }
    }
}
