package com.minh.wechatonline.model;

/**
 * Created by HP on 3/28/2018.
 */

public class User {
    //public String name;
    public String email;
    public String status;
    public String image;

    public User(){}
    public User(String email, String status){}

    public User(String email,String status,String image) {
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
