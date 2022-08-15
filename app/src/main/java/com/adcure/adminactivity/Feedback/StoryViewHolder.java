package com.adcure.adminactivity.Feedback;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adcure.adminactivity.Interface.ItemClickListener;
import com.adcure.adminactivity.R;

public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
public TextView unme,uid,tme,dte,cmnt;
    private ItemClickListener listener;

    public StoryViewHolder(@NonNull View itemView) {
        super(itemView);
        unme=itemView.findViewById(R.id.unme);
        uid=itemView.findViewById(R.id.p_id);
        tme=itemView.findViewById(R.id.utme);
        dte=itemView.findViewById(R.id.udte);
        cmnt=itemView.findViewById(R.id.cmnt);
    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {

    }
}
