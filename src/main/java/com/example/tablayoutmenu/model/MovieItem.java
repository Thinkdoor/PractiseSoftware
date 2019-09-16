package com.example.tablayoutmenu.model;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/6/24.
 */

public class MovieItem {
    private String title;
    private MovieImage images;
    private String alt;
    private String[] genres;
    private String[] durations;
    private String year;
    private Rating rating;

    public String[] getGenres() {
        return genres;
    }

    public String[] getDurations() {
        return durations;
    }

    public String getYear() {
        return year;
    }

    public Rating getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public MovieImage getImages() {
        return images;
    }

    public String getAlt() {
        return alt;
    }

    public String toString(String[] strings){
        String s = "";
        for(int i = 0;i < strings.length;i++){
            if(i==strings.length-1)
                s+= strings[i];
            else
                s+= strings[i]+",";
        }
        return s;
    }
}
