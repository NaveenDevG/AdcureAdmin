package com.adcure.adminactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Empty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        String spe= getIntent().getStringExtra("spe");
        String id=getIntent().getStringExtra("Did");
        Toast.makeText(this, ""+spe+" "+ id, Toast.LENGTH_SHORT).show();
    }
}