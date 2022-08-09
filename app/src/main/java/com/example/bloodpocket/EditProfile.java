package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    EditText mFullName, mPassword, mPhone, mAge, mGender, mNokp;
    TextView mEmail;
    TextInputLayout textInputLayout, textInputLayout2 ;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;;
    Button mSaveBtn;
    FirebaseAuth fAuth;
    private String email;
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
        setContentView(R.layout.activity_edit_profile);
        setTitle("Kemaskini Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USER);

        AlertDialog.Builder altdial = new AlertDialog.Builder(EditProfile.this);


        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mSaveBtn = findViewById(R.id.saveBtn);
        mAge = findViewById(R.id.age);
        //mGender         = findViewById(R.id.gender);
        mNokp = findViewById(R.id.ic);
        //progressBar     = findViewById(R.id.fullName);

        Resources res = getResources();

        textInputLayout = findViewById(R.id.gender);
        textInputLayout2 = findViewById(R.id.bloodType);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);

        gender = res.getStringArray(R.array.jantina);
        bloodType = res.getStringArray(R.array.jenisDarah);


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                //String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString().trim();
                String nokp = mNokp.getText().toString().trim();
                String age = mAge.getText().toString().trim();
                //String gender = mGender.getText().toString().trim();
                //String role = "normal user";
                String phone = mPhone.getText().toString().trim();
                String gender = autoCompleteTextView.getText().toString().trim();
                String bloodType = autoCompleteTextView2.getText().toString().trim();
                //User user = new User(email, password, fullName, phone, nokp, age, gender, bloodType, role);


                if (TextUtils.isEmpty(gender)) {
                    textInputLayout.setError("Jantina diperlukan");
                    return;
                }

                if (TextUtils.isEmpty(bloodType)) {
                    textInputLayout2.setError("Sila pilih jenis darah");
                    return;
                }


                if (TextUtils.isEmpty(nokp)) {
                    mNokp.setError("Nombor kad pengenalan diperlukan");
                    return;
                }

                if (TextUtils.isEmpty(age)) {
                    mAge.setError("umur diperlukan");
                    return;
                }

                if (TextUtils.isEmpty(gender)) {
                    mGender.setError("jantina diperlukan");
                    return;
                }

                if (Integer.parseInt(age) < 18) {
                    mAge.setError("Hanya penderma berumur 18 tahun ke atas layak menderma darah");
                    return;
                }

                altdial.setMessage("Adakah anda ingin kemaskini maklumat profil ini?").setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                userRef.child(String.valueOf(user.getUid())).child("fullName").setValue(fullName);
                                userRef.child(String.valueOf(user.getUid())).child("nokp").setValue(nokp);
                                userRef.child(String.valueOf(user.getUid())).child("age").setValue(age);
                                userRef.child(String.valueOf(user.getUid())).child("phone").setValue(phone);
                                userRef.child(String.valueOf(user.getUid())).child("gender").setValue(gender);
                                userRef.child(String.valueOf(user.getUid())).child("bloodType").setValue(bloodType);
                                Toast.makeText(EditProfile.this, "Kemaskini Profil Berjaya", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle("Blood Pocket");
                alert.show();




            }
        });

        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname, umur, phone, noic, jantina, bloodtype;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("fullName").getValue(String.class);
                        email = keyId.child("email").getValue(String.class);
                        phone = keyId.child("phone").getValue(String.class);
                        umur = keyId.child("age").getValue(String.class);
                        noic = keyId.child("nokp").getValue(String.class);
                        jantina = keyId.child("gender").getValue(String.class);
                        bloodtype = keyId.child("bloodType").getValue(String.class);
                        break;
                    }
                }


                mFullName.setText(fname);
                mEmail.setText(email);
                mPhone.setText(phone);
                mAge.setText(umur);
                mNokp.setText(noic);
                autoCompleteTextView.setText(jantina);
                autoCompleteTextView2.setText(bloodtype);

                ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(EditProfile.this, R.layout.dropdown_item, gender);
                autoCompleteTextView.setAdapter(itemAdapter);
                itemAdapter = new ArrayAdapter<>(EditProfile.this, R.layout.dropdown_item, bloodType);
                autoCompleteTextView2.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}