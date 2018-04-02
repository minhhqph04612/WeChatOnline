package com.minh.wechatonline.model;

/**
 * Created by HP on 4/1/2018.
 */

public class SearchUser {
    String email, status;

    public SearchUser() {
    }

    public SearchUser(String email, String status) {
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
