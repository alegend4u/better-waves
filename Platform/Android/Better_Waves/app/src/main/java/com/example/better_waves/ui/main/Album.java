package com.example.better_waves.ui.main;

public class Album {
    String title;
    String artist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Album(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
}
