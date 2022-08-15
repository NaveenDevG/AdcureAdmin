package com.adcure.adminactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LabTestCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_categories);

    }

    public void addLabTest(View view) {
        startActivity(new Intent(this,AddLabTest.class));

    }

    public void viewLabtests(View view) {
        startActivity(new Intent(this,AddedLabtests.class));

    }


}