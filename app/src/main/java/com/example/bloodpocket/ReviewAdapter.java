package com.example.bloodpocket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    //private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    java.util.List<Model> modelList;



    public ReviewAdapter(Context context, java.util.List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
        //this.recyclerViewInterface = recyclerViewInterface;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTv, mDescriptionTv, mDescription2Tv;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }*/
                    //mClickListener.onItemClick(v, getAdapterPosition());

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.onItemLongClick(v, getAdapterPosition());
                    return true;
                }
            });

            mTitleTv = itemView.findViewById(R.id.rTitleTv);
            mDescriptionTv = itemView.findViewById(R.id.rDescriptionTv);
            mDescription2Tv = itemView.findViewById(R.id.rDescription2Tv);

        }

        private com.example.bloodpocket.ViewHolder.ClickListener mClickListener;
        public interface ClickListener{
            void onItemClick(View view, int position);
            void onItemLongClick(View view, int position);

        }
        public void setOnClickListener(com.example.bloodpocket.ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }
    }


    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        /*ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {



                //String userid = modelList.get(position).getUserid();
                //String pusat = modelList.get(position).getPusat();
                //String status = modelList.get(position).getStatus();
                //Toast.makeText(reviewActivity,userid+"\n"+pusat+ "\n"+status, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }); */


        return new ReviewAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder , int position) {

        holder.mTitleTv.setText(modelList.get(position).getUserid());
        holder.mDescriptionTv.setText(modelList.get(position).getType());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(modelList.get(position).getDateTime());
        String id = modelList.get(position).getId();
        holder.mDescription2Tv.setText(strDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewDetails.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


}
