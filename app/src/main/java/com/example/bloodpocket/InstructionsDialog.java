package com.example.bloodpocket;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class InstructionsDialog extends Dialog {

    private int instructionPoints = 0;
    public InstructionsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_dialog_layout);

        final AppCompatButton continueBtn = findViewById(R.id.continueBtn);
        final TextView instructionsTV = findViewById(R.id.instructionsTV);

        setInstructionPoint(instructionsTV,"1. you will get maximum 24h to go to blood center after completing assesment");

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
    }
    private void setInstructionPoint(TextView instructionsTV, String instructionPoint){

        if (instructionPoints == 0){
            instructionsTV.setText(instructionPoint);
        } else{
            instructionsTV.setText(instructionsTV.getText()+"\n\n"+ instructionPoint);
        }

    }
}
