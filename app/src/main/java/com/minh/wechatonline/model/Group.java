package com.minh.wechatonline.model;

import java.util.List;

/**
 * Created by HP on 4/16/2018.
 */

public class Group {
    private String idGroup;
    private String nameGroup;
    private List<Friend> listFriend;
    public Group(){}

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public List<Friend> getListFriend() {
        return listFriend;
    }

    public void setListFriend(List<Friend> listFriend) {
        this.listFriend = listFriend;
    }

    public Group(String idGroup, String nameGroup, List<Friend> listFriend) {
        this.idGroup = idGroup;
        this.nameGroup = nameGroup;
        this.listFriend = listFriend;
    }
}
