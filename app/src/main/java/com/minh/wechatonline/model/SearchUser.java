package com.minh.wechatonline.model;

/**
 * Created by HP on 4/1/2018.
 */

public class SearchUser {
    String email, status,image;

    public SearchUser() {
    }

    public SearchUser(String email, String status,String image) {
        this.email = email;
        this.status = status;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
