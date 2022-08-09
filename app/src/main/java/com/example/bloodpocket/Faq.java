package com.example.bloodpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Faq extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Versions> versionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        setTitle("Informasi Pengguna");

        recyclerView = findViewById(R.id.exp_recyclerview);

        initData();
        setRecyclerView();


    }

    private void setRecyclerView() {

        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);

    }

    private void initData() {

        versionsList = new ArrayList<>();

        versionsList.add(new Versions("Mengapa saya perlu menderma darah?","","","Setiap hari darah diperlukan untuk menyelamatkan nyawa. Darah mungkin diperlukan untuk pembedahan, mangsa kemalangan atau untuk merawat pesakit leukemia, barah, penyakit jantung, penyakit hati dan hemophilia. Sumbangan anda mungkin menyelamatkan nyawa."));
        versionsList.add(new Versions("Apakah Jenis-jenis pendermaan darah yang boleh dilakukan?","","","Ada 2 jenis pendermaan Yang boleh dilakukan oleh penderma darah. Pendermaan darah penuh dan Pendermaan aferesis. Pendermaan darah penuh ialah pendermaan kesemua komponen darah. Darah penderma yang dikeluarkan tidak dikembalikan ke dalam badan penderma. Pendermaan aferesis adalah darah penderma yang dikeluarkan diasingkan mengikut komponen darah di mana pendermaan aferesis ialah pendermaan komponen darah iaitu plasma darah atau platelet atau kedua-duanya sekali. Komponen darah yang tidak diperlukan akan dikembalikan semula ke dalam badan penderma."));
        versionsList.add(new Versions("Adakah saya layak untuk menderma darah?","","","Ya. Jika anda berumur 17 tahun ke atas (pengesahan umur diperlukan) dan mempunyai berat badan melebihi 45kg. Pada amnya, pengambilan ubat tidak menjadi halangan untuk menderma darah kerana penerimaan dan penolakan berdasarkan laporan perubatan. Sekiranya anda mengambil sebarang ubat, sila beritahu sebelum menderma darah."));
        versionsList.add(new Versions("Adakah saya cukup sihat untuk menderma darah?","","","Anda harus cukup sihat jika menderma darah. Kesihatan dan keselamatan anda amat penting bagi kami. Anda akan diminta untuk mengisi borang persetujuan menderma darah di mana sejarah perubatan anda akan disoal. Tekanan darah dan setitik darah akan diambil untuk ujian haemoglobin. Semua maklumat yang diberi oleh anda adalah sulit."));
        versionsList.add(new Versions("Berapa lamakah masa diambil untuk menderma darah?","","","Proses menderma darah ini akan mengambil masa diantara 7-10 minit, tetapi anda dinasihatkan supaya berada di tempat menderma selama lebih kurang satu jam."));
        versionsList.add(new Versions("Adakah Jarum itu menyakitkan?","","","Anda cuma berasa sakit sedikit apabila jarum dimasukkan dan tidak lagi selepas itu."));
        versionsList.add(new Versions("Berapa banyak darah yang akan diambil?","","","Sebanyak 450ml, iaitu kurang dari 1 pain (586ml). Orang dewasa biasanya mempunyai lebih kurang 5 liter (10-12 pain) darah di dalam badan."));
        versionsList.add(new Versions("Bagaimanakah rasanya selepas menderma darah?","","","Kebanyakan orang merasa selesa selepas menderma darah. Anda dinasihatkan supaya mengambil makanan ringan sebelum menderma darah."));
        versionsList.add(new Versions("Bolehkah saya melakukan aktiviti sukan selepas menderma darah?","","","Boleh. Cuma elakkan dari melakukan aktiviti yang berat untuk tempoh 5 jam selepas menderma darah. Banyakkan minum air dalam tempoh 24 jam."));
        versionsList.add(new Versions("Berapakah lama masa yang diambil untuk menggantikan darah yang telah diderma itu?","","","Plasma digantikan semula dalam masa 24 jam. Anda perlu minum 4 gelas air tanpa alkohol dan jangan melakukan kerja berat dengan menggunakan tangan anda untuk tempoh 5 jam. Darah merah memerlukan jangkamasa 4-8 minggu untuk digantikan dan anda boleh menderma semula selepas itu."));
        versionsList.add(new Versions("Apakah yang akan dibuat kepada darah yang telah didermakan itu?  ","","","Darah anda akan diuji untuk menentukan kumpulan darah, jangkitan virus seperti siflis, hepatitis (B & C) dan AIDS (HIV). Kemudian, disimpan untuk kegunaan pesakit. Darah juga boleh diasingkan kepada beberapa komponen darah dan boleh digunakan untuk merawati lebih dari seorang pesakit."));

    }
}