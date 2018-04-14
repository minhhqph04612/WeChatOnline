package com.minh.wechatonline.Message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.minh.wechatonline.Activity.SearchActivity;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.Conversation;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagersActivity extends AppCompatActivity {

    private RecyclerView listConv;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference conversationDatabase;
    private DatabaseReference messagesDatabase;
    private DatabaseReference userDatabase;

    private String current_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagers);
        listConv = (RecyclerView)  findViewById(R.id.listConversation);
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        conversationDatabase = FirebaseDatabase.getInstance().getReference().child("Chat").child(current_user_id);
        conversationDatabase.keepSynced(true);
        userDatabase  = FirebaseDatabase.getInstance().getReference().child("User");

        messagesDatabase = FirebaseDatabase.getInstance().getReference().child("mesaages").child(current_user_id);
        messagesDatabase.keepSynced(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        listConv.setHasFixedSize(true);
        listConv.setLayoutManager(layoutManager);
        //Toast.makeText(MessagersActivity.this,user_id,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query conversationQuery = conversationDatabase.orderByChild("timestamp");
        FirebaseRecyclerAdapter<Conversation,ConversationHolder>  adapter = new FirebaseRecyclerAdapter<Conversation, ConversationHolder>(
                Conversation.class,
                R.layout.conversation_item_single_layout,
                ConversationHolder.class,
                conversationQuery

        ) {
            @Override
            protected void populateViewHolder(final ConversationHolder viewHolder, final Conversation conversation, int position) {
                final String list_user_id = getRef(position).getKey();
                Query lastMessageQuery =  messagesDatabase.child(list_user_id).limitToLast(1);
                lastMessageQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String message =  dataSnapshot.child("message").getValue().toString();
                        viewHolder.setMessage(message);

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
                userDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String email = dataSnapshot.child("email").getValue().toString();
                        String image =  dataSnapshot.child("image").getValue().toString();
                        String status = dataSnapshot.child("status").getValue().toString();
                        viewHolder.setEmail(email);
                        viewHolder.setStatus(status);
                        viewHolder.setImage(image, MessagersActivity.this);
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent chatIntent =  new Intent(MessagersActivity.this,ChatActivity.class);
                                chatIntent.putExtra("user_id",list_user_id);
                                chatIntent.putExtra("email",email);
                                startActivity(chatIntent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        listConv.setAdapter(adapter);
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
    public static class ConversationHolder extends RecyclerView.ViewHolder {
        View view;
        public ConversationHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setMessage(String message){
            TextView tvLastMessage = (TextView) view.findViewById(R.id.last_message);
            tvLastMessage.setText(message);
//            if(!isSeen){
//                tvLastMessage.setTypeface(tvLastMessage.getTypeface(), Typeface.BOLD);
//            }else{
//                tvLastMessage.setTypeface(tvLastMessage.getTypeface(), Typeface.NORMAL);
//            }
        }
        public void setEmail(String email){
            TextView tvEmail =  (TextView) view.findViewById(R.id.chat_user_email);
            tvEmail.setText(email);
        }
        public void setStatus(String status){
            TextView tvStatus =  (TextView) view.findViewById(R.id.user_single_status);
            tvStatus.setText(status);
        }
        public void setImage(String image, Context context){
            CircleImageView profileImage = (CircleImageView) view.findViewById(R.id.profileImage);
            Picasso.with(context).load(image).placeholder(R.drawable.ic_person_black_24dp).into(profileImage);
        }
    }

}
