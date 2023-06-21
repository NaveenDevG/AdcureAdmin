package com.adcure.adminactivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView payid,nme,num,paid,itemcount,orderDate,shipstate;
    public Button toViewPro,toViewPastOrder;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        paid=itemView.findViewById(R.id.txtPaid);
        payid=itemView.findViewById(R.id.txtPayid);
        nme=itemView.findViewById(R.id.txtName);
        num=itemView.findViewById(R.id.txtNum);
        shipstate=itemView.findViewById(R.id.txtshipstate);
        itemcount=itemView.findViewById(R.id.txtItemscount);
        orderDate=itemView.findViewById(R.id.txtOrderdate);
        toViewPro=itemView.findViewById(R.id.btnView);
        toViewPastOrder=itemView.findViewById(R.id.btnViewPast);
    }

    @Override
    public void onClick(View v) {

    }
}
