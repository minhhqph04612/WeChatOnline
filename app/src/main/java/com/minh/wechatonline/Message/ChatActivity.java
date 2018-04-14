package com.minh.wechatonline.Message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.minh.wechatonline.Adapter.MessageAdapter;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private DatabaseReference rootDatabase;
    private ImageView btnAdd, btnSendMail;
    private RecyclerView messageList;
    private SwipeRefreshLayout refreshLayout;
    //private ListView messageList;
    private EditText edtContent;
    private String chat_user_id;

    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private final List<Message> arrMessage = new ArrayList<>() ;

    private MessageAdapter adapter;
    private static final int TOTAL_ITEM_TO_LOAD= 10;
    private int Current_Page= 2;
    private static final int GALLARY_PICK = 1;
    private StorageReference messageImageStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        edtContent = (EditText) findViewById(R.id.edtContent);

        rootDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        chat_user_id = getIntent().getStringExtra("user_id");
        String chat_user_email = getIntent().getStringExtra("email");
        btnAdd = (ImageView) findViewById(R.id.btnAdd);
        btnSendMail = (ImageView) findViewById(R.id.btnSendMail);
        adapter = new MessageAdapter(arrMessage);
        messageList = (RecyclerView) findViewById(R.id.listMessages);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        messageList.setHasFixedSize(true);
        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.setAdapter(adapter);
        messageImageStorage = FirebaseStorage.getInstance().getReference();
        loadMessages();

        actionBar.setTitle(chat_user_email);

        rootDatabase.child("Chat").child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(chat_user_id)) {
                    Map chatAddMap = new HashMap();
                    chatAddMap.put("seen", false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);
                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/" + current_user_id + "/" + chat_user_id, chatAddMap);
                    chatUserMap.put("Chat/" + chat_user_id + "/" + current_user_id, chatAddMap);
                    rootDatabase.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Log.d("CHAT_LOG", databaseError.getMessage().toString());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallaryIntent =  new Intent();
                gallaryIntent.setType("image/*");
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallaryIntent,"Select Image"),GALLARY_PICK);
            }
        });
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessenger();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Current_Page++;
                arrMessage.clear();
                loadMessages();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLARY_PICK && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            final String current_user_ref = "messages/" + current_user_id + "/" + chat_user_id;
            final String chat_user_ref = "messages/" + chat_user_id + "/" + current_user_id;

            DatabaseReference user_message_push = rootDatabase.child("messages")
                    .child(current_user_id).child(chat_user_id).push();

            final String push_id = user_message_push.getKey();

            StorageReference filePath =  messageImageStorage.child("message_images").child(push_id+".jpg");
            filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        String download_url = task.getResult().getDownloadUrl().toString();

                        Map messageMap = new HashMap();
                        messageMap.put("message", download_url);
                        messageMap.put("seen", false);
                        messageMap.put("type", "text");
                        messageMap.put("time",ServerValue.TIMESTAMP);
                        messageMap.put("from",current_user_id);
                        //messageMap.put("image",)
                        Map messageUserMap = new HashMap();
                        messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
                        messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

                        edtContent.setText("");
                        rootDatabase.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError!= null){
                                    Log.d("CHAT_LOG",databaseError.getMessage().toString());
                                }
                            }
                        });
                    }
                }
            });

        }
    }
    //Load Message
    private void loadMessages() {
        DatabaseReference messageDatabase =  rootDatabase.child("messages").child(current_user_id).child(chat_user_id);

        Query messageQuery =  messageDatabase.limitToLast(Current_Page + TOTAL_ITEM_TO_LOAD);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Message message = dataSnapshot.getValue(Message.class);
                arrMessage.add(message);
                adapter.notifyDataSetChanged();
                messageList.scrollToPosition(arrMessage.size()-1);
                refreshLayout.setRefreshing(false);
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
    private void sendMessenger() {
        String message = edtContent.getText().toString();

        if (!TextUtils.isEmpty(message)) {
            String current_user_ref = "messages/" + current_user_id + "/" + chat_user_id;
            String chat_user_ref = "messages/" + chat_user_id + "/" + current_user_id;

            DatabaseReference user_message_push = rootDatabase.child("messages")
                    .child(current_user_id).child(chat_user_id).push();

            String push_id = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time",ServerValue.TIMESTAMP);
            messageMap.put("from",current_user_id);
            //messageMap.put("image",)
            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            edtContent.setText("");
            rootDatabase.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d("CHAT_LOG", databaseError.getMessage().toString());

                    }
                }
            });
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
    //        rootDatabase.child("User").child(chat_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String status =  dataSnapshot.child("status").getValue().toString();
//                if(status.equals("online")){
//
//                }else{
//                    GetTimeAgo getTimeAgo =  new GetTimeAgo();
//                    long lastTime =  Long.parseLong(status);
//                    String lastSeenTime =  getTimeAgo.getTimeAgo(lastTime,getApplicationContext());
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

}
