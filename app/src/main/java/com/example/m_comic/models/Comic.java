package com.example.m_comic.models;

import java.util.ArrayList;

public class Comic {

    private String id, user_id;
    private ArrayList<ComicDetail> comic_details;

    public Comic() {}

    public Comic(String id, String user_id, ArrayList<ComicDetail> comicDetails) {
        this.id = id;
        this.user_id = user_id;
        this.comic_details = comicDetails;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ComicDetail> getComic_details() {
        return comic_details;
    }

    public void setComic_details(ArrayList<ComicDetail> comic_details) {
        this.comic_details = comic_details;
    }
}
