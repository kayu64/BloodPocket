package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReviewDetails extends AppCompatActivity {

    java.util.List<AppointmentModel> modelList = new ArrayList<>();
    FirebaseFirestore db;
    String assesmentId;
    ProgressDialog pd;
    Button batalBtn, lulusBtn;
    TextView idPenggunaTv, jenisPendermaanTv, TarikhTv, beratBadanTv, q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12,q13,q14,q15,q16,q17,q18,q19;


    TextView idTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Semakan Janji Temu");

        batalBtn = findViewById(R.id.abortBtn);
        lulusBtn = findViewById(R.id.proceedBtn);

        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);

        idTV = findViewById(R.id.textView5);
        idPenggunaTv= findViewById(R.id.textView11);
        TarikhTv= findViewById(R.id.textView12);
        beratBadanTv= findViewById(R.id.textView13);
        jenisPendermaanTv= findViewById(R.id.textView6);
        //q1= findViewById(R.id.textView5);
        q2= findViewById(R.id.textView14);
        q3= findViewById(R.id.textView16);
        q4= findViewById(R.id.textView18);
        q5= findViewById(R.id.textView20);
        q6= findViewById(R.id.textView22);
        q7= findViewById(R.id.textView24);
        q8= findViewById(R.id.textView26);
        q9= findViewById(R.id.textView29);
        q10= findViewById(R.id.textView30);
        q11= findViewById(R.id.textView32);
        q12= findViewById(R.id.textView34);
        q13= findViewById(R.id.textView36);
        q14= findViewById(R.id.textView38);
        q15= findViewById(R.id.textView40);
        q16= findViewById(R.id.textView42);
        q17= findViewById(R.id.textView44);
        q18= findViewById(R.id.textView46);
        q19= findViewById(R.id.textView48);


        AlertDialog.Builder altdial = new AlertDialog.Builder(ReviewDetails.this);

        final Object object = getIntent().getSerializableExtra("id");

        assesmentId = object.toString();

        idTV.setText(assesmentId);

        showData();

        batalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                altdial.setMessage("Adakah anda ingin batalkan temujanji ini?").setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.collection("Appointment").document(assesmentId).update("status","batal");
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



            }
        });

        lulusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                altdial.setMessage("Adakah anda ingin luluskan temujanji ini?").setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.collection("Appointment").document(assesmentId).update("status","selesai");
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



            }
        });




    }

    private void showData() {
        pd.setTitle("Loading Data...");
        pd.show();
        db.collection("Appointment")
                .whereEqualTo("id", assesmentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        pd.dismiss();
                        //show data
                        for(DocumentSnapshot doc: task.getResult()) {
                            AppointmentModel model = new AppointmentModel(doc.getString("id"),
                                    doc.getDate("datetime_created"),
                                    doc.getString("userid"),
                                    doc.getString("pusat"),
                                    doc.getString("status"),
                                    doc.getString("type"),
                                    doc.getString("Berapakah berat badan anda?"),
                                    doc.getString("Pernahkah anda menderma darah sebelum ini?"),
                                    doc.getString("nterval pendermaan darah utuh adalah 3 bulan, Adakah pendermaan terakhir anda sudah mencukupi 3 bulan?"),
                                    doc.getString("Pernahkah anda menghadapi sebarang masalah semasa atau selepas menderma darah?"),
                                    doc.getString("Adakah anda sihat hari ini?"),
                                    doc.getString("Dalam tempoh 2 minggu yang lepas, adakah anda mengambil sebarang ubat-ubatan?"),
                                    doc.getString("Adakah anda mengalami sakit kepala atau migrain dalam seminggu yang lepas?"),
                                    doc.getString("Dalam tempoh 2 minggu yang lepas, adakah anda mengalami gejala demam, selesema, batuk dan?"),
                                    doc.getString("Adakah anda sedang menghidap | pernah menghidap | sedang dirawat | pernah dirawat untuk sebarang masalah kesihatan?"),
                                    doc.getString("Adakah anda sendiri atau sesiapa di dalam keluarga anda pernah menghidap atau sedang dirawat untuk penyakit Hepatitis B atau Hepatitis C?"),
                                    doc.getString("Dalam tempoh 6 bulan yang lalu, pernahkah anda (1) menjalani sebarang rawatan pembedahan, termasuk pembedahan rawatan gigi, (2) menerima pemindahan transfusi darah, (3) menerima suntikan kecantikan, (4) bertindik, (5) bertatu, (6) berbekam darah atau (7) menjalani akupunktur?"),
                                    doc.getString("Pernahkah anda menerima suntikan imunisasi dalam tempoh 4 minggu yang lepas?"),
                                    doc.getString("Adakah anda menghidap demam denggi dalam masa 6 bulan yang lepas?"),
                                    doc.getString("Adakah anda mendapat sebarang rawatan pergigian?"),
                                    doc.getString("Adakah di dalam tempoh 24 jam yang lepas anda telah mengambil minuman beralkohol sehingga memabukkan?"),
                                    doc.getString("Pernahkah anda melawat atau menetap di United Kingdom (England, Ireland Utara, Wales, Scotland, Isle of Man, Channel Island) atau Republik Ireland untuk tempoh terkumpul 6 bulan atau lebih di antara 1hb Januari 1980 hingga 31hb Disember 1996? Pernahkah anda menerima transfusi atau suntikan darah atau produk darah sewaktu berada di United Kingdom di antara 1hb Januari 1980 hingga sekarang?  Pernahkah anda melawat atau menetap di negara-negara Eropah berikut untuk tempoh terkumpul 5 tahun atau lebih di antara 1hb Januari 1980 hingga sekarang? (Austria, Belanda, Belgium, Denmark, Finland, Greece, Jerman, Itali, Liechtenstein, Luxembourg, Norway, Perancis, Portugal, Sepanyol, Sweden dan Switzerland) Adakah jawapan anda ya untuk mana-mana soalan di atas?"),
                                    doc.getString("1) Jika anda lelaki, pernahkah anda melakukan hubungan seks dengan lelaki lain? 2) Pernahkah anda melakukan hubungan seks dengan pekerja seks komersil (pelacur)? 3) Pernahkah anda membayar atau menerima bayaran untuk seks? 4) Pernahkah anda mempunyai lebih daripada seorang pasangan seks? 5) Adakah anda mempunyai pasangan seks baru dalam tempoh 12 bulan yang lalu? 6) Pernahkah anda menyuntik diri anda dengan dadah yang terlarang, termasuk dadah bina badan? 7) Adakah pasangan seks anda tergolong di dalam mana-mana kategori di atas? 8) Adakah anda atau pasangan seks anda pernah diuji positif untuk HIV? 9) Adakah anda rasa anda atau pasangan seks anda mungkin dijangkiti HIV? Adakah jawapan anda ya untuk mana-mana soalan di atas?"),
                                    doc.getString("Adakah anda sedang kedatangan haid (Hari 1 - 3) sekarang? Adakah anda mengandung atau mungkin mengandung? Adakah anda mempunyai anak yang masih menyusu-badan? Pernahkah anda melahirkan anak atau keguguran dalam tempoh 6 bulan yang lepas? Adakah jawapan anda ya untuk mana-mana soalan di atas?"),
                                    doc.getString("Adakah anda mempunyai anak yang masih menyusu badan? Adakah anak anda melebihi umur 1 tahun? Adakah anak anda sudah mengambil makanan pepejal? Adakah anak anda sudah bermula meminum susu formula? Adakah jawapan anda ya untuk semua di atas?"));
                            modelList.add(model);
                        }
                        idTV.setText(modelList.get(0).getAll());
                        idPenggunaTv.setText(modelList.get(0).getUserid());
                        TarikhTv.setText(modelList.get(0).getDateTime().toString());
                        jenisPendermaanTv.setText(modelList.get(0).getType());
                        beratBadanTv.setText(modelList.get(0).getQ1());
                        q2.setText(modelList.get(0).getQ2());
                        q3.setText(modelList.get(0).getQ3());
                        q4.setText(modelList.get(0).getQ4());
                        q5.setText(modelList.get(0).getQ5());
                        q6.setText(modelList.get(0).getQ6());
                        q7.setText(modelList.get(0).getQ7());
                        q8.setText(modelList.get(0).getQ8());
                        q9.setText(modelList.get(0).getQ9());
                        q10.setText(modelList.get(0).getQ10());
                        q11.setText(modelList.get(0).getQ11());
                        q12.setText(modelList.get(0).getQ12());
                        q13.setText(modelList.get(0).getQ13());
                        q14.setText(modelList.get(0).getQ14());
                        q15.setText(modelList.get(0).getQ15());
                        q16.setText(modelList.get(0).getQ16());
                        q17.setText(modelList.get(0).getQ17());
                        q18.setText(modelList.get(0).getQ18());
                        q19.setText(modelList.get(0).getQ19());


                        //adapter
                        //adapter = new ReviewAdapter(ReviewActivity.this, modelList);
                        //set adapter to recyclerview
                        //mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(ReviewDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}