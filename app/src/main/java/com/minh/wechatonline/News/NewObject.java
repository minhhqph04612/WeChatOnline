package com.minh.wechatonline.News;

/**
 * Created by HP on 3/21/2018.
 */

public class NewObject {
    private String title;
    private String link;
    private String imageURL;
    private String pubDate;
    public NewObject(String title, String link , String imageURL) {
        // TODO Auto-generated constructor stub
        this.title = title;
        this.link = link;
        this.imageURL = imageURL;
        this.pubDate = pubDate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getPubDate() {
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
