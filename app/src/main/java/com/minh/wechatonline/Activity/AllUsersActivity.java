package com.minh.wechatonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.Holder.UserHolder;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.User;

public class AllUsersActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private RecyclerView list_Users;
    private DatabaseReference userDatabase;
    private FirebaseListAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");
//        list_User = (ListView) findViewById(R.id.list_users);

        userDatabase.keepSynced(true);
        list_Users = (RecyclerView) findViewById(R.id.list_users);
        list_Users.setHasFixedSize(true);
        list_Users.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<User, UserHolder> adapter = new FirebaseRecyclerAdapter<User, UserHolder>(
                User.class,
                R.layout.user_single_layout,
                UserHolder.class,
                userDatabase
        ) {
            @Override
            protected void populateViewHolder(UserHolder viewHolder, User user, final int position) {
                viewHolder.setEmail(user.getEmail());
                viewHolder.setStatus(user.getStatus());
                viewHolder.setImage(user.getImage(), getApplicationContext());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String user_id = getRef(position).getKey();
                        Intent intent = new Intent(AllUsersActivity.this, UserActivity.class);
                        intent.putExtra("user_id", user_id);
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
                startActivity(new Intent(AllUsersActivity.this, SearchActivity.class));
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }


//    @Nullable
//    @Override
//    public Intent getSupportParentActivityIntent() {
//        super.getSupportParentActivityIntent();
//        Intent intent = new Intent(AllUsersActivity.this, MainActivity.class);
//        return
//    }
}
