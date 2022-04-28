package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Assesment extends AppCompatActivity {

    //creating questions list
    private final List<QuestionsList> questionsLists = new ArrayList<>();

    private RelativeLayout option1Layout, option2Layout, option3Layout, option4Layout;
    private TextView option1TV, option2TV, option3TV, option4TV;
    private ImageView option1Icon, option2Icon, option3Icon, option4Icon;
    private TextView questionTV;
    private ScrollView assesmentSv;



    private TextView totalQuestion;
    private TextView currentQuestion;

    //creating database reference from  URL
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodpocket-667b1-default-rtdb.firebaseio.com/");

    //current question position by default 0 (first question)
    private int currentQuestionPosition = 0;

    //selected option number. value must between 1-4. 0 means no option is selected
    private int selectedOption = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment);

        assesmentSv = findViewById(R.id.assesmentSv);

        option1Layout = findViewById(R.id.option1Layout);
        option2Layout = findViewById(R.id.option2Layout);
        option3Layout = findViewById(R.id.option3Layout);
        option4Layout = findViewById(R.id.option4Layout);

        option1TV = findViewById(R.id.option1TV);
        option2TV = findViewById(R.id.option2TV);
        option3TV = findViewById(R.id.option3TV);
        option4TV = findViewById(R.id.option4TV);

        option1Icon = findViewById(R.id.option1Icon);
        option2Icon = findViewById(R.id.option2Icon);
        option3Icon = findViewById(R.id.option3Icon);
        option4Icon = findViewById(R.id.option4Icon);

        questionTV = findViewById(R.id.questionTV);
        totalQuestion = findViewById(R.id.totalQuestionTV);
        currentQuestion = findViewById(R.id.currentQuestionTV);

        final AppCompatButton nextBtn = findViewById(R.id.nextQuestionBtn);

        /* show instructions dialog
        InstructionsDialog instructionsDialog = new InstructionsDialog(Assesment.this);
        instructionsDialog.setCancelable(false);
        instructionsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        instructionsDialog.show(); */





        //getting data(questions)from firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot questions : snapshot.child("questions").getChildren()){

                    String getQuestion = questions.child("question").getValue(String.class);
                    String getOption1 =  questions.child("option1").getValue(String.class);
                    String getOption2 =  questions.child("option2").getValue(String.class);
                    String getOption3 =  questions.child("option3").getValue(String.class);
                    String getOption4 =  questions.child("option4").getValue(String.class);
                    int getAnswer =  Integer.parseInt(questions.child("answer").getValue(String.class));

                    //creating question list object  and add details
                    QuestionsList questionsList = new QuestionsList(getQuestion, getOption1, getOption2,getOption3, getOption4, getAnswer);

                    //adding questionList object into the list
                    questionsLists.add(questionsList);

                    option3Layout.setVisibility(View.INVISIBLE);
                    option4Layout.setVisibility(View.INVISIBLE);


                }

                //setting total questions to TextView
                totalQuestion.setText("/"+questionsLists.size());

                //select first question by default
                selectQuestion(currentQuestionPosition);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Assesment.this, "Failed to get data from Database", Toast.LENGTH_SHORT).show();

            }
        });

        option1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign 1 as first option is selected
                selectedOption = 1;
                //select option
                selectedOption(option1Layout, option1Icon);


            }
        });

        option2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign 2 as second option is selected
                selectedOption = 2;

                //select option
                selectedOption(option2Layout, option2Icon);

            }
        });

        option3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign 3 as third option is selected
                selectedOption = 3;

                //select option
                selectedOption(option3Layout, option3Icon);

            }
        });

        option4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign 4 as forth option is selected
                selectedOption = 4;

                //select option
                selectedOption(option4Layout, option4Icon);

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assesmentSv.scrollTo(0, 0);

                String option1 = questionsLists.get(currentQuestionPosition).getOption1();
                String option2 = questionsLists.get(currentQuestionPosition).getOption2();
                String option3 = questionsLists.get(currentQuestionPosition).getOption3();
                String option4 = questionsLists.get(currentQuestionPosition).getOption4();


                if(selectedOption !=0){
                    String condition = Integer.toString(currentQuestionPosition) + Integer.toString(selectedOption) ;
                    Dialog dialogFragment = new Dialog();
                    Bundle bundle = new Bundle();
                    dialogFragment.setCancelable(false);

                    if (currentQuestionPosition == 0 ){
                        if(selectedOption == 1){
                            bundle.putString("case", condition);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show((Assesment.this).getSupportFragmentManager(),"Image Dialog");


                        }

                    }
                    if (currentQuestionPosition == 1 || currentQuestionPosition == 4){
                        if(selectedOption == 2){

                            selectedOption = 0;
                            currentQuestionPosition = 3;

                        }

                    }

                    if (currentQuestionPosition == 5 || currentQuestionPosition == 13){
                        if(selectedOption == 1 || selectedOption == 2 || selectedOption == 3){

                            bundle.putString("case", condition);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show((Assesment.this).getSupportFragmentManager(),"Image Dialog");

                        }

                    }
                    if (currentQuestionPosition == 6 ){
                        if(selectedOption == 1 || selectedOption == 3){

                            bundle.putString("case", condition);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show((Assesment.this).getSupportFragmentManager(),"Image Dialog");

                        }

                    }

                    if (currentQuestionPosition == 3 || currentQuestionPosition == 7 || currentQuestionPosition == 8 || currentQuestionPosition == 9 || currentQuestionPosition == 10
                            || currentQuestionPosition == 11  || currentQuestionPosition == 12 || currentQuestionPosition == 14 || currentQuestionPosition == 15 || currentQuestionPosition == 16
                            || currentQuestionPosition == 17){
                        if(selectedOption == 1 ){

                            bundle.putString("case", condition);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show((Assesment.this).getSupportFragmentManager(),"Image Dialog");

                        }

                    }



                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOption);

                    selectedOption = 0;
                    currentQuestionPosition++;

                    if(currentQuestionPosition<questionsLists.size()){
                        selectQuestion(currentQuestionPosition);

                    } else{
                        finishAssesment();
                    }


                }

            }
        });

    }
    private void finishAssesment(){

        //creating intent to  open  Assesment Result activity
        Intent intent = new Intent(Assesment.this, AssesmentResult.class);

        //creating bundle to pass assesmentQuesttionList
        Bundle bundle = new Bundle();
        bundle.putSerializable("questions", (Serializable) questionsLists);

        //add bundle to intent
        intent.putExtras(bundle);

        //start activity to open Assesment Result activity
        startActivity(intent);

        //destroy current activity
        finish();
    }

    private void condition1(int questionListPosition){

    }


    private void selectQuestion(int questionListPosition){

        String option1 = questionsLists.get(currentQuestionPosition).getOption1();
        String option2 = questionsLists.get(currentQuestionPosition).getOption2();
        String option3 = questionsLists.get(currentQuestionPosition).getOption3();
        String option4 = questionsLists.get(currentQuestionPosition).getOption4();

        if(option1 == null){
            option1Layout.setVisibility(View.GONE);
        } else
        {
            option1Layout.setVisibility(View.VISIBLE);
        }


        if(option2 == null){
            option2Layout.setVisibility(View.GONE);
        }else
        {
            option2Layout.setVisibility(View.VISIBLE);
        }


        if(option3== null){
            option3Layout.setVisibility(View.GONE);
        }else
        {
            option3Layout.setVisibility(View.VISIBLE);
        }

        if(option4 == null){
            option4Layout.setVisibility(View.GONE);
        }else
        {
            option4Layout.setVisibility(View.VISIBLE);
        }

        //reset options from new question
        resetOptions();

        //getting question detail and set to TextViews
        questionTV.setText(questionsLists.get(questionListPosition).getQuestion());
        option1TV.setText(option1);
        option2TV.setText(option2);
        option3TV.setText(option3);
        option4TV.setText(option4);

        //setting current question number to TextView
        currentQuestion.setText("Soalan "+(questionListPosition+1));
    }

    private void resetOptions(){
        option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option4Layout.setBackgroundResource(R.drawable.round_back_white50_10);

        option1Icon.setBackgroundResource(R.drawable.round_back_white50_100);
        option2Icon.setBackgroundResource(R.drawable.round_back_white50_100);
        option3Icon.setBackgroundResource(R.drawable.round_back_white50_100);
        option4Icon.setBackgroundResource(R.drawable.round_back_white50_100);

    }

    private void selectedOption(RelativeLayout selectedOptionLayout, ImageView selectedOptionIcon){

        resetOptions();

        selectedOptionIcon.setImageResource(R.drawable.check_icon);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);


    }

}