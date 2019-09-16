package com.example.better_waves.ui.main;

public class Song {
    String title;
    String album;
    String artist;

    public Song(String title, String album, String artist) {
        this.title = title;
        this.album = album;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
