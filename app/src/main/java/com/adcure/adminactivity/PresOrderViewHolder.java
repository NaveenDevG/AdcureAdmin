package com.adcure.adminactivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PresOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nme,num,mail,addre,state,city,date,time;
    public Button toViewPro,toDail,toMail;
    ImageView presImg;
    public PresOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        state=itemView.findViewById(R.id.state);
        date=itemView.findViewById(R.id.date);
        nme=itemView.findViewById(R.id.Name);
        num=itemView.findViewById(R.id.Contact);
        mail=itemView.findViewById(R.id.email);
        addre=itemView.findViewById(R.id.addres);
        presImg=itemView.findViewById(R.id.product_image);
         toViewPro=itemView.findViewById(R.id.viewPres);
        toDail=itemView.findViewById(R.id.toCall);
        toMail=itemView.findViewById(R.id.toEmail);

    }

    @Override
    public void onClick(View v) {

    }
}
