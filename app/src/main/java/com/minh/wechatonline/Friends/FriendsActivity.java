package com.minh.wechatonline.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.Activity.SearchActivity;
import com.minh.wechatonline.Activity.UserActivity;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.User;

public class FriendsActivity extends AppCompatActivity {


    private ListView list_Users;

    private DatabaseReference userDatabase;
    private FirebaseListAdapter<User> adapter;

//    private FirebaseListAdapter<Friend> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        list_Users = (ListView) findViewById(R.id.list_user);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new FirebaseListAdapter<User>(this, User.class,R.layout.user_single_layout,userDatabase) {
            @Override
            protected void populateView(View view, User user, int position) {
                TextView email = (TextView) view.findViewById(R.id.user_single_email);
                TextView status = (TextView)view.findViewById(R.id.user_single_status);
                email.setText(user.getEmail());
                status.setText(user.getStatus());

                list_Users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final String user_id = getRef(i).getKey();
                        Intent intent  = new Intent(FriendsActivity.this, UserActivity.class);
                        intent.putExtra("user_id",user_id);
                        startActivity(intent);
                    }
                });
            }
        };
        list_Users.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.all_user_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(FriendsActivity.this, SearchActivity.class));
                return true;
        }
        return true;
    }
}
