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
    private TextView occupationTxtView, nameTxtView, workTxtView;
    private TextView emailTxtView, phoneTxtView, videoTxtView, facebookTxtView, twitterTxtView;
    private ImageView emailImageView, phoneImageView, videoImageView;
    private ImageView facebookImageView, twitterImageView;
    private CircleImageView profileCircleImgView;
    private Button updateBtn;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private Map<String, String> userMap;
    private String email;
    private String userid;
    private static final String USER = "user";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        //receive data from login screen
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        //private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodpocket-667b1-default-rtdb.firebaseio.com/");
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USER);
        Log.v("USERID", userRef.getKey());

        occupationTxtView = findViewById(R.id.occupation_textview);
        nameTxtView = findViewById(R.id.name_textview);
        workTxtView = findViewById(R.id.location_textview);
        emailTxtView = findViewById(R.id.email_textview);
        phoneTxtView = findViewById(R.id.phone_textview);
        videoTxtView = findViewById(R.id.video_textview);
        //facebookTxtView = findViewById(R.id.facebook_textview);
        twitterTxtView = findViewById(R.id.twitter_textview);

        profileCircleImgView = findViewById(R.id.profile_image);
        emailImageView = findViewById(R.id.email_imageview);
        phoneImageView = findViewById(R.id.phone_imageview);
        videoImageView = findViewById(R.id.phone_imageview);
        //facebookImageView = findViewById(R.id.facebook_imageview);
        twitterImageView = findViewById(R.id.twitter_imageview);
        updateBtn = findViewById(R.id.updateBtn);

        userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Glide.with(getApplicationContext()).load(user.getProfileImg()).into(profileCircleImgView);


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
            String fname, mail, profession, workplace, phone, profileImg;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("fullName").getValue(String.class);
                        profession = keyId.child("profession").getValue(String.class);
                        workplace = keyId.child("location").getValue(String.class);
                        phone = keyId.child("phone").getValue(String.class);
                        break;
                    }
                }
                nameTxtView.setText(fname);
                emailTxtView.setText(email);
                occupationTxtView.setText(profession);
                workTxtView.setText(workplace);
                phoneTxtView.setText(phone);
                videoTxtView.setText(phone);
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
                updateUserProfile();
            }

            
        });
    }

    private void updateUserProfile() {

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