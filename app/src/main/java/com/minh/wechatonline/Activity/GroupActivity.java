package com.minh.wechatonline.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.Adapter.ChatAllAdapter;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.ChatAllMessage;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private EditText inputMessage;
    private FloatingActionButton btnSendMail;
    private ListView messageList;
    private RecyclerView listMessages;
    private FirebaseUser firebaseAuth;
    private DatabaseReference rootDatabase;
    private DatabaseReference chatAllDatabase;
    private DatabaseReference userDatabase;

    private String current_user_id;
    private ChatAllAdapter adapter;
    private ArrayList<ChatAllMessage> arrMessage;

//    private static final int TOTAL_ITEM_TO_LOAD= 10;
//    private int Current_Page= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Group Chatting");
        chatAllDatabase = FirebaseDatabase.getInstance().getReference().child("ChatAll");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        firebaseAuth =  FirebaseAuth.getInstance().getCurrentUser();
        final String current_user_email = firebaseAuth.getEmail();
        btnSendMail = (FloatingActionButton) findViewById(R.id.fab);
        inputMessage = (EditText) findViewById(R.id.input);

        arrMessage = new ArrayList<>() ;
        adapter =  new ChatAllAdapter(this,R.layout.group_chat_item_message_layout,arrMessage);
        messageList =  (ListView) findViewById(R.id.list_of_messages);
        messageList.setAdapter(adapter);
        //current_user_id = getIntent().getStringExtra("user_id");
//        Toast.makeText(GroupActivity.this,current_user_id,Toast.LENGTH_SHORT).show();

        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message =  inputMessage.getText().toString();
                chatAllDatabase.push().setValue(new ChatAllMessage(message ,current_user_email));
                inputMessage.setText("");
                Toast.makeText(GroupActivity.this,"Send message successful",Toast.LENGTH_SHORT).show();
            }
        });
        chatAllDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatAllMessage message = dataSnapshot.getValue(ChatAllMessage.class);
                arrMessage.add(message);
                adapter.notifyDataSetChanged();
                messageList.smoothScrollToPosition(arrMessage.size()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
    public void loadMessages(){

    }


}
