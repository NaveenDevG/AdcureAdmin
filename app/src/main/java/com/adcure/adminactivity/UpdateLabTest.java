package com.adcure.adminactivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateLabTest extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String value,sub,cat;
    private DatabaseReference databaseReference1;
    private String pid;
    CircleImageView Image;                int disp,sd;

    TextInputLayout labteName,desc,labName,dis,edis,tetsIncluded,testNAmes,packageEdt,ap;
    TextView dp;
    ImageView back;
    Button update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{  super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_lab_test);
            pid=getIntent().getStringExtra("lid");
            labteName=(TextInputLayout)findViewById(R.id.labtest_name_edt);
            desc=findViewById(R.id.descr_edt);
            packageEdt= (TextInputLayout) findViewById(R.id.package_covers_edt);
            ap=findViewById(R.id.actualPrice);
            dis=findViewById(R.id.fdiscnt);
            edis=findViewById(R.id.extraDis);
            testNAmes=findViewById(R.id.test_namess_edt);
            tetsIncluded=findViewById(R.id.tests_inclu_edt);
            labName=findViewById(R.id.labName_edt);
            update=findViewById(R.id.updatebtn);
            delete=(Button) findViewById(R.id.deletebtn);

            dp=findViewById(R.id.discntPrice);

             databaseReference = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("All Labtests");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                        if (pid.equals(dataSnapshot.child("Lid").getValue().toString())){

                            labteName.getEditText().setText(dataSnapshot.child("Labtest_name").getValue().toString());
                            tetsIncluded.getEditText().setText(dataSnapshot.child("Tests_included").getValue().toString());
                            ap.getEditText().setText(dataSnapshot.child("Actual_price").getValue().toString());
                            dis.getEditText().setText(dataSnapshot.child("Flat_discount").getValue().toString());
                            testNAmes.getEditText().setText(dataSnapshot.child("Test_names").getValue().toString());
                            packageEdt.getEditText().setText(dataSnapshot.child("Package_covers").getValue().toString());
                            edis.getEditText().setText(dataSnapshot.child("Extra_discount").getValue().toString());
                            desc.getEditText().setText(dataSnapshot.child("Description").getValue().toString());
                            dp.setText(dataSnapshot.child("Discount_price").getValue().toString());
                                    labName.getEditText().setText(dataSnapshot.child("Lab_name").getValue().toString());
//                                    doctorTime.setText(dataSnapshot.child("Time").getValue().toString())
                                                     cat=dataSnapshot.child("Category").getValue().toString();
                            sub=dataSnapshot.child("Sub_category").getValue().toString();
                             return;
                        }

                        Log.i("TAG", "onDataChange: " + value);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//        TextWatcher watcher=new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//         dis.getEditText().addTextChangedListener(new TextWatcher() {
//             @Override
//             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//             }
//
//             @Override
//             public void onTextChanged(CharSequence s, int start, int before, int count) {
//                 String disd=dis.getEditText().getText().toString().replace("%","");
//                 sd=100-Integer.parseInt(disd);
//                 disp = (sd*Integer.parseInt(ap.getEditText().getText().toString()))/100;
//                 dis.getEditText().setText(disp);
//             }
//
//             @Override
//             public void afterTextChanged(Editable s) {
//
//             }
//         });
//        ap.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String disd=dis.getEditText().getText().toString().replace("%","");
//                sd=100-Integer.parseInt(disd);
//                disp = (sd*Integer.parseInt(ap.getEditText().getText().toString()))/100;
//                dis.getEditText().setText(disp);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("All Labtests");
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                String value4 = dataSnapshot1.getKey().toString();

                                Log.i("value4", "onDataChange: " + value4);

                                if (pid.equals(value4)){


                                    DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("All Labtests").child(value4);
                                    //  DatabaseReference db5=FirebaseDatabase.getInstance().getReference().child("").child("Products in Sub-Category").child(dataSnapshot1.child("Category").getValue().toString()).child(dataSnapshot1.child("Sub_category").getValue().toString()).child(value4);
                                    // DatabaseReference db6=FirebaseDatabase.getInstance().getReference().child("").child("Products in Category").child(dataSnapshot1.child("Category").getValue().toString()).child(value4);
                                    DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Labtests in Category").child(cat).child(value4);
                                    DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Labtests in Sub-Category").child(cat).child(sub).child(value4);
//
                                    final HashMap<String,Object> productMap=new HashMap<>();

                                    String disd=dis.getEditText().getText().toString().replace("%","");

                                    String dise=edis.getEditText().getText().toString().replace("%","");

                                    sd=100-(Integer.parseInt(disd)+Integer.parseInt(dise));
                                    disp = (sd*Integer.parseInt(ap.getEditText().getText().toString()))/100;

                                    //  dis.getEditText().setText(disp);
//                                    productMap.put("Name",pname.getEditText().getText().toString());
//                                    productMap.put("Stock", quan.getEditText().getText().toString());

                                    productMap.put("Labtest_name",labteName.getEditText().getText().toString());
                                    productMap.put("Tests_included",tetsIncluded.getEditText().getText().toString());
                                    productMap.put("Test_names",testNAmes.getEditText().getText().toString());
                                    productMap.put("Package_covers",packageEdt.getEditText().getText().toString());
                                    productMap.put("Description",desc.getEditText().getText().toString());
                                    productMap.put("Lab_name",labName.getEditText().getText().toString());

                                    productMap.put("Flat_discount",dis.getEditText().getText().toString());
                                    productMap.put("Extra_discount",edis.getEditText().getText().toString());

                                    productMap.put("Actual_price",ap.getEditText().getText().toString());
//                                    productMap.put("Company",brnd.getEditText().getText().toString());
//                                    productMap.put("No",num.getEditText().getText().toString());
                                    productMap.put( "Discount_price", String.valueOf(disp));
                                    if(String.valueOf(disp).contains("-")){
                                        Toast.makeText(UpdateLabTest.this, "Discount should be less than 100%.", Toast.LENGTH_SHORT).show();

                                    }
                                   else if(!testNAmes.getEditText().getText().toString().equals("") && tetsIncluded.getEditText().getText().length()>0){
                                        String[] testsCount=testNAmes.getEditText().getText().toString().split(",");
                                        int tinclud=Integer.parseInt(tetsIncluded.getEditText().getText().toString());
                                        if (testsCount.length!=tinclud){

                                            Toast.makeText(UpdateLabTest.this, "Test names count should be equal to tests included..", Toast.LENGTH_LONG).show();
                                        }

else {
                                            Log.i("hashmap", "onDataChange: " + databaseReference4.updateChildren(productMap));
                                            Log.i("hashmap", "onDataChange: " + databaseReference5.updateChildren(productMap));
                                            Log.i("hashmap", "onDataChange: " + databaseReference6.updateChildren(productMap));

                                            Toast.makeText(UpdateLabTest.this, "Your item Updated.", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(UpdateLabTest.this, "Please Enter Test names/Tests Included", Toast.LENGTH_SHORT).show();
                                    }
//                                    else {
//                                        Log.i("hashmap", "onDataChange: " + databaseReference4.updateChildren(productMap));
//                                        Log.i("hashmap", "onDataChange: " + databaseReference5.updateChildren(productMap));
//                                        Log.i("hashmap", "onDataChange: " + databaseReference6.updateChildren(productMap));
//
//                                        Toast.makeText(UpdateLabTest.this, "Your item Updated.", Toast.LENGTH_SHORT).show();
//                                    }
                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            });

delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

             new AlertDialog.Builder(UpdateLabTest.this)
                    .setMessage("Delete LabTest")
                    .setPositiveButton("Yes",
                            (dialog, id) -> {
                                // User wants to try giving the permissions again.
                                DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("All Labtests").child(pid);
                                DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Labtests in Category").child(cat).child(pid);
                                DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Labtests in Sub-Category").child(cat).child(sub).child(pid);
//                                                                DatabaseReference db5=FirebaseDatabase.getInstance().getReference().child(Constants.ADMIN_ID).child("Products in Sub-Category").child(model.getCategory()).child(model.getSub_category()).child(model.getPid());
//                                                                DatabaseReference db6=FirebaseDatabase.getInstance().getReference().child(Constants.ADMIN_ID).child("Products in Category").child(model.getCategory()).child(model.getPid());
                                databaseReference4.removeValue();
                                databaseReference5.removeValue();
                                databaseReference6.removeValue();
                                dialog.cancel();
                                onBackPressed();

                            })
                    .setNegativeButton("No",
                            (dialog, id) -> {
                                // User doesn't want to give the permissions.
                                dialog.cancel();
                            })
                    .show();
            //Intent intent=new Intent(ViewMedicines.this,ProductDetailsActivity.class);
//                                intent.putExtra("pid",model.getPid());
//                                intent.putExtra("pimage",model.getPimage());
//
            //startActivity(intent);

    }
});
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }  }
}

