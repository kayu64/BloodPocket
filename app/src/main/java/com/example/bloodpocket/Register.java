package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    //ProgressBar progressBar;
    private static final String USER = "user";
    private static final String TAG = "Register";
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName       = findViewById(R.id.fullName);
        mEmail          = findViewById(R.id.email);
        mPassword       = findViewById(R.id.password);
        mPhone          = findViewById(R.id.phone);
        mRegisterBtn    = findViewById(R.id.registerBtn);
        mLoginBtn       = findViewById(R.id.createText);
        //progressBar     = findViewById(R.id.fullName);

        fAuth           = FirebaseAuth.getInstance();
        database        = FirebaseDatabase.getInstance();
        mDatabase       = database.getReference(USER);


        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString().trim();
                String occupation ="-";
                String location = "-";
                String phone = mPhone.getText().toString().trim();
                User user = new User(email,password,fullName,phone,occupation,location);

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Emel diperlukan");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("kata laluan diperlukan");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("kata laluan metilah melebihi 5 karakter");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Pengguna Baru dicipta", Toast.LENGTH_SHORT).show();
                            String id = task.getResult().getUser().getUid();
                            mDatabase.child(id).setValue(user);

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        
    }
}