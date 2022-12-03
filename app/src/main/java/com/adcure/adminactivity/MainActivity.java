package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adcure.adminactivity.Prevalent.Users;
import com.adcure.adminactivity.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private EditText loginPhone,loginPassword;
    private Button loginButton,toreg;
    private ProgressDialog dialog;
    String name,pswrd;

    private  com.rey.material.widget.CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginPassword=(EditText)findViewById(R.id.login_password);
        loginPhone=(EditText)findViewById(R.id.login_phone);
        loginButton=(Button)findViewById(R.id.main_login);
        dialog=new ProgressDialog(this);
        checkBox=( com.rey.material.widget.CheckBox)findViewById(R.id.remembe_me_check);
        Paper.init(this);

        String phoneKey= Paper.book().read(Prevalent.usersPhoneKey);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAccount();
            }
        });
        String paswrdkey=Paper.book().read(Prevalent.usersPasswordKey);
        dialog=new ProgressDialog(this);

        if(phoneKey!="" && paswrdkey!=""){
            if(!TextUtils.isEmpty(phoneKey) && !TextUtils.isEmpty(paswrdkey)){
                AllowAccess(phoneKey,paswrdkey);
                dialog.setTitle("Already Logggedin");
                dialog.setMessage("Please wait...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
          name=loginPhone.getText().toString();
          pswrd=loginPassword.getText().toString();
toreg=(Button)findViewById(R.id.regester);
toreg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }
});
    }catch (Exception e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }  }

    private void LoginAccount() {
        String name=loginPhone.getText().toString();
        String pswrd=loginPassword.getText().toString();

        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(pswrd)){
            Toast.makeText(this,"Please write your name and password..",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your  name..",Toast.LENGTH_SHORT).show();
        }else  if(TextUtils.isEmpty(pswrd)){
            Toast.makeText(this,"Please write your password..",Toast.LENGTH_SHORT).show();
        }else{
            dialog.setTitle("Logging Account");
            dialog.setMessage("Please wait,while we are checking the credintials.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();AllowAccesToAccount(name,pswrd);
        }
    }
    private void AllowAccess(final  String name, final String pswrd) {
        final DatabaseReference rootdef;
//        Paper.book().write(Prevalent.usersPhoneKey,name);
//        Paper.book().write(Prevalent.usersPasswordKey,pswrd);
        rootdef= FirebaseDatabase.getInstance().getReference();
        rootdef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Admins").child(name).child("Details").exists()) {
                    Users userdata = snapshot.child("Admins").child(name).child("Details").getValue(Users.class);
                    if (userdata.getName().equals(name)) {
                        if (userdata.getPassword().equals(pswrd)) {
                            Toast.makeText(MainActivity.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent intent=new Intent(MainActivity.this,AdminCategoryActivity.class);
                            Prevalent.currentUserData=userdata;
                            intent.putExtra("nme",name);
                            intent.putExtra("pass",pswrd);
                            startActivity(intent);
                            finish();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Account with this "+name+" name do not exist..", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void AllowAccesToAccount(final String name, final String pswrd) {
        if(checkBox.isChecked()){
            Paper.book().write(Prevalent.usersPhoneKey,name);
            Paper.book().write(Prevalent.usersPasswordKey,pswrd);
        }
        final DatabaseReference rootdef;
        rootdef= FirebaseDatabase.getInstance().getReference();
        rootdef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Admins").child(name).child("Details").exists()) {
                    Users userdata = snapshot.child("Admins").child(name).child("Details").getValue(Users.class);
                    if (userdata.getName().equals(name)) {
                        if (userdata.getPassword().equals(pswrd)) {

                                Toast.makeText(MainActivity.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent inten=new Intent(MainActivity.this,AdminCategoryActivity.class);
                            inten.putExtra("nme",name);
                            inten.putExtra("pass",pswrd);
                                startActivity(inten);
                                finish();

                        }
                    }}else{
                    Toast.makeText(MainActivity.this, "Account with this "+name+"  with this name do not exist..", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }}