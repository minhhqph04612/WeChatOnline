package com.minh.wechatonline.model;

/**
 * Created by HP on 3/28/2018.
 */

public class User {
    //public String name;
    public String email;



    public String status;
    public User(){}

    public User(String email,String status) {
        this.email = email;
        this.status = status;
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
}
