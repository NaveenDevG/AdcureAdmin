package com.adcure.adminactivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adcure.adminactivity.Interface.ItemClickListener;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  public TextView productname,proctPrice,productQnty,addedtime,addeddate,prNo;
  public ImageView productImage;
  Button update,delete;
  public ItemClickListener listener;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
  productImage=(ImageView)itemView.findViewById(R.id.product_image);
  proctPrice=(TextView)itemView.findViewById(R.id.product_price) ;

        prNo=(TextView)itemView.findViewById(R.id.product_No) ;

        productname=(TextView)itemView.findViewById(R.id.product_name);
 productQnty=(TextView)itemView.findViewById(R.id.quantity);
  addeddate=(TextView)itemView.findViewById(R.id.addedDate);
addedtime=(TextView)itemView.findViewById(R.id.medNo);
update=(Button)itemView.findViewById(R.id.updatebtn);
delete=(Button)itemView.findViewById(R.id.deletebtn);
    }
public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
}
    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }


}
