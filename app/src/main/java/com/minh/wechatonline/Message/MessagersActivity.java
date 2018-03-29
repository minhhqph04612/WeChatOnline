package com.minh.wechatonline.Message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.R;

public class MessagersActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagers);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        myRef.setValue("Hello","world");
    }

}
