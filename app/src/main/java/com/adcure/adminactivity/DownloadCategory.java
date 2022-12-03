package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.adcure.adminactivity.Appointment.Appointments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DownloadCategory extends AppCompatActivity {
    private DatabaseReference productRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_category);
        productRef= FirebaseDatabase.getInstance().getReference();//app/use

    }

    public void Consulted(View view) {
        try{
        productRef.child("Appoinments").child("Consulted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS);
//                                                                                             File path =new File(Environment.getExternalStoragePublicDirectory(
//                                                                                                     Environment.DIRECTORY_DOWNLOADS),"test.csv");
                    try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(path,"Consulted.csv")))) {

                        StringBuilder sb = new StringBuilder();
                        sb.append("Doctor Name");
                        sb.append(',');
                        sb.append("Doctor Number");
                        sb.append(',');
                        sb.append("Specialist Type");
                        sb.append(',');
                        sb.append("Appointment Date");
                        sb.append(',');
                        sb.append("Appointment Time");
                        sb.append(',');

                        sb.append("Patient Name");
                        sb.append(',');
                        sb.append("Patient Gender");
                        sb.append(',');
                        sb.append("Patient Number");
                        sb.append(',');
                        sb.append("Amount Paid");
                        sb.append(',');

                        sb.append('\n');
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Appointments appointments=dataSnapshot.getValue(Appointments.class);
                            String dnme= appointments.getDoctor_Name().toString();
                            String amon= appointments.getAmount().toString();
                            String dnum=appointments.getDoctor_Number();
                            String unme=appointments.getName();
                            String gender=appointments.getGender();
                            String num=appointments.getNumber();
                            String atime=appointments.getTime();
                            String date=appointments.getDate();
                            String spec=appointments.getSpecialist_Type();

                            Appointments appointments1=new Appointments();
                            appointments1.setAmount(amon);
                            appointments1.setDoctor_Name(dnme);
                            sb.append(dnme);
                            sb.append(',');
                            sb.append(dnum);
                            sb.append(',');
                            sb.append(spec);
                            sb.append(',');
                            sb.append(date);
                            sb.append(',');
                            sb.append(atime);
                            sb.append(',');
                            sb.append(unme);
                            sb.append(',');
                            sb.append(gender);
                            sb.append(',');
                            sb.append(num);
                            sb.append(',');
                            sb.append(amon);
                            sb.append(',');
                            sb.append('\n');

                        }





                        writer.write(sb.toString());
                        Toast.makeText(DownloadCategory.this, "File saved as:  "+ path.getAbsolutePath()+"/Consulted.csv", Toast.LENGTH_SHORT).show();

//
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent downloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                                downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(downloadIntent);
                            }},2000);

                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }else{
                    Toast.makeText(DownloadCategory.this, "Not having patients in consulted.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }catch (Exception e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }   }

    public void appointments(View view) {
        productRef.child("Appoinments").child("Not Consulted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    File path =Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS);
//                                                                                             File path =new File(Environment.getExternalStoragePublicDirectory(
//                                                                                                     Environment.DIRECTORY_DOWNLOADS),"test.csv");
                    try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(path,"Appointments.csv")))) {

                        StringBuilder sb = new StringBuilder();
                        sb.append("Doctor Name");
                        sb.append(',');
                        sb.append("Doctor Number");
                        sb.append(',');
                        sb.append("Specialist Type");
                        sb.append(',');
                        sb.append("Appointment Date");
                        sb.append(',');
                        sb.append("Appointment Time");
                        sb.append(',');

                        sb.append("Patient Name");
                        sb.append(',');
                        sb.append("Patient Gender");
                        sb.append(',');
                        sb.append("Patient Number");
                        sb.append(',');
                        sb.append("Amount Paid");
                        sb.append(',');

                        sb.append('\n');
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Appointments appointments=dataSnapshot.getValue(Appointments.class);
                            String dnme= appointments.getDoctor_Name().toString();
                            String amon= appointments.getAmount().toString();
                            String dnum=appointments.getDoctor_Number();
                            String unme=appointments.getName();
                            String gender=appointments.getGender();
                            String num=appointments.getNumber();
                            String atime=appointments.getTime();
                            String date=appointments.getDate();
                            String spec=appointments.getSpecialist_Type();

                            Appointments appointments1=new Appointments();
                            appointments1.setAmount(amon);
                            appointments1.setDoctor_Name(dnme);
                            sb.append(dnme);
                            sb.append(',');
                            sb.append(dnum);
                            sb.append(',');
                            sb.append(spec);
                            sb.append(',');
                            sb.append(date);
                            sb.append(',');
                            sb.append(atime);
                            sb.append(',');
                            sb.append(unme);
                            sb.append(',');
                            sb.append(gender);
                            sb.append(',');
                            sb.append(num);
                            sb.append(',');
                            sb.append(amon);
                            sb.append(',');
                            sb.append('\n');

                        }





                        writer.write(sb.toString());
                        Toast.makeText(DownloadCategory.this, "File saved as:  "+ path.getAbsolutePath()+"/Appointments.csv", Toast.LENGTH_SHORT).show();

//
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent downloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                                downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(downloadIntent);
                            }},2000);

                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }else {
                    Toast.makeText(DownloadCategory.this, "Not having any Appointments", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}