package com.minh.wechatonline.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minh.wechatonline.R;

import java.text.DateFormat;
import java.util.Date;

public class UserActivity extends AppCompatActivity {
    //    Toolbar toolbar;
    private TextView tvEmail, tvStatus;
    private Button btnRequest;
    private FirebaseUser userCurrent;
    private DatabaseReference userDatabase;
    private DatabaseReference friendReqDatabase;
    private DatabaseReference friendDatabase;


    private Toolbar toolbar;

    private ProgressDialog progressDialog;
    private String current_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        toolbar = (Toolbar) findViewById(R.id.user_action_bar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("All User");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        recyclerView = (RecyclerView) findViewById(R.id.user_list);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String user_id = getIntent().getStringExtra("user_id");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
        friendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friends_req");
        friendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        userCurrent = FirebaseAuth.getInstance().getCurrentUser();
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnRequest = (Button) findViewById(R.id.btnRequest);


        current_state = "not_friend";
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading user data");
        progressDialog.setMessage("please wait while we are loading user's data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //userCurrent = FirebaseAuth.getInstance().getCurrentUser();


        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                tvEmail.setText(email);
                tvStatus.setText(status);

                //Toast.makeText(ProfileActivity.this,user_id,Toast.LENGTH_SHORT).show();

                //--------------- FRIEND LIST / REQUEST FEATURE-----------

                friendReqDatabase.child(userCurrent.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(user_id)) {
                            String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();
                            if (req_type.equals("received")) {
                                current_state = "req_received";
                                btnRequest.setText("Accept Friend Request");
                            } else if (req_type.equals("sent")) {
                                current_state = "req_sent";
                                btnRequest.setText("Cancel Friend Request");
                            }
                            progressDialog.dismiss();
                        }else{
                            friendDatabase.child(userCurrent.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(user_id)) {
                                        current_state = "friend";
                                        btnRequest.setText("UnFriend This Person");
                                    }progressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    progressDialog.dismiss();
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(UserActivity.this, "has some error", Toast.LENGTH_SHORT).show();
            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRequest.setEnabled(false);
                ///      Not Friend state
                if (current_state.equals("not_friend")) {

                    friendReqDatabase.child(userCurrent.getUid()).child(user_id).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                friendReqDatabase.child(user_id).child(userCurrent.getUid()).child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        current_state.equals("req_sent");
                                        btnRequest.setText("Cancel Friend Request");
                                        Toast.makeText(UserActivity.this, " Request Send Successful ", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            } else {
                                Toast.makeText(UserActivity.this, "Failed Sending Request", Toast.LENGTH_SHORT).show();
                            }btnRequest.setEnabled(true);
                        }
                    });

                }
                // cancel sent request
                if (current_state.equals("req_sent")) {
                    friendReqDatabase.child(userCurrent.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            friendReqDatabase.child(user_id).child(userCurrent.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    btnRequest.setEnabled(true);
                                    current_state.equals("not_friend");
                                    btnRequest.setText("Send Friend Request");
                                    Toast.makeText(UserActivity.this, " Request Send Successful ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                //------------------REQ RECEIVED STATE----------------------
                if (current_state.equals("req_received")) {
                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());
                    friendDatabase.child(userCurrent.getUid()).child(user_id).setValue(currentDate)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            friendDatabase.child(user_id).child(userCurrent.getUid()).setValue(currentDate)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    friendReqDatabase.child(userCurrent.getUid()).child(user_id).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            friendReqDatabase.child(user_id).child(userCurrent.getUid()).removeValue()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    btnRequest.setEnabled(true);
                                                    current_state = "friend";
                                                    btnRequest.setText("UnFriend This Person");

                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }


}
