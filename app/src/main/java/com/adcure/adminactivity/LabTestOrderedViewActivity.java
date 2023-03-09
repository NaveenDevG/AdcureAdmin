package com.adcure.adminactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adcure.adminactivity.Constants.AllConstants;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LabTestOrderedViewActivity extends AppCompatActivity {
    TextView txt_labtestname, txt_sample_y_n, txt_sample_pick_date, umail, unum, dte, tme, amnt, report_generation;
    String pid, currentUserid;
    FirebaseAuth mAuth;
    String name, email, addr, paid, state, city, num, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lab_test_ordered_view);
            txt_labtestname = findViewById(R.id.txt_labtestname);
            txt_sample_y_n = findViewById(R.id.txt_sample_y_n);
            txt_sample_pick_date = findViewById(R.id.txt_sample_pick_date);
            umail = findViewById(R.id.umail);
            unum = findViewById(R.id.unum);
            dte = findViewById(R.id.dte);
            tme = findViewById(R.id.tme);
            amnt = findViewById(R.id.amnt);
            report_generation = findViewById(R.id.report_generation);
            pid = getIntent().getStringExtra("pid");
            uid = getIntent().getStringExtra("uid");
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null) {
                currentUserid = mAuth.getCurrentUser().getUid();
            }


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pathalogy").child("All Bookings")
                    .child(pid);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

//            hashMap.put("labtest_name",tn);
//            hashMap.put("labtests_included",ti);
//            hashMap.put("lab_name",ln);
//            hashMap.put("payment_id",razorpayPaymentID);
//            hashMap.put("name", nme);
//            hashMap.put("email_id", mail);
//            hashMap.put("gender",value );
//            hashMap.put("pickudate", date);
//            hashMap.put("pickuptime", time);
//            hashMap.put("pickedup","n");
//            hashMap.put("reports","n");
//            hashMap.put("delay","n");
//            hashMap.put("number", num);
//            hashMap.put("user_id", currentUserid);
//            hashMap.put("date",saveCurrentdate);
//            hashMap.put("time",saveCurentTime);
//            hashMap.put("state",state.getSelectedItem().toString());
//            hashMap.put("city",city.getSelectedItem().toString());
//            hashMap.put("address", addr);
//            hashMap.put("paid", amnt);
                        txt_labtestname.setText("Labtest Name : " + snapshot.child("labtest_name").getValue().toString());
                        String sYN = snapshot.child("pickedup").getValue().toString();


                        if (sYN.equals("n")) {
                            txt_sample_y_n.setText("Sample : Not picked");
                            txt_sample_pick_date.setVisibility(View.GONE);
                        } else {
                            txt_sample_y_n.setText("Sample picked UP : YES");
                            txt_sample_pick_date.setVisibility(View.VISIBLE);

                            txt_sample_pick_date.setText("Sample Pickedup Date : " + snapshot.child("pickudate").getValue().toString());

                        }


                        unum.setText("Given Number : " + snapshot.child("number").getValue().toString());
                        umail.setText("Given mailId : " + snapshot.child("email_id").getValue().toString());
                        dte.setText("Payment Date : " + snapshot.child("date").getValue().toString());
                        tme.setText("Payment Time : " + snapshot.child("time").getValue().toString());
                        if (snapshot.child("paid").getValue().toString().contains("POC")) {
                            amnt.setText(snapshot.child("paid").getValue().toString());

                        } else {
                            amnt.setText("Paid Amount : " + snapshot.child("paid").getValue().toString());
                        }
                        if (snapshot.child("reports").getValue().toString().equals("n")) {
                            report_generation.setText("  Reports Generation : User Not recieved Reports.");
                        } else {
                            report_generation.setText("Reports Generation : User Recieved");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//        CustomDialog cdd=new CustomDialog(LabTestOrderedViewActivity.this);
//        cdd.show();
//        cdd.no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(LabTestOrderedViewActivity.this, "Sorry,for the trouble.AdCure Person will be contact you soon..", Toast.LENGTH_SHORT).show();
//            cdd.cancel();}
//        });
//         cdd.yes.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Toast.makeText(LabTestOrderedViewActivity.this, "We appreciate of your response.Thank you", Toast.LENGTH_SHORT).show();
//            cdd.cancel();
//             }
//         });
        } catch (Exception e) {
            e.getMessage();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void toGetInvoice(View view) {
        Intent intent = new Intent();
//        intent.setClass(this,InvoiceLabtest.class);
//        intent.putExtra("pid",pid);
//
//        startActivity(intent);
    }

    public void toBack90(View view) {
        onBackPressed();
    }

    public void updateSampleStatus(View view) {

        String str=amnt.getText().toString();
        String amount= str.replaceAll("[^0-9]", "");
        if(str.contains("POC")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this
            );

            // Setting Dialog Title
            alertDialog.setTitle("Confirmation");

            // Setting Dialog Message
            alertDialog.setMessage("Did you collected the Amount ?");

            // On pressing EkycSettingsActivity button
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pathalogy").child("All Bookings")
                            .child(pid);

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Users").
                            child(uid).child("Pathalogy").child("My bookings").child(pid);
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                reference.child("paid").setValue(amount);
                                reference1.child("paid").setValue(amount);

//                reference.child("reports").setValue("n");
//                                getToken("LabTest", "Your Sample PickedUp.. you'll be notified soon for reports..");
                                showAlert("Sample Status", "Did you pickedUp the Sample?", "s");

//                                onBackPressed();
//                                Toast.makeText(LabTestOrderedViewActivity.this, "Sample Status Updated..", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }  });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
                }
        else{
            showAlert("Sample Status", "Did you pickedUp the Sample?", "s");

        }


//        int =Integer.parseInt(numberOnly);


    }

    public void updateReportStatus(View view) {
        showAlert("Report Status", "Did you sent the Reports to User Email?", "r");
    }

    public void showAlert(String title, String message, String n) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LabTestOrderedViewActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // On pressing EkycSettingsActivity button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (n.equals("s")) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pathalogy").child("All Bookings")
                            .child(pid);

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Users").
                            child(uid).child("Pathalogy").child("My bookings").child(pid);
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("pickedup").getValue().equals("n")) {
                                reference.child("pickedup").setValue("y");
                                reference1.child("pickedup").setValue("y");

//                reference.child("reports").setValue("n");
                                getToken("LabTest", "Your Sample PickedUp.. you'll be notified soon for reports..");

                                onBackPressed();
                                Toast.makeText(LabTestOrderedViewActivity.this, "Sample Status Updated..", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LabTestOrderedViewActivity.this, "Sample already pickup..", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (n.equals("r")) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pathalogy").child("All Bookings")
                            .child(pid);
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Users").
                            child(uid).child("Pathalogy").child("My bookings").child(pid);
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("pickedup").getValue().equals("y")) {
                                if (snapshot.child("reports").getValue().equals("n")) {

                                    reference.child("reports").setValue("y");
                                    reference1.child("reports").setValue("y");

//                reference.child("reports").setValue("n");
                                    getToken("LabTest", "Please check your mail.. Reports are generated.");
                                    onBackPressed();
                                    Toast.makeText(LabTestOrderedViewActivity.this, "Report Status Updated..", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LabTestOrderedViewActivity.this, "Report Status Already Updated..", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(LabTestOrderedViewActivity.this, "Sample not Collected yet..", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void getToken(String title, String message) {
        try {


            DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Details");
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String token = snapshot.child("token").getValue().toString();


                    JSONObject to = new JSONObject();
                    JSONObject data = new JSONObject();
                    try {
                        data.put("title", title);
                        data.put("message", message);
                        data.put("hisID", uid);
                        //data.put("hisImage", myImage);
                        to.put("to", token);
                        to.put("data", data);

                        sendNotification(to);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(JSONObject to) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AllConstants.NOTIFICATION_URL, to, response -> {
            Log.d("notification", "sendNotification: " + response);
        }, error -> {
            Log.d("notification", "sendNotification: " + error);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "key=" + AllConstants.SERVER_KEY);
                map.put("Content-Type", "application/json");
                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}