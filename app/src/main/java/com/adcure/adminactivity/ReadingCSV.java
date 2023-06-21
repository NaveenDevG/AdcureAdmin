package com.adcure.adminactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

public class ReadingCSV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_csv);
        try {
            CSVReader reader = new CSVReader(new FileReader("yourfile.csv"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }
        } catch (IOException e) {

        }
    }

}