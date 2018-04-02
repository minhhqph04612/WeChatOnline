package com.minh.wechatonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.Holder.FriendHolder;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.User;

public class AllUsersActivity extends AppCompatActivity {

//    private ListView list_User;
    private RecyclerView list_Users;
    private DatabaseReference userDatabase;
    private FirebaseListAdapter<User>  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");
//        list_User = (ListView) findViewById(R.id.list_users);
        list_Users = (RecyclerView) findViewById(R.id.list_users);
        userDatabase.keepSynced(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        adapter = new FirebaseListAdapter<User>(this,User.class,R.layout.user_single_layout,userDatabase) {
//            @Override
//            protected void populateView(View view, User user, int position) {
//                TextView email = (TextView) view.findViewById(R.id.user_single_email);
//                TextView status = (TextView) view.findViewById(R.id.user_single_status);
//                email.setText(user.getEmail());
//                status.setText(user.getStatus());
//                list_Users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        final String user_id = getRef(i).getKey();
//                        Intent intent =  new Intent(AllUsersActivity.this,UserActivity.class);
//                        intent.putExtra("user_id",user_id);
//                        startActivity(intent);
//                    }
//                });
//            }
//        };
        FirebaseRecyclerAdapter<User,FriendHolder> adapter = new FirebaseRecyclerAdapter<User, FriendHolder>(
                User.class,
                R.layout.user_single_layout,
                FriendHolder.class,
                userDatabase
        ) {
            @Override
            protected void populateViewHolder(FriendHolder viewHolder, User user, int position) {
                viewHolder.setEmail(user.getEmail());
            }
        };list_Users.setAdapter(adapter);
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
                startActivity(new Intent(AllUsersActivity.this, SearchActivity.class));
                return true;
        }
        return true;
    }

}
