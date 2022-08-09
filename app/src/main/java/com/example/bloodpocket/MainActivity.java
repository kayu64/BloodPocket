package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    TextView verifyMsg, nameTv, bloodTypeTv;
    Button verifyEmailBtn;
    CardView janjiTemuBtn, semakanBtn, rekodBtn, infoBtn;
    CircleImageView profilePic;
    FirebaseAuth auth;
    private static final String USER = "user";
    private final String TAG = this.getClass().getName().toUpperCase();
    private String email, role;

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Blood Pocket");


        auth = FirebaseAuth.getInstance();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {
            // No user is signed in
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USER);



        verifyMsg = findViewById(R.id.verifyEmailMsg);
        verifyEmailBtn = findViewById(R.id.verifyEmailBtn);
        janjiTemuBtn =  findViewById(R.id.janjiTemuBtn);
        semakanBtn =  findViewById(R.id.semakanBtn);
        rekodBtn = findViewById(R.id.rekodBtn);
        infoBtn = findViewById(R.id.infoBtn);
        profilePic = findViewById(R.id.profile_iv);
        nameTv = findViewById(R.id.name_tv);
        bloodTypeTv = findViewById(R.id.bloodtype_tv);
        janjiTemuBtn.setVisibility(View.GONE);
        semakanBtn.setVisibility(View.GONE);
        rekodBtn.setVisibility(View.GONE);

        //load profile pic
        userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String profilePicString = user.getProfileImg();
                if (profilePicString != null) {
                    Glide.with(getApplicationContext()).load(profilePicString).fitCenter().centerCrop().into(profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(!auth.getCurrentUser().isEmailVerified()){
            //verifyEmailBtn.setVisibility(View.VISIBLE);
            //verifyMsg.setVisibility(View.VISIBLE);
        }

       /* userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */

        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname, bloodType, profession, workplace, phone, profileImg;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("fullName").getValue(String.class);
                        bloodType = keyId.child("bloodType").getValue(String.class);
                        role = keyId.child("role").getValue(String.class);
                        break;
                    }
                }
                if (role.equals("normal user")){
                    janjiTemuBtn.setVisibility(View.VISIBLE);
                    rekodBtn.setVisibility(View.VISIBLE);
                    infoBtn.setVisibility(View.GONE);
                    FirebaseMessaging.getInstance().subscribeToTopic("user");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("admin");
                }
                else if (role.equals("admin")){
                    semakanBtn.setVisibility(View.VISIBLE);
                    infoBtn.setVisibility(View.VISIBLE);
                    FirebaseMessaging.getInstance().subscribeToTopic("user");
                   //FirebaseMessaging.getInstance().unsubscribeFromTopic("user");
                }
                nameTv.setText(fname);
                bloodTypeTv.setText(bloodType);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this,"Verification Email Sent", Toast.LENGTH_SHORT).show();
                        verifyEmailBtn.setVisibility(View.GONE);
                        verifyMsg.setVisibility(View.GONE);
                    }
                });
            }
        });


    }




    public void temujanji(View view) {
        startActivity(new Intent(getApplicationContext(),StartActivity.class));
    }

    public void rekod(View view) {
        startActivity(new Intent(getApplicationContext(), List.class));
        finish();
    }

    public void semak(View view) {
        startActivity(new Intent(getApplicationContext(), ReviewActivity.class));
        finish();
    }

    public void info(View view) {
        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
        finish();
    }

    public void faq(View view) {
        startActivity(new Intent(getApplicationContext(), Faq.class));
        finish();
    }

    public void profil(View view) {
        updateUI(auth.getCurrentUser());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.resetUserPassword){
            startActivity(new Intent(getApplicationContext(),ResetPassword.class));
        }
        if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileIntent.putExtra("email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(profileIntent);
    }
}