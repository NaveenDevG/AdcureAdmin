package com.adcure.adminactivity.Appointment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adcure.adminactivity.Interface.ItemClickListener;
import com.adcure.adminactivity.R;


public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public Button btn;
    public TextView usr,dte,tme,gen,con,paid;
    public ItemClickListener listener;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        usr=(TextView)itemView.findViewById(R.id.name_user);
        dte=(TextView)itemView.findViewById(R.id.date);
        tme=(TextView)itemView.findViewById(R.id.time_d);
        gen=(TextView)itemView.findViewById(R.id.gender_user);
        con=(TextView)itemView.findViewById(R.id.consulted);
        paid=(TextView)itemView.findViewById(R.id.paid);
     }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {

    }
}
