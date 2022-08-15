package com.adcure.adminactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PharaCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phara_category);
    }

    public void viewMedicine(View view) {    startActivity(new Intent(this,ViewMedicines.class));

    }

    public void addMedicine(View view) {
    startActivity(new Intent(this,PharmaSection.class));
    }
}