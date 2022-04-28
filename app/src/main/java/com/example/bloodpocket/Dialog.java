package com.example.bloodpocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {

    TextView textView;
    AppCompatButton continueBtn;
    AppCompatButton endBtn;
    String feedback;


    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_assesment_feedback_dialog, null);

        Bundle bundle = getArguments();
        String imageLink = bundle.getString("case", "");


        textView = v.findViewById(R.id.feedbackTV);
        continueBtn =  v.findViewById(R.id.continueBtn);
        endBtn =v.findViewById(R.id.endBtn);

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



        switch (imageLink) {
            case "01":
                feedback = "Berat badan anda tidak mencukupi untuk menderma darah. Berat badan minima untuk menderma darah adalah 45 kg.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;
            case "31":
                feedback = "Pegawai perubatan di lokasi pendermaan darah akan menetapkan kesesuaian anda untuk menderma darah. Sila maklumkan kepada pegawai perubatan tentang masalah yang dihadapi semasa atau selepas menderma darah.\n" +
                        "\n" + "Namun, sekiranya anda ingin mendapatkan kepastian sebelum memulakan perjalanan, sila hubungi laman media sosial tabung darah yang terdekat untuk pertanyaan lanjut mengenai kesesuaian untuk menderma darah.";
                endBtn.setVisibility(View.INVISIBLE);
                continueBtn.setVisibility(View.VISIBLE);
                break;
            case "42":
                feedback = "Mohon Maaf. Sekiranya anda tidak sihat, anda tidak sesuai untuk menderma darah. Sila rujuk ke tabung darah yang terdekat untuk pertanyaan lanjut mengenai tempoh penangguhan pendermaan darah.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "51":
            case "53":
                feedback = "Untuk ubat tahan sakit untuk sebab selain sakit kepala dan ubat-ubat lain (termasuk produk kecantikan), pegawai perubatan di lokasi pendermaan darah akan menetapkan kesesuaian anda untuk menderma darah. Sila rujuk kepada pegawai perubatan yang bertugas mengenai ubat yang diambil.";
                endBtn.setVisibility(View.INVISIBLE);
                continueBtn.setVisibility(View.VISIBLE);
                break;

            case "52":
                feedback = "Pengambilan ubat antibiotik memerlukan penangguhan pendermaan selama 2 minggu dari tarikh terakhir pengambilan ubat tersebut. ";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "61":
                feedback = "Sila tangguhkan pendermaan selama 24 jam. Jumpa anda lagi selepas 24 jam.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "63":
                feedback = "Maaf. Individu yang mengalami migrain yang kerap tidak sesuai untuk melakukan pendermaan darah.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "71":
                feedback = "Maaf. Sekiranya anda mengalami gejala demam, selesema, batuk dan/atau batuk, sila tangguhkan pendermaan darah untuk 2 minggu dari tarikh pulih sepenuhnya bagi memastikan keselamatan darah yang didermakan kepada pesakit. Jumpa anda lagi selepas 2 minggu.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "81":
                feedback = "Pegawai perubatan di lokasi pendermaan darah akan menetapkan kesesuaian anda untuk menderma darah. Sila rujuk kepada pegawai perubatan yang bertugas.";
                endBtn.setVisibility(View.INVISIBLE);
                continueBtn.setVisibility(View.VISIBLE);
                break;

            case "91":
                feedback = "Maaf. Sila hubungi laman media sosial atau rujuk ke tabung darah yang terdekat untuk pertanyaan lanjut mengenai kesesuaian untuk menderma darah. Kriteria berkaitan dengan penyakit Hepatitis B atau Hepatitis C yang dihidapi oleh keluarga atau diri sendiri memerlukan penjelasan dan penerangan yang lebih mendalam.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "101":
                feedback = "Maaf. Sila tangguhkan pendermaan darah selama 6 bulan dari tarikh menjalani pembedahan / pemindahan transfusi darah / suntikan kecantikan / bertindik / bertatu / berbekam darah / akupunktur. Jumpa anda lagi selepas 6 bulan.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "111":
                feedback = "Pegawai perubatan di lokasi pendermaan darah akan menetapkan kesesuaian anda untuk menderma darah.";
                endBtn.setVisibility(View.INVISIBLE);
                continueBtn.setVisibility(View.VISIBLE);
                break;

            case "121":
                feedback = "Sila tangguhkan pendermaan darah untuk masa 6 bulan dari tarikh pulih sepenuhnya. Jumpa anda lagi selepas 6 bulan.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "131":
                feedback = "Sila tangguhkan pendermaan darah selama 7 hari dari tarikh prosedur dilakukan. Jumpa anda lagi selepas 7 hari.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;
            case "132":
                feedback = "Sila tangguhkan pendermaan darah selama 6 bulan dari tarikh menjalani pembedahan / pemindahan transfusi darah / suntikan kecantikan / bertindik / bertatu / berbekam darah / akupunktur. Jumpa anda lagi selepas 6 bulan.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;
            case "133":
                feedback = "Pegawai perubatan di lokasi pendermaan darah akan menetapkan kesesuaian anda untuk menderma darah.";
                endBtn.setVisibility(View.INVISIBLE);
                continueBtn.setVisibility(View.VISIBLE);
                break;

            case "141":
                feedback = "Sila tangguhkan pendermaan darah selama 24 jam.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "151":
                feedback = "Maaf. Anda tidak boleh menderma darah kerana risiko jangkitan variant Creutzfeld Jakob Disease (vCJD). Sila rujuk ke tabung darah yang terdekat untuk mendapatkan penerangan lanjut.";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "161":
                feedback = "Maaf. Anda tidak boleh menderma darah. Sila hubungi laman media sosial atau rujuk ke tabung darah yang terdekat untuk pertanyaan lanjut mengenai kesesuaian untuk menderma darah. ";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;

            case "171":
                feedback = "Maaf. Anda tidak boleh menderma darah. Sila hubungi laman media sosial atau rujuk ke tabung darah yang terdekat untuk pertanyaan lanjut mengenai kesesuaian untuk menderma darah. ";
                endBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.INVISIBLE);
                break;




        }



        textView.setText(feedback);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        return builder.create();




    }
}