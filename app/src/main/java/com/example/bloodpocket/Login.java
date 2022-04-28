package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, mForgetPasswordBtn;
    FirebaseAuth fAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    private static final String TAG = "Login";
    //FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail               = findViewById(R.id.email);
        mPassword            = findViewById(R.id.password);
        mLoginBtn            = findViewById(R.id.loginBtn);
        mCreateBtn           = findViewById(R.id.createText);
        mForgetPasswordBtn   = findViewById(R.id.forgetPasswordTxt);
        fAuth                = FirebaseAuth.getInstance();
        //user                 = FirebaseAuth.getInstance().getCurrentUser();

        //checking if user is logged in
        if (fAuth.getCurrentUser() != null) {
            updateUI(fAuth.getCurrentUser());
        }


        inflater = this.getLayoutInflater();

        reset_alert = new AlertDialog.Builder(this);

        mForgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = inflater.inflate(R.layout.reset_pop, null);

                reset_alert.setTitle("Set semula kata laluan").setMessage("sila masukkan email untuk mendapatkan pautan penukaran kata laluan")
                        .setPositiveButton("Set Semula", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // va;idate email address
                                EditText email = view.findViewById(R.id.resetEmailPop);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Emel diperlukan");
                                    return;
                                }
                                // send reset link
                                fAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Login.this, "Pautan set semula e-mel telah dihantar" , Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, e.getMessage() , Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).setNegativeButton("batal", null)
                        .setView(view)
                        .create().show();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

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

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Log Masuk Berjaya", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = fAuth.getCurrentUser();
                            updateUI(user);
                            //FirebaseUser user = fAuth.getCurrentUser();
                            //updateUI(user);
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    public void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), MainActivity.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(profileIntent);
    }
}