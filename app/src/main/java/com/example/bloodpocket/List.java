package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    java.util.List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    FirebaseFirestore db;
    CustomAdapter adapter;
    ProgressDialog pd;

    String uid;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //init firestore
        db = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAddBtn = findViewById(R.id.addBtn);
        pd = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                // UID specific to the provider
                uid = profile.getUid();
            }
        }

        showData();

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(List.this, Record.class));
                finish();
            }
        });

    }

    private void showData() {
        pd.setTitle("Loading Data...");
        pd.show();
        db.collection("Appointment")
                .whereEqualTo("userid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        pd.dismiss();
                        //show data
                        for(DocumentSnapshot doc: task.getResult()) {
                            Model model = new Model(doc.getString("id"),
                                    doc.getDate("datetime_created"),
                                    doc.getString("userid"),
                                    doc.getString("pusat"),
                                    doc.getString("status"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(List.this, modelList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(List.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}