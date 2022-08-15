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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText regname,regrepas,regPass;
    private Button register,tologin;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regname=(EditText)findViewById(R.id.register_name);
        regrepas=(EditText)findViewById(R.id.register_repassword);
        regPass=(EditText)findViewById(R.id.register_password);
        register = (Button)findViewById(R.id.main_register);
        tologin=(Button)findViewById(R.id.to_Loginlnk);
        dialog=new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
    }
    private void CreateAccount() {
        String name=regname.getText().toString();
        String repas=regrepas.getText().toString();

        String pass=regPass.getText().toString();
        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please write your name and password..",Toast.LENGTH_SHORT).show();
        } else  if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please write your password..",Toast.LENGTH_SHORT).show();
        }else  if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name..",Toast.LENGTH_SHORT).show();
        }else  if(!(pass.equals(repas))){
            Toast.makeText(this,"Please check your passwords..",Toast.LENGTH_SHORT).show();
        }else{
            dialog.setTitle("Create Account");
            dialog.setMessage("Please wait,while we are checking the credintials.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            ValidatePhoneNumber(name,pass);}
    }

    private void ValidatePhoneNumber(final String name, final String pass) {
        final DatabaseReference rootdef;
        rootdef= FirebaseDatabase.getInstance().getReference();
        rootdef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Admins").child(name).exists())){
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("name",name);

                    hashMap.put("password",pass);

                    rootdef.child("Admins").child(name).child("Details").updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Intent in=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(in);
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Network error.", Toast.LENGTH_SHORT).show();

                                        Intent in=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(in);
                                    }
                                }
                            });

                }else{
                    Toast.makeText(RegisterActivity.this,"This"+name+"already exists.",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "please try again using another phone number.", Toast.LENGTH_SHORT).show();
                    Intent inn=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(inn);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}