package com.example.tablayoutmenu.model;

/**新闻详情
 * @author thinkdoor
 */

public class NewsDetail {

    private String body;

    private String image_source;

    private String title;

    private String image;

    private String share_url;

    private String[] images;

    private long id;

    private String[] css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
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

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getCss() {
        return css;
    }

    public void setCss(String[] css) {
        this.css = css;
    }
}
