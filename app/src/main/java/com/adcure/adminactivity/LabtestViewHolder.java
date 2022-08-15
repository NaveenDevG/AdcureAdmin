package com.adcure.adminactivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LabtestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
public TextView fdis,edis,ltname,ltt,ap,dp,ti;
    LinearLayout lt;
    public LabtestViewHolder(@NonNull View itemView) {
        super(itemView);

        fdis=itemView.findViewById(R.id.fdis);
        edis=itemView.findViewById(R.id.edis);
        ltname=itemView.findViewById(R.id.lt_name);
        ltt=itemView.findViewById(R.id.tests);
        ap=itemView.findViewById(R.id.actualprice);
        dp=itemView.findViewById(R.id.dprice);
lt=itemView.findViewById(R.id.lvltview);
        ti=itemView.findViewById(R.id.testsincluded);

    }

    @Override
    public void onClick(View view) {
    }
}
