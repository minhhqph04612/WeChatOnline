package com.minh.wechatonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.User;

public class UserActivity extends AppCompatActivity {
   // Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<User,UsersViewHolder>  firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(
                User.class,
                R.layout.user_single_layout,
                UsersViewHolder.class,
                userDatabase
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, User user, int position) {
                usersViewHolder.setEmail(user.getEmail());
                usersViewHolder.setStatus(user.getStatus());
                final String user_id  =  getRef(position).getKey();
                usersViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =  new Intent(UserActivity.this,ProfileActivity.class);
                        intent.putExtra("user_id",user_id);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public class UsersViewHolder extends RecyclerView.ViewHolder {
        View view;
        public UsersViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setEmail(String email) {
            TextView userEmailView = view.findViewById(R.id.user_single_email);
            userEmailView.setText(email);
        }
        public void setStatus(String status){
            TextView userStatusView = view.findViewById(R.id.user_single_status);
            userStatusView.setText(status);
        }
    }
}
