package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AssesmentResult extends AppCompatActivity {

    EditText mDonationTypeEt, mDonationLocationEt;
    ProgressDialog pd;
    FirebaseFirestore db;
    TextInputLayout textInputLayout, textInputLayout2 ;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;;
    String donateType[], bloodBank[], uid;
    FirebaseUser user;

    private List<QuestionsList> questionsLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Janji Temu");

       // final TextView scoreTV = findViewById(R.id.scoreTV);
        //final TextView totalScoreTV = findViewById(R.id.totalScoreTV);
        final AppCompatButton returnBtn = findViewById(R.id.returnBtn);
        final AppCompatButton appointmentBtn = findViewById(R.id.appointmentBtn);

        //mDonationTypeEt = findViewById(R.id.donationType) ;
        //mDonationLocationEt = findViewById(R.id.donationLocation);

        textInputLayout = findViewById(R.id.donationType);
        textInputLayout2 = findViewById(R.id.donationLocation);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);

        Resources res = getResources();
        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                // UID specific to the provider
                 uid = profile.getUid();
            }
        }


        //getting question list from assesment Activity
        questionsLists = (List<QuestionsList>) getIntent().getSerializableExtra("questions");
        donateType = res.getStringArray(R.array.jenisPendermaaan);
        bloodBank = res.getStringArray(R.array.pusatPendermaaan);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(AssesmentResult.this, R.layout.dropdown_item, donateType );
        autoCompleteTextView.setAdapter(itemAdapter);
        itemAdapter = new ArrayAdapter<>(AssesmentResult.this, R.layout.dropdown_item, bloodBank );
        autoCompleteTextView2.setAdapter(itemAdapter);


        //totalScoreTV.setText("/"+questionsLists.size());
        //scoreTV.setText(getCorrectAnswers()+"");
        //scoreTV.setText(getAllQuestion()+"");

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(AssesmentResult.this,MainActivity.class));
               finish();

            }
        });

        appointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String donationType = autoCompleteTextView.getText().toString().trim();
                String donationLocation = autoCompleteTextView2.getText().toString().trim();

                if(TextUtils.isEmpty(donationType)){
                    mDonationTypeEt.setError("Jenis Pendermaan diperlukan");
                    return;
                }

                if(TextUtils.isEmpty(donationLocation)){
                    mDonationLocationEt.setError("Lokasi pendermaan diperlukan");
                    return;
                }


                uploadData(donationType,donationLocation);
            }
        });

    }

    private int getCorrectAnswers(){
        int correctAnswer =0;

        for(int i =0; i < questionsLists.size(); i++){
            int getUserSelectedOption = questionsLists.get(i).getUserSelectedAnswer(); //get user selected option
            int getQuestionAnswer = questionsLists.get(i).getAnswer(); //get correct answer for the question

            //check for user selected answer matchs with correct answer
            if(getQuestionAnswer == getUserSelectedOption){
                correctAnswer++; //count user correct answer
            }
        }
        return correctAnswer;
    }

    private void uploadData(String donationType, String donationLocation) {
        pd.setTitle("Adding data to Firebase");
        pd.show();
        Date currentTime = Calendar.getInstance().getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.HOUR, 24);

        Date expiredTime = calendar.getTime();

        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("userid",uid);
        doc.put("datetime_created",currentTime);
        doc.put("datetime_expired",expiredTime);
        doc.put("pusat", donationLocation);
        doc.put("type", donationType);
        doc.put("status", "baru");

        for(int i =0; i < questionsLists.size(); i++){
            String getQuestion = questionsLists.get(i).getQuestion(), answer;
            int getUserSelectedOption = questionsLists.get(i).getUserSelectedAnswer();
            if(getUserSelectedOption==1){
                answer = questionsLists.get(i).getOption1();
            } else if (getUserSelectedOption==2){
                answer = questionsLists.get(i).getOption2();
            } else if (getUserSelectedOption==3){
                answer = questionsLists.get(i).getOption3();
            } else if (getUserSelectedOption==4) {
                answer = questionsLists.get(i).getOption4();
            }else{
                answer = "-";
            }

            doc.put(getQuestion, answer);

            //int getUserSelectedOption = questionsLists.get(i).getUserSelectedAnswer(); //get user selected option
            //int getQuestionAnswer = questionsLists.get(i).getAnswer(); //get correct answer for the question

            //check for user selected answer matchs with correct answer
            //if(getQuestionAnswer == getUserSelectedOption){
            // correctAnswer++; //count user correct answer
        }



        db.collection("Appointment").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data is added successfully

                        pd.dismiss();
                        Toast.makeText(AssesmentResult.this, "Uploaded...", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any error while uploading
                        Toast.makeText(AssesmentResult.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


 /*   private String getAllQuestion(){
        String[] question = new String[questionsLists.size()];

        for(int i =0; i < questionsLists.size(); i++){

            question[i]= questionsLists.get(i).getQuestion();

            //int getUserSelectedOption = questionsLists.get(i).getUserSelectedAnswer(); //get user selected option
            //int getQuestionAnswer = questionsLists.get(i).getAnswer(); //get correct answer for the question

            //check for user selected answer matchs with correct answer
            //if(getQuestionAnswer == getUserSelectedOption){
            //correctAnswer++; //count user correct answer
        }
        return question[1];
    } */



}