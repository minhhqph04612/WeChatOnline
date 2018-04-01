package com.minh.wechatonline.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.Activity.UserActivity;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.Friend;
import com.minh.wechatonline.model.ListFriend;
import com.minh.wechatonline.model.User;

public class FriendsActivity extends AppCompatActivity {
    //TextView textView;
    private ListFriend<Friend> listFriend;
    private ListView list_User;
    private DatabaseReference userDatabase;

    private FirebaseListAdapter<User> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        listFriend = new ListFriend<>();
        list_User = (ListView) findViewById(R.id.list_user);

        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new FirebaseListAdapter<User>(this,User.class,R.layout.user_single_layout,userDatabase) {

            @Override
            protected void populateView(View view, User user, int position) {
                TextView email = (TextView) view.findViewById(R.id.user_single_email);
                TextView status = (TextView) view.findViewById(R.id.user_single_status);
                email.setText(user.getEmail());
                status.setText(user.getStatus());
                //Toast.makeText(UserActivity.this,user_id,Toast.LENGTH_LONG).show();
                list_User.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final String user_id = getRef(i).getKey();
                        Intent intent =  new Intent(FriendsActivity.this,UserActivity.class);

                        intent.putExtra("user_id",user_id);
                        //Toast.makeText(FriendsActivity.this,listFriend.setListFriend(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
            }
        };list_User.setAdapter(adapter);
    }


}
