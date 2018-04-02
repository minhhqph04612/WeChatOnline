package com.minh.wechatonline.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.minh.wechatonline.R;

public class ListAllFriendActivity extends AppCompatActivity {
    private FirebaseUser user_Current;
    private DatabaseReference userDatabase;
    private DatabaseReference friendDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_friend);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
