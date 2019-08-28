package com.example.better_waves.ui.main;

import java.util.ArrayList;
import java.util.List;

public class songs {
    private String url;
    private String album;
    private String title;
    private String file;
    private String genre;
    public songs() {
    }

    public String getUrl() {
        return url;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public String getFile() {
        return file;
    }

    public String getGenre() {
        return genre;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
