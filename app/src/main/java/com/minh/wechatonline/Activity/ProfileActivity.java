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

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference userDatabase;
    DatabaseReference friendDatabase;
    FirebaseUser userCurrent;
    private Toolbar toolbar;
    TextView tvEmail,tvStatus;
    Button btnRequest;
    private ProgressDialog progressDialog;
    String current_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnRequest = (Button) findViewById(R.id.btnRequest);
        current_state = "not_friend";

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading user data");
        progressDialog.setMessage("please wait while we are loading user's data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
         final String user_id = getIntent().getStringExtra("user_id");
        //userCurrent = FirebaseAuth.getInstance().getCurrentUser();
        tvEmail.setText(user_id);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
        friendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends_req");
        userCurrent = FirebaseAuth.getInstance().getCurrentUser();
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                tvEmail.setText(email);
                tvStatus.setText(status);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "has some error", Toast.LENGTH_SHORT).show();
            }
        });
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_state.equals("not_friend")){

                    friendDatabase.child(userCurrent.getUid()).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                friendDatabase.child(user_id).child(userCurrent.getUid()).child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ProfileActivity.this," Request Send Successful ",Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }else{
                                Toast.makeText(ProfileActivity.this,"Failed Sending Request",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

}
