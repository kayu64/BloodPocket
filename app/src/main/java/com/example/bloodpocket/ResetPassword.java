package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    EditText userPassword, userConfirmPassword;
    Button savePasswordBtn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userPassword = findViewById(R.id.newUserPassword);
        userConfirmPassword= findViewById(R.id.newConfirmPass);

        savePasswordBtn = findViewById(R.id.resetPasswordBtn);
        savePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userPassword.getText().toString().isEmpty()){
                    userPassword.setError("Perlu diisi");
                    return;
                }

                if(userConfirmPassword.getText().toString().isEmpty()){
                    userConfirmPassword.setError("Perlu diisi");
                    return;
                }

                if(userPassword.getText().toString().length() < 6){
                    userPassword.setError("kata laluan metilah melebihi 5 karakter");
                    return;
                }

                if(!userPassword.getText().toString().equals(userConfirmPassword.getText().toString())){
                    userConfirmPassword.setError("Kata laluan tidak sepadan");
                    return;
                }

                user.updatePassword(userPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPassword.this, "Kata laluan Baru berjaya dikemaskini", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}