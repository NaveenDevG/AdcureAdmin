package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class AddStatePincode extends AppCompatActivity {
    private SearchableSpinner state,city,pincode;
    private DatabaseReference stateRef,cityRef,pinRef;
    private ArrayList<String> cityAL,stateAL,pinAL;
    private AlertDialog.Builder alertDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_state_pincode);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        pincode=findViewById(R.id.pincode);
        pinAL=new ArrayList<>();
        cityAL=new ArrayList<>();
        stateAL=new ArrayList<>();

        stateAL.add("--State--");
        stateAL.add("Add State");
        pinAL.add("--Pincode--");
        pinAL.add("Add Pincode");
        alertDialog = new AlertDialog.Builder(this);
        cityAL.add("--City--");
        stateRef = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("States") ;
        cityRef = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Cities") ;
        pinRef = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Pincodes") ;

        final ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, stateAL);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, cityAL);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityAdapter);
        final ArrayAdapter<String> pinAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, pinAL);
        pinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pincode.setAdapter(pinAdapter);
        stateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnap: snapshot.getChildren()){
                    String temp=childsnap.getKey();

                    if(!stateAL.contains(temp)){
                        stateAL.add(temp);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();

//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
                final String[] sp1 = {parent.getSelectedItem().toString()};
                stateRef.child(parent.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        cityAL.clear();
                        cityAL.add("--City--");


                        cityAdapter.notifyDataSetChanged();
                        cityAL.add("Add City");

                        for (DataSnapshot childsnap: snapshot.getChildren()){
                            String temp=childsnap.getKey();

                            if(!cityAL.contains(temp)){


                                cityAL.add(temp);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if (sp1[0].equals("Add State") ) {
                    alertDialog.setTitle("Add State");
                    final EditText input = new EditText(AddStatePincode.this);
                    alertDialog.setView(input);
                    input.setHint("Enter State name");
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                    final String string=input.getText().toString();

                                    if (TextUtils.isEmpty(string)){
                                        Toast.makeText(AddStatePincode.this, "you forget to write State name..", Toast.LENGTH_SHORT).show();
                                        stateAdapter.notifyDataSetChanged();
                                    } else{
                                        final DatabaseReference rootdef;
                                        rootdef= FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("States");
                                        rootdef.child(string).setValue(0);
                                        //dialog.dismiss();

                                        Toast.makeText(AddStatePincode.this, "added..", Toast.LENGTH_SHORT).show();
                                        stateAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog

                                    stateAdapter.notifyDataSetChanged();
//                                    spinner1.setSelection(stateAdapter.getPosition("--State--"));
                                    dialog.cancel();

                                }
                            });

                    // closed

                    // Showing Alert Message
                    alertDialog.show();
                }


            }



            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        pincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();

//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
                final String[] sp1 = {parent.getSelectedItem().toString()};

                 if (sp1[0].equals("Add Pincode") &&!city.getSelectedItem().equals("Add City") && !city.getSelectedItem().equals("--City--")
                        && !state.getSelectedItem().equals("Add State") && !state.getSelectedItem().equals("--State--")) {
                    alertDialog.setTitle("Add Pincode");
                    final EditText input = new EditText(AddStatePincode.this);
                    alertDialog.setView(input);
                    input.setHint("Enter Pincode");
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                    final String string=input.getText().toString();

                                    if (TextUtils.isEmpty(string)){
                                        Toast.makeText(AddStatePincode.this, "you forget to write City Name..", Toast.LENGTH_SHORT).show();
                                        cityAdapter.notifyDataSetChanged();
                                    }else{
//                                        if (!(string.length() == 10)) {
//                                            Toast.makeText(AddStatePincode.this, "not a Valid Number", Toast.LENGTH_SHORT).show();
//                                        } else
                                        if (!(string.length() == 6)) {
                                            Toast.makeText(AddStatePincode.this, "Enter Valid Pincode", Toast.LENGTH_SHORT).show();
                                        }else {
                                            final DatabaseReference rootdef;
                                            rootdef = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Pincodes")
                                                    .child(state.getSelectedItem().toString()).child(city.getSelectedItem().toString());
                                            rootdef.child(string).setValue(0);
                                            //dialog.dismiss();
                                            pinAdapter.notifyDataSetChanged();

                                            Toast.makeText(AddStatePincode.this, "added..", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    city.setSelection(cityAdapter.getPosition("--City--"));
                                    pincode.setSelection(pinAdapter.getPosition("--Pincode--"));
                                    dialog.cancel();
                                }
                            });

                    // closed
                    // Showing Alert Message
                    alertDialog.show();
                    // alertDialog.setCancelable(false);
                }  else if((sp1[0].equals("Add Pincode") && city.getSelectedItem().equals("Add City")) || (sp1[0].equals("Add Pincode") && city.getSelectedItem().equals("--City--"))){
                    //  sub_cat.setSelection(arrayAdapter3.getPosition(sp));

                    Toast.makeText(AddStatePincode.this, "Select City...", Toast.LENGTH_SHORT).show();
                    city.setSelection(cityAdapter.getPosition("--City--"));
                    pincode.setSelection(pinAdapter.getPosition("--Pincode--"));

                    //arrayAdapter3.notifyDataSetChanged();
                }
                else if((sp1[0].equals("Add Pincode") && state.getSelectedItem().equals("Add State")) || (sp1[0].equals("Add Pincode") && state.getSelectedItem().equals("--State--"))){
                    //  sub_cat.setSelection(arrayAdapter3.getPosition(sp));

                    Toast.makeText(AddStatePincode.this, "Select State...", Toast.LENGTH_SHORT).show();
                    state.setSelection(stateAdapter.getPosition("--State--"));
                    city.setSelection(cityAdapter.getPosition("--City--"));
                    pincode.setSelection(pinAdapter.getPosition("--Pincode--"));

                    //arrayAdapter3.notifyDataSetChanged();
                }
                else{
                    cityAdapter.notifyDataSetChanged();
                }


            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();

//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
                final String[] sp1 = {parent.getSelectedItem().toString()};
                pinRef.child(state.getSelectedItem().toString()).child(parent.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        pinAL.clear();
                        pinAL.add("--Pincode--");


                        pinAdapter.notifyDataSetChanged();
                        pinAL.add("Add Pincode");

                        for (DataSnapshot childsnap: snapshot.getChildren()){
                            String temp=childsnap.getKey();

                            if(!pinAL.contains(temp)){


                                pinAL.add(temp);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (sp1[0].equals("Add City") && !state.getSelectedItem().equals("Add State") && !state.getSelectedItem().equals("--State--")) {
                    alertDialog.setTitle("Add City");
                    final EditText input = new EditText(AddStatePincode.this);
                    alertDialog.setView(input);
                    input.setHint("Enter City Name");
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                    final String string=input.getText().toString();

                                    if (TextUtils.isEmpty(string)){
                                        Toast.makeText(AddStatePincode.this, "you forget to write City Name..", Toast.LENGTH_SHORT).show();
                                        cityAdapter.notifyDataSetChanged();
                                    }else{
                                        final DatabaseReference rootdef;
                                        rootdef= FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("States").child(state.getSelectedItem().toString());
                                        rootdef.child(string).setValue(0);
                                        //dialog.dismiss();
                                        cityAdapter.notifyDataSetChanged();

                                        Toast.makeText(AddStatePincode.this, "added..", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    city.setSelection(cityAdapter.getPosition("--City--"));
                                    dialog.cancel();


                                }
                            });

                    // closed
                    // Showing Alert Message
                    alertDialog.show();
                    // alertDialog.setCancelable(false);
                }
                else if((sp1[0].equals("Add City") && state.getSelectedItem().equals("Add State")) || (sp1[0].equals("Add City") && state.getSelectedItem().equals("--State--"))){
                    //  sub_cat.setSelection(arrayAdapter3.getPosition(sp));

                    Toast.makeText(AddStatePincode.this, "Select State...", Toast.LENGTH_SHORT).show();
                    city.setSelection(cityAdapter.getPosition("--City--"));
                    //arrayAdapter3.notifyDataSetChanged();
                }
                else{
                    cityAdapter.notifyDataSetChanged();
                }


            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }
}