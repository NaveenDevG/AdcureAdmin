package com.adcure.adminactivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adcure.adminactivity.Interface.ItemClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView doctorname,doctorSpecialist,doctorExp,doctorState,paid;
    public Button appoi,feeds;
    public CircleImageView doctorImage;
    public ItemClickListener listener;
    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        doctorImage=(CircleImageView) itemView.findViewById(R.id.doctor_image);
        doctorname=(TextView)itemView.findViewById(R.id.doctor_name);
        doctorSpecialist=(TextView)itemView.findViewById(R.id.doctor_specialist);
        doctorExp=(TextView)itemView.findViewById(R.id.doctor_experience);
        doctorState=(TextView)itemView.findViewById(R.id.doctor_state);
        appoi=(Button)itemView.findViewById(R.id.hisAppo);
        feeds=(Button)itemView.findViewById(R.id.hisFeed);

    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }


}
