package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class AssesmentFeedbackDialog extends Dialog {

    private int assesmentPoints = 0;
    public AssesmentFeedbackDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment_feedback_dialog);

        final AppCompatButton continueBtn = findViewById(R.id.continueBtn);
        final AppCompatButton endBtn = findViewById(R.id.endBtn);
        final TextView feedbackTV = findViewById(R.id.feedbackTV);

        setInstructionPoint(feedbackTV,"Berat badan anda tidak mencukupi untuk menderma darah. Berat badan minima untuk menderma darah adalah 45 kg.");

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent main = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(main);
            }
        });

    }
    private void setInstructionPoint(TextView feedbackTV, String instructionPoint){

        if (assesmentPoints == 0){
            feedbackTV.setText(instructionPoint);
        } else{
            feedbackTV.setText(feedbackTV.getText()+"\n\n"+ instructionPoint);
        }

    }


}