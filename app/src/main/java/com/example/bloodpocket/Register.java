package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mPhone, mAge, mGender, mNokp;
    TextInputLayout textInputLayout, textInputLayout2 ;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    String gender[], bloodType[];
    //ProgressBar progressBar;
    private static final String USER = "user";
    private static final String TAG = "Register";
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mFullName       = findViewById(R.id.fullName);
        mEmail          = findViewById(R.id.email);
        mPassword       = findViewById(R.id.password);
        mPhone          = findViewById(R.id.phone);
        mRegisterBtn    = findViewById(R.id.registerBtn);
        mLoginBtn       = findViewById(R.id.createText);
        mAge            = findViewById(R.id.age);
        //mGender         = findViewById(R.id.gender);
        mNokp           = findViewById(R.id.ic);
        //progressBar     = findViewById(R.id.fullName);

        Resources res = getResources();

        textInputLayout = findViewById(R.id.gender);
        textInputLayout2 = findViewById(R.id.bloodType);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);

        fAuth           = FirebaseAuth.getInstance();
        database        = FirebaseDatabase.getInstance();
        mDatabase       = database.getReference(USER);

        gender = res.getStringArray(R.array.jantina);
        bloodType = res.getStringArray(R.array.jenisDarah);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(Register.this, R.layout.dropdown_item, gender );
        autoCompleteTextView.setAdapter(itemAdapter);
        itemAdapter = new ArrayAdapter<>(Register.this, R.layout.dropdown_item, bloodType );
        autoCompleteTextView2.setAdapter(itemAdapter);


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
                String nokp = mNokp.getText().toString().trim();
                String age = mAge.getText().toString().trim();
                //String gender = mGender.getText().toString().trim();
                String role = "normal user";
                String phone = mPhone.getText().toString().trim();
                String gender = autoCompleteTextView.getText().toString().trim();
                String bloodType = autoCompleteTextView2.getText().toString().trim();
                User user = new User(email,password,fullName,phone,nokp,age,gender,bloodType, role);



                if(TextUtils.isEmpty(gender)){
                    textInputLayout.setError("Jantina diperlukan");
                    return;
                }

                if(TextUtils.isEmpty(bloodType)){
                    textInputLayout2.setError("Sila pilih jenis darah");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Emel diperlukan");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("kata laluan diperlukan");
                    return;
                }

                if(TextUtils.isEmpty(nokp)){
                    mNokp.setError("Nombor kad pengenalan diperlukan");
                    return;
                }

                if(TextUtils.isEmpty(age)){
                    mAge.setError("umur diperlukan");
                    return;
                }

                if(TextUtils.isEmpty(gender)){
                    mGender.setError("jantina diperlukan");
                    return;
                }

                if(Integer.parseInt(age)<18){
                    mAge.setError("Hanya penderma berumur 18 tahun ke atas layak menderma darah");
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

                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(),Login.class));
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