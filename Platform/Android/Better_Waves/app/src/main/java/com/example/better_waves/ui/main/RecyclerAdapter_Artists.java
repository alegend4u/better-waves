package com.example.better_waves.ui.main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.better_waves.R;

import java.util.List;

public class RecyclerAdapter_Artists extends RecyclerView.Adapter<RecyclerAdapter_Artists.MyViewHolder> {

    Context mContext;
    List<Artist> artists;

    public RecyclerAdapter_Artists(Context mContext, List<Artist> artists) {
        this.mContext = mContext;
        this.artists = artists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_artist, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.artist_name.setText(artists.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class MyViewHolder extends ViewHolder{

        private TextView artist_name;

        public MyViewHolder(View itemView){
            super(itemView);

            artist_name = (TextView) itemView.findViewById(R.id.album_artist);

        }
    }
}
