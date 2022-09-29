package com.adcure.adminactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OrderSection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_section);
    }

    public void toTodayOrders(View view) {
        startActivity(new Intent(this,TodayOrders.class));
    }

    public void toTotalOrders(View view) {
        startActivity(new Intent(this,TotalOrders.class));

    }
}