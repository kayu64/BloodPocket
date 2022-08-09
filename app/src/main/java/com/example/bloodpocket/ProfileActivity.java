package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private TextView umurTxtView, nameTxtView, jantinaTxtView;
    private TextView emailTxtView, phoneTxtView, bloodTxtView, noicTxtView;
    private CircleImageView profileCircleImgView;
    private Button updateBtn;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private String email;
    private static final String USER = "user";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profil Pengguna");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        //receive data from login screen
        //Intent intent = getIntent();
        //email = intent.getStringExtra("email");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {
            // No user is signed in
        }

        //private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodpocket-667b1-default-rtdb.firebaseio.com/");
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USER);
        Log.v("USERID", userRef.getKey());


        nameTxtView = findViewById(R.id.name_textview);
        emailTxtView = findViewById(R.id.email_textview);
        phoneTxtView = findViewById(R.id.notel_textview);
        umurTxtView = findViewById(R.id.umur_textview);
        noicTxtView = findViewById(R.id.noic_textview);
        jantinaTxtView = findViewById(R.id.jantina_textview);
        bloodTxtView = findViewById(R.id.bloodtype_textview);

        profileCircleImgView = findViewById(R.id.profile_image);

        updateBtn = findViewById(R.id.updateBtn);

        userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String profilePic = user.getProfileImg();
                if (profilePic != null) {
                    Glide.with(getApplicationContext()).load(profilePic).fitCenter().into(profileCircleImgView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileCircleImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname, umur, phone, noic, jantina, bloodtype;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
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


                nameTxtView.setText("Nama penuh: " + fname);
                emailTxtView.setText("Email: "+email);
                phoneTxtView.setText("No Tel: "+phone);
                umurTxtView.setText("Umur: "+umur);
                noicTxtView.setText("No IC: "+noic);
                jantinaTxtView.setText("Jantina: "+jantina);
                bloodTxtView.setText("Jenis Darah :"+bloodtype);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile(auth.getCurrentUser());
            }

            
        });
    }


    public void updateUserProfile(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), EditProfile.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(profileIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null){
            Uri profileUri = data.getData();
            profileCircleImgView.setImageURI(profileUri);

            final StorageReference reference = storage.getReference().child("profile_picture/")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Uploaded complete", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid())
                                .child("profileImg").setValue(uri.toString());
                            Toast.makeText(getApplicationContext(), "Uploaded complete", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                }
            })
            ;

        }
    }

}