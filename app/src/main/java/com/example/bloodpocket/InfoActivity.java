package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class InfoActivity extends AppCompatActivity {

    Button sendBtn;
    private EditText mTitle, mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Informasi");



        //get device ID

       FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println("TOKEN " +token);
                    }
                });



        sendBtn = findViewById(R.id.sendBtn);
        mTitle = findViewById(R.id.titleEt);
        mMessage = findViewById(R.id.messageEt);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString().trim();
                String message = mMessage.getText().toString().trim();

                if (!title.equals("")&& !message.equals("")){
                    AlertDialog.Builder altdial = new AlertDialog.Builder(InfoActivity.this);
                    altdial.setMessage("Adakah anda ingin menghantar maklumat ini?").setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    FCMSend.pushNotification(
                                            InfoActivity.this,
                                            "/topics/user",
                                            //"ca9rOfK7SBC7wFZFuLet1d:APA91bHYR0Ci82hXGN2WiAkV-NzAC4f-a6xWV99yBRvQmkNM4z-PBSPOuvcOkz3pt822RA0OQbW_kWTDCgCWof4HZDNmU0ndz70VTNvBdymE8Ou9uSP1i51JeoFOFnPhGmNuZ_Jm-BD_",
                                            title,
                                            message
                                    );
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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




                } else{
                    Toast.makeText(InfoActivity.this, "Sila Masukkan maklumat dengan lengkap", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString().trim();
                String message = mMessage.getText().toString().trim();

                if (!title.equals("")&& !message.equals("")){
                    FCMSend.pushNotification(
                            InfoActivity.this,
                            "ca9rOfK7SBC7wFZFuLet1d:APA91bHYR0Ci82hXGN2WiAkV-NzAC4f-a6xWV99yBRvQmkNM4z-PBSPOuvcOkz3pt822RA0OQbW_kWTDCgCWof4HZDNmU0ndz70VTNvBdymE8Ou9uSP1i51JeoFOFnPhGmNuZ_Jm-BD_",
                            title,
                            message
                    );

                }

                /*NotificationCompat.Builder builder = new NotificationCompat.Builder(InfoActivity.this,"My Notification");

                     builder.setContentTitle("My Title");
                     builder.setContentText("Bekalan darah A di Pusat Darah negara semakin kurang. penderma dengan jenis darah A dimohon membuat pendermaan segeara.");
                     builder.setSmallIcon(R.drawable.ic_action_add);
                     builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(InfoActivity.this);
                managerCompat.notify(1,builder.build());


            }
        });*/

    }
}