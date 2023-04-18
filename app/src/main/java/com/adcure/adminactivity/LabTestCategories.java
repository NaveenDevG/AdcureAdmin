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
         Intent intent=new Intent(this,AddLabTest.class);
        intent.putExtra("UPDATE","N");
        startActivity(intent);

    }

    public void viewLabtests(View view) {
        startActivity(new Intent(this,AddedLabtests.class));

    }


    public void orderedLabTests(View view) {
        startActivity(new Intent(this,PathalogyOrders.class));
    }
}