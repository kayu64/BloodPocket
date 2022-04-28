package com.example.bloodpocket;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    List listActivity;
    java.util.List<Model> modelList;
    Context context;

    public CustomAdapter(List listActivity, java.util.List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String userid = modelList.get(position).getUserid();
                String pusat = modelList.get(position).getPusat();
                String status = modelList.get(position).getStatus();
                Toast.makeText(listActivity,userid+"\n"+pusat+ "\n"+status, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder , int position) {

        holder.mTitleTv.setText(modelList.get(position).getPusat());
        holder.mDescriptionTv.setText(modelList.get(position).getStatus());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(modelList.get(position).getDateTime());
        holder.mDescription2Tv.setText(strDate);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
