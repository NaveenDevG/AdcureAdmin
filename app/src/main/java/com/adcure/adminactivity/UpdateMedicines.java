package com.adcure.adminactivity;

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

public class UpdateMedicines extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String value,sub,cat;
    private DatabaseReference databaseReference1;
    private String pid;
    CircleImageView Image;                int disp,sd;

    TextInputLayout pname,desc,ap,dis,edis,quan,brnd,num,dcharge;
    TextView dp;
    ImageView back;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medicines);
        pid=getIntent().getStringExtra("pid");
        pname=findViewById(R.id.productName);
        desc=findViewById(R.id.descr);
        ap=findViewById(R.id.actualPrice);
        dis=findViewById(R.id.fdiscnt);
        edis=findViewById(R.id.extraDis);
        quan=findViewById(R.id.quant);
        brnd=findViewById(R.id.brand);
         num=findViewById(R.id.num);
        update=findViewById(R.id.updatebtn);
        dp=findViewById(R.id.discntPrice);
        Image=findViewById(R.id.productImg);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    if (pid.equals(dataSnapshot.child("Pid").getValue().toString())){

                        pname.getEditText().setText(dataSnapshot.child("Name").getValue().toString());
                        desc.getEditText().setText(dataSnapshot.child("Description").getValue().toString());
                        ap.getEditText().setText(dataSnapshot.child("Price").getValue().toString());
                        dis.getEditText().setText(dataSnapshot.child("Flat_discount").getValue().toString());
                        quan.getEditText().setText(dataSnapshot.child("Stock").getValue().toString());
                        brnd.getEditText().setText(dataSnapshot.child("Company").getValue().toString());
                        edis.getEditText().setText(dataSnapshot.child("Extra_discount").getValue().toString());
                        num.getEditText().setText(dataSnapshot.child("No").getValue().toString());
                        dp.setText(dataSnapshot.child("Discount_price").getValue().toString());
//                                    doctorDate.setText(dataSnapshot.child("Date").getValue().toString());
//                                    doctorTime.setText(dataSnapshot.child("Time").getValue().toString())
//                                    ;
                        sub=dataSnapshot.child("Subcategory").getValue().toString();
                        cat=dataSnapshot.child("Category").getValue().toString();
                        Picasso.get().load(dataSnapshot.child("Img1").getValue().toString()).into(Image);
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


                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines");
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            String value4 = dataSnapshot1.getKey().toString();

                            Log.i("value4", "onDataChange: " + value4);

                            if (pid.equals(value4)){


                                DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines").child(value4);
                              //  DatabaseReference db5=FirebaseDatabase.getInstance().getReference().child("").child("Products in Sub-Category").child(dataSnapshot1.child("Category").getValue().toString()).child(dataSnapshot1.child("Sub_category").getValue().toString()).child(value4);
                               // DatabaseReference db6=FirebaseDatabase.getInstance().getReference().child("").child("Products in Category").child(dataSnapshot1.child("Category").getValue().toString()).child(value4);
                                DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("Sub Category").child(sub).child(value4);
                                DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(cat).child(sub).child(value4);
//
                                final HashMap<String,Object> productMap=new HashMap<>();

                                String disd=dis.getEditText().getText().toString().replace("%","");

                                String dise=edis.getEditText().getText().toString().replace("%","");

                                sd=100-(Integer.parseInt(disd)+Integer.parseInt(dise));
                                disp = (sd*Integer.parseInt(ap.getEditText().getText().toString()))/100;
                                //  dis.getEditText().setText(disp);
                                productMap.put("Name",pname.getEditText().getText().toString());
                                productMap.put("Stock", quan.getEditText().getText().toString());

                                productMap.put("Description",desc.getEditText().getText().toString());

                                productMap.put("Flat_Discount",dis.getEditText().getText().toString());
                                productMap.put("Extra_Discount",edis.getEditText().getText().toString());

                                productMap.put("Price",ap.getEditText().getText().toString());
                                productMap.put("Company",brnd.getEditText().getText().toString());
                                productMap.put("No",num.getEditText().getText().toString());
                                productMap.put( "Discount_price", String.valueOf(disp));
                                Log.i("hashmap", "onDataChange: "+ databaseReference4.updateChildren(productMap));
                                Log.i("hashmap", "onDataChange: "+ databaseReference5.updateChildren(productMap));
                                Log.i("hashmap", "onDataChange: "+ databaseReference6.updateChildren(productMap));

                                Toast.makeText(UpdateMedicines.this, "Your item Updated.", Toast.LENGTH_SHORT).show();

                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }  }
        }

