package com.example.better_waves.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.better_waves.R;

import java.util.List;

public class RecyclerAdapter_Albums extends RecyclerView.Adapter<RecyclerAdapter_Albums.MyViewHolder> {

    Context mContext;
    List<Album> albums;

    public RecyclerAdapter_Albums(Context mContext, List<Album> albums) {
        this.mContext = mContext;
        this.albums = albums;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_album, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.album_name.setText(albums.get(position).getTitle());
        myViewHolder.artist_name.setText(albums.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class MyViewHolder extends ViewHolder{

        private TextView album_name;
        private TextView artist_name;

        public MyViewHolder(View itemView){
            super(itemView);

            album_name = (TextView) itemView.findViewById(R.id.album_name);
            artist_name = (TextView) itemView.findViewById(R.id.album_artist);

        }
    }
}
