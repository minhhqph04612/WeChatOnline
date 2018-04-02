package com.minh.wechatonline.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.Activity.SearchActivity;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.all_user_action_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_search:
                startActivity(new Intent(MessagersActivity.this, SearchActivity.class));
                return true;
        }
        return true;
    }
}
