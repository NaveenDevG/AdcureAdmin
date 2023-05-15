package com.adcure.adminactivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adcure.adminactivity.Appointment.Appointments;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileActivity extends AppCompatActivity {

    CircleImageView doctorImage;
    TextInputLayout doctorName, doctorEmail, doctorExp, doctorQua,doctorHosName, doctorHosAdd, doctorPrice, doctorCity, doctorState, doctorDate, doctorTime;
    ImageView back;
    Button update;
String phoneId,id;

    DatabaseReference databaseReference, databaseReference1,databaseReference2;

    Appointments appointment;

    public static String value;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
//         try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

phoneId=getIntent().getStringExtra("did").toString();
id=getIntent().getStringExtra("id");
        appointment = new Appointments();


        doctorImage = findViewById(R.id.ProfileImage);
        doctorName = findViewById(R.id.profileName);
        doctorEmail = findViewById(R.id.profileEmail);
        doctorExp = findViewById(R.id.profileExp);
        doctorQua = findViewById(R.id.profileQua);
        doctorHosName = findViewById(R.id.profileHosName);
        doctorHosAdd = findViewById(R.id.profileHosAdd);
        doctorPrice = findViewById(R.id.profilePrice);
        doctorCity = findViewById(R.id.profileCity);
        doctorState = findViewById(R.id.profileState);
//        doctorDate = findViewById(R.id.profileDate);
//        doctorTime = findViewById(R.id.profileTime);
        back = findViewById(R.id.back);
        update = findViewById(R.id.update);

             databaseReference = FirebaseDatabase.getInstance().getReference().child("Added Doctors");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


           databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if(id.equals(dataSnapshot.child("Id").getValue().toString())){

                        value = dataSnapshot.child("Specialist").getValue().toString();

                        Log.i("TAG", "onDataChange: " + value);
                        doctorName.getEditText().setText(dataSnapshot.child("Name").getValue().toString());
                        doctorEmail.getEditText().setText(dataSnapshot.child("Email").getValue().toString());
                        doctorExp.getEditText().setText(dataSnapshot.child("Experience").getValue().toString());
                        doctorQua.getEditText().setText(dataSnapshot.child("Qualification").getValue().toString());
                        doctorHosName.getEditText().setText(dataSnapshot.child("Hospital_Name").getValue().toString());
                        doctorHosAdd.getEditText().setText(dataSnapshot.child("Hospital_Address").getValue().toString());
                        doctorPrice.getEditText().setText(dataSnapshot.child("Price").getValue().toString());
                        doctorCity.getEditText().setText(dataSnapshot.child("City").getValue().toString());
                        doctorState.getEditText().setText(dataSnapshot.child("State").getValue().toString());
//                                    doctorDate.setText(dataSnapshot.child("Date").getValue().toString());
//                                    doctorTime.setText(dataSnapshot.child("Time").getValue().toString());
                        Picasso.get().load(dataSnapshot.child("Image").getValue().toString()).into(doctorImage);
//                        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Doctors").child(value);
//                        databaseReference1.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                                    if (phoneId.equals(dataSnapshot.child("Number").getValue().toString())){
//
//                                        doctorName.getEditText().setText(dataSnapshot.child("Name").getValue().toString());
//                                        doctorEmail.getEditText().setText(dataSnapshot.child("Email").getValue().toString());
//                                        doctorExp.getEditText().setText(dataSnapshot.child("Experience").getValue().toString());
//                                        doctorQua.getEditText().setText(dataSnapshot.child("Qualification").getValue().toString());
//                                        doctorHosName.getEditText().setText(dataSnapshot.child("Hospital_Name").getValue().toString());
//                                        doctorHosAdd.getEditText().setText(dataSnapshot.child("Hospital_Address").getValue().toString());
//                                        doctorPrice.getEditText().setText(dataSnapshot.child("Price").getValue().toString());
//                                        doctorCity.getEditText().setText(dataSnapshot.child("City").getValue().toString());
//                                        doctorState.getEditText().setText(dataSnapshot.child("State").getValue().toString());
////                                    doctorDate.setText(dataSnapshot.child("Date").getValue().toString());
////                                    doctorTime.setText(dataSnapshot.child("Time").getValue().toString());
//                                        Picasso.get().load(dataSnapshot.child("Image").getValue().toString()).into(doctorImage);
//                                        return;
//                                    }
//
//
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
                    }




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Added Doctors");
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                if(id.equals(dataSnapshot1.child("Id").getValue().toString())){

                                    String   value1 = dataSnapshot1.child("Specialist").getValue().toString();

                                    Log.i("TAG1", "onDataChange: " + value1);
                                    String value4 = dataSnapshot1.getKey().toString();
                                    DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Doctors").child(value1).child(value4);
                                    DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Available Doctors").child(value4);

                                    DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Added Doctors").child(id);

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("Name",doctorName.getEditText().getText().toString());
                                    hashMap.put("Email", doctorEmail.getEditText().getText().toString());
                                    hashMap.put("Experience", doctorExp.getEditText().getText().toString());
                                    hashMap.put("Qualification", doctorQua.getEditText().getText().toString());
                                    hashMap.put("Hospital_Name", doctorHosName.getEditText().getText().toString());
                                    hashMap.put("Hospital_Address", doctorHosAdd.getEditText().getText().toString());
                                    hashMap.put("Price", doctorPrice.getEditText().getText().toString());
                                    hashMap.put("City", doctorCity.getEditText().getText().toString());
                                    hashMap.put("State", doctorState.getEditText().getText().toString());
                                    databaseReference4.updateChildren(hashMap);
                                    databaseReference5.updateChildren(hashMap);
                                    databaseReference6.updateChildren(hashMap);
                                    Toast.makeText(DoctorProfileActivity.this, "Doctor Profile Updated.", Toast.LENGTH_SHORT).show();
                                    Log.i("hashmap", "onDataChange: "+ databaseReference4.updateChildren(hashMap));
//
//                                    DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Doctors").child(value1);
//                                    databaseReference3.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                            for (DataSnapshot dataSnapshot2 : snapshot.getChildren()){
//
//
//                                                Log.i("value4", "onDataChange: " + value4);
//
//                                                if (phoneId.equals(dataSnapshot2.child("Number").getValue().toString())){
//                                                    DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Doctors").child(value1).child(value4);
//                                                    DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Available Doctors").child(value4);
//
//                                                    DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Added Doctors").child(id);
//
//                                                    HashMap<String, Object> hashMap = new HashMap<>();
//                                                    hashMap.put("Name",doctorName.getEditText().getText().toString());
//                                                    hashMap.put("Email", doctorEmail.getEditText().getText().toString());
//                                                    hashMap.put("Experience", doctorExp.getEditText().getText().toString());
//                                                    hashMap.put("Qualification", doctorQua.getEditText().getText().toString());
//                                                    hashMap.put("Hospital_Name", doctorHosName.getEditText().getText().toString());
//                                                    hashMap.put("Hospital_Address", doctorHosAdd.getEditText().getText().toString());
//                                                    hashMap.put("Price", doctorPrice.getEditText().getText().toString());
//                                                    hashMap.put("City", doctorCity.getEditText().getText().toString());
//                                                    hashMap.put("State", doctorState.getEditText().getText().toString());
//                                                    databaseReference4.updateChildren(hashMap);
//                                                    databaseReference5.updateChildren(hashMap);
//                                                    databaseReference6.updateChildren(hashMap);
//                                                    Toast.makeText(DoctorProfileActivity.this, "Doctor Profile Updated.", Toast.LENGTH_SHORT).show();
//                                                    Log.i("hashmap", "onDataChange: "+ databaseReference4.updateChildren(hashMap));
//
//                                                }
//
//
//                                            }
//
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                        }
//                                    });


                                }



                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            }
        });


//         }catch (Exception e){
//             Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//         }
     }

    public void deleteDoctorProfile(View view) {

        new AlertDialog.Builder(DoctorProfileActivity.this).setTitle("Delete Profile")
                .setMessage("Are you sure you want to remove this doctor profile from our records?")
                .setPositiveButton("Yes",
                        (dialog, id) -> {
                            // User wants to try giving the permissions again.
                            DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Added Doctors").child(this.id);
                            DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Doctors").child(getIntent().getStringExtra("sp")).child(this.id);
                            DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Available Doctors").child(this.id);
                            DatabaseReference databaseReference7 = FirebaseDatabase.getInstance().getReference().child("All Doctors").child(this.phoneId);

                             databaseReference4.removeValue();
                            databaseReference5.removeValue();
                            databaseReference6.removeValue();
                            databaseReference7.removeValue();
                            dialog.cancel();
                            onBackPressed();

                        })
                .setNegativeButton("No",
                        (dialog, id) -> {
                            // User doesn't want to give the permissions.
                            dialog.cancel();
                        })
                .show();
    }
}
