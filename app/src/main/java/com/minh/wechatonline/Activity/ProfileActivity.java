package com.minh.wechatonline.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minh.wechatonline.R;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference userDatabase;
    private FirebaseUser userCurrent;
    TextView tvEmail,tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvEmail = (TextView) findViewById(R.id.text1);
        tvStatus =(TextView) findViewById(R.id.text2);
        userCurrent = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = userCurrent.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(current_id);
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();

                tvEmail.setText(email);
                tvStatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
