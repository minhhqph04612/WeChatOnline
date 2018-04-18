package com.minh.wechatonline.model;

/**
 * Created by HP on 3/28/2018.
 */

public class Friend {
    private String id;
    public String date;
    public String image;
    public Friend() {
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Friend(String date, String image) {
        this.id = getId();
        this.date = date;
        this.image =  image;
    }

}
