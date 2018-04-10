package com.minh.wechatonline.Friends;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minh.wechatonline.Activity.SearchActivity;
import com.minh.wechatonline.Activity.UserActivity;
import com.minh.wechatonline.Message.ChatActivity;
import com.minh.wechatonline.Holder.FriendHolder;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.Friend;

public class FriendsActivity extends AppCompatActivity {


    //private ListView list_Users;
    private RecyclerView list_Friends;
    private DatabaseReference friendDatabase;
    private DatabaseReference userDatabase;
    private FirebaseAuth firebaseAuth;
    //    private FirebaseListAdapter<User> adapter;
    private String current_user_id;
//    private FirebaseListAdapter<Friend> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        //list_Users = (ListView) findViewById(R.id.list_user);
        list_Friends = (RecyclerView) findViewById(R.id.list_Friend);
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        friendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(current_user_id);
        friendDatabase.keepSynced(true);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        userDatabase.keepSynced(true);
        list_Friends.setHasFixedSize(true);
        list_Friends.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(FriendsActivity.this, current_user_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Friend, FriendHolder> adapter = new FirebaseRecyclerAdapter<Friend, FriendHolder>(
                Friend.class,
                R.layout.user_single_layout,
                FriendHolder.class,
                friendDatabase
        ) {
            @Override
            protected void populateViewHolder(final FriendHolder viewHolder, final Friend friend, final int position) {
                viewHolder.setDate(friend.getDate());
                viewHolder.setImage(friend.getImage(), getApplicationContext());
                final String list_user_id = getRef(position).getKey();
                userDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String userEmail = dataSnapshot.child("email").getValue().toString();
                        final String userStatus = dataSnapshot.child("status").getValue().toString();
//                        final String image = dataSnapshot.child("image").getValue().toString();
                        viewHolder.setEmailAndStatus(userEmail,userStatus);
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dialog dialog = new Dialog(FriendsActivity.this);
                                dialog.setContentView(R.layout.dialog_friend_item);
                                dialog.setTitle("Select Option");
                                Button btnSend =(Button) dialog.findViewById(R.id.btnSend);
                                Button btnProfile = (Button) dialog.findViewById(R.id.btnProfile);
                                btnSend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent chatIntent =  new Intent(FriendsActivity.this, ChatActivity.class);
                                        chatIntent.putExtra("user_id",list_user_id);
                                        chatIntent.putExtra("email",userEmail);
                                        chatIntent.putExtra("status",userStatus);
                                        startActivity(chatIntent);
                                    }
                                });
                                btnProfile.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent userIntent =  new Intent(FriendsActivity.this, UserActivity.class);
                                        userIntent.putExtra("user_id",list_user_id);
                                        startActivity(userIntent);
                                    }
                                });
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        list_Friends.setAdapter(adapter);
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

    public static class FriendsHolder extends RecyclerView.ViewHolder {
        View view;

        public FriendsHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setDate(String date) {
            TextView tvStatus = (TextView) view.findViewById(R.id.user_single_status);
            tvStatus.setText(date);
        }

        public void setEmail(String email) {
            TextView tvEmail = (TextView) view.findViewById(R.id.user_single_email);
            tvEmail.setText(email);
        }
    }
}
