package com.minh.wechatonline.LoginAndRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.minh.wechatonline.MainActivity;
import com.minh.wechatonline.R;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp, btnSignIn;
    EditText edtEmail, edtPassword;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        progressDialog = new ProgressDialog(this);
        btnSignUp = (Button) findViewById(R.id.btnDangKy);
        btnSignIn = (Button) findViewById(R.id.btnDangNhap);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);


    }

    public void DangKy(View view) {
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Dang xac nhan user...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //User user = new User(email,password);
                            //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            //databaseReference.child("User").child(firebaseUser.getUid()).setValue(user);
                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            finish();


                            Toast.makeText(SignUpActivity.this, "Dang Ky thanh Cong", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Dang Ky that bai", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void DangNhap(View view) {
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
    }

}
