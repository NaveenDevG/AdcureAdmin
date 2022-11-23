package com.adcure.adminactivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PathalogyOrderView extends RecyclerView.ViewHolder implements View.OnClickListener {
public TextView testName,testsIncluded,date,time,amountpaid;
    public Button btn;
    public PathalogyOrderView(@NonNull View itemView) {
        super(itemView);
        testName=itemView.findViewById(R.id.tstname);
        testsIncluded=itemView.findViewById(R.id.tests);
        date=itemView.findViewById(R.id.dte_app);
        time=itemView.findViewById(R.id.time_app);
        amountpaid=itemView.findViewById(R.id.amnt_patha);
btn=itemView.findViewById(R.id.btn_lab_deatail);
    }

    @Override
    public void onClick(View view) {

    }
}
