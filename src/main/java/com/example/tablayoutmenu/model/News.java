package com.example.tablayoutmenu.model;

import java.util.List;

/** 新闻列表类
 * @author thinkdoor
 */

public class News {

    private String date;

    private List<NewsInfo> stories;

    private List<NewsInfo> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<NewsInfo> getStories() {
        return stories;
    }

    public void setStories(List<NewsInfo> stories) {
        this.stories = stories;
    }

    public List<NewsInfo> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<NewsInfo> top_stories) {
        this.top_stories = top_stories;
    }
}
