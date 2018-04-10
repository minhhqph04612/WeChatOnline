package com.minh.wechatonline.model;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by HP on 3/31/2018.
 */

public class SaveDataCurrent extends Application{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userDatabase;
    @Override
    public void onCreate() {
        super.onCreate();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
//        Picasso build = builder.build();
//        build.setIndicatorsEnabled(true);
//        build.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(build);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        userDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseAuth.getCurrentUser().getUid());
//        userDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
