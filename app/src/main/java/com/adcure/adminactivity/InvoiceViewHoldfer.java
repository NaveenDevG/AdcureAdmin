package com.adcure.adminactivity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adcure.adminactivity.Interface.ItemClickListener;
import com.adcure.adminactivity.Interface.ItemClickListener;

public class InvoiceViewHoldfer extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView sl,productname,qty, up,tp;
ItemClickListener listener;
    public InvoiceViewHoldfer(@NonNull View itemView) {
        super(itemView);
         productname=(TextView)itemView.findViewById(R.id.itemName);
        qty=(TextView)itemView.findViewById(R.id.qty);
        up=itemView.findViewById(R.id.unitprice);
 sl=itemView.findViewById(R.id.slno);
 tp=itemView.findViewById(R.id.totalprice);
        }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }


}
