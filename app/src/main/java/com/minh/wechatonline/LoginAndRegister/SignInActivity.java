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
import com.minh.wechatonline.MainActivity;
import com.minh.wechatonline.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtEmail,edtPassword;
    Button btnSignIn, btnSignUp;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignIn = (Button) findViewById(R.id.btnDangNhap);
        btnSignUp = (Button) findViewById(R.id.btnDangKy);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

    }
    private void DangNhap(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(SignInActivity.this,"Xac nhan email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(SignInActivity.this,"Xac nhan password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Dang Dang Nhap...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(),"Dang Nhap thanh cong",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Dang Nhap that bai",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    @Override
    public void onClick(View view) {
        if(view == btnSignIn){
            DangNhap();
        }
        if(view == btnSignUp){
            finish();
            startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        }
    }
}



