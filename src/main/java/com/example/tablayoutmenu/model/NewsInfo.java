package com.example.tablayoutmenu.model;

import java.util.Arrays;

/**新闻类
 * @author thinkdoor
 */

public class NewsInfo {

    private String[] images;

    private String image;

    private int type;

    private long id;

    private String ga_prefix;

    private String title;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "images=" + Arrays.toString(images) +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
