package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartSection extends AppCompatActivity {
TextView name,num,email,addr,uid;String uuid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_section);
        Toast.makeText(this, getIntent().getStringExtra("euid"), Toast.LENGTH_SHORT).show();
        name=findViewById(R.id.txt_name);
        num=findViewById(R.id.contact);
        email=findViewById(R.id.emailId);
        addr=findViewById(R.id.address);
        uid=findViewById(R.id.userId);
uuid=getIntent().getStringExtra("euid");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(uuid).child("Details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("email").exists()){
email.setText(snapshot.child("email").getValue().toString());
                    Toast.makeText(CartSection.this, snapshot.child("email").getValue().toString(), Toast.LENGTH_SHORT).show();
                    databaseReference.child("Users").child(uuid).child("Udetails").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String numS=snapshot.child("phone").getValue().toString();
                               num.setText("Contact : "+numS);
                                name.setText(snapshot.child("name").getValue().toString());
                             if(!snapshot.child("address").getValue().toString().equals(""))
                             {addr.setText(snapshot.child("address").getValue().toString());}
           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if(snapshot.child("phone").exists()){
                    num.setText(snapshot.child("phone").getValue().toString());
                    databaseReference.child("Users").child(uuid).child("Udetails").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
//                                num.setText(snapshot.child("phone").getValue().toString());
                                name.setText(snapshot.child("name").getValue().toString());
                                if(!snapshot.child("address").getValue().toString().equals(""))
                                {addr.setText(snapshot.child("address").getValue().toString());}

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void toBack90(View view) {
        startActivity(new Intent(CartSection.this,AdminCategoryActivity.class));
    }
}