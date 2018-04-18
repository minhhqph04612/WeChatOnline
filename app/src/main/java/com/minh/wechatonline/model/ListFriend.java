package com.minh.wechatonline.model;

import java.util.ArrayList;

/**
 * Created by HP on 3/28/2018.
 */

public class ListFriend  {

    private ArrayList<Friend> listFriend;

    public ArrayList<Friend> getListFriend() {
        return listFriend;
    }

    public ListFriend(){
        listFriend = new ArrayList<>();
    }

    public String getEmailById(String id){
        for(Friend friend: listFriend){
            if(id.equals(friend.getId())){
                return friend.getId();

            }
        }
        return "";
    }

    public void setListFriend(ArrayList<Friend> listFriend) {
        this.listFriend = listFriend;
    }
}
