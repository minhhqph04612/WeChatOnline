package com.minh.wechatonline.model;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by HP on 3/31/2018.
 */

public class SaveDataCurrent extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
