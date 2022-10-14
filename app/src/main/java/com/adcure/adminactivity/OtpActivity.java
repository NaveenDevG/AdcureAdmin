package com.adcure.adminactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
private Button submit_num;
private LinearLayout layout1,layout2;
    private EditText phoneNumber,code;
    private Button sendVerCode,verifyBtn;
    private DatabaseReference rootRef;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mVerification;
    private FirebaseAuth mAuth;    private ProgressDialog loadingbar;

    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        layout1=(LinearLayout)findViewById(R.id.num);
        layout2=(LinearLayout)findViewById(R.id.otpPro);
//        sendVerCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                layout1.setVisibility(View.GONE);
//                layout2.setVisibility(View.VISIBLE);
//            }
//        });
        mAuth=FirebaseAuth.getInstance();
        rootRef= FirebaseDatabase.getInstance().getReference();
        loadingbar=new ProgressDialog(this);
        phoneNumber=(EditText)findViewById(R.id.phone);
        code=(EditText)findViewById(R.id.otp);
        sendVerCode=(Button)findViewById(R.id.number_submit_btn);
        verifyBtn=(Button)findViewById(R.id.validate_otp);

        sendVerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone= "+91"+phoneNumber.getText().toString();
                if (TextUtils.isEmpty(phoneNumber.getText().toString()) || phoneNumber.getText().toString().equals("")){
                    Toast.makeText(OtpActivity.this, "Please enter your Phone Number with out country code...", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingbar.setTitle("Phone Verification");
                    loadingbar.setMessage("Please wait,While we are authenticating your phone..");

                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phone,60, TimeUnit.SECONDS, OtpActivity.this,callbacks
                    );
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneNumber.setVisibility(View.INVISIBLE);

                sendVerCode.setVisibility(View.INVISIBLE);
                String VerificationCode=code.getText().toString();
                if(TextUtils.isEmpty(VerificationCode)){
                    Toast.makeText(OtpActivity.this, "Please write verification code..", Toast.LENGTH_SHORT).show();
                }else {

                    loadingbar.setTitle("Verification Code");
                    loadingbar.setMessage("Please wait,While we are verifying your verification code..");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(mVerification,VerificationCode);
                   // FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential);
  signInWithPhoneauthCredential(phoneAuthCredential);
                }
              }
        });

        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneauthCredential(phoneAuthCredential);
            }
            public void onCodeSent(String VerificationId,PhoneAuthProvider.ForceResendingToken token){

                mVerification=VerificationId;
                mResendToken=token;
                loadingbar.dismiss();
                Toast.makeText(OtpActivity.this, "code has been sent to your given phone number..", Toast.LENGTH_SHORT).show();
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);

            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                loadingbar.dismiss();
                Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.INVISIBLE);
            }

        };
    }

    private void signInWithPhoneauthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            final String currentUserid=mAuth.getCurrentUser().getUid();
                            final String currentPhone=mAuth.getCurrentUser().getPhoneNumber();
                            final String phone="+91"+phoneNumber.getText().toString();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!(snapshot.child("Admins").child(currentUserid).child("Details").exists())) {

                                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(data -> {

                                            String token = data.getResult().getToken();
                                            HashMap<String, Object> hashMap1 = new HashMap<>();
                                            hashMap1.put("token",token);
                                             String token1=FirebaseInstanceId.getInstance().getToken();
                                           //  hashMap1.put("token1",token1);
                                            hashMap1.put("phone", phone);
                                            hashMap1.put("auth",currentUserid);
                                            rootRef.child("AdminToken").child("token").setValue(token);
                                            rootRef.child("AdminToken").child("auth").setValue(currentUserid);

                                            rootRef.child("Admins").child(currentUserid).child("Details").updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){

                                                        sendUserToMainActivity();
                                                        loadingbar.dismiss();
                                                        Toast.makeText(OtpActivity.this, "Loggedin successfully..", Toast.LENGTH_SHORT).show();

                                                    }else {
                                                        Toast.makeText(OtpActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                                        loadingbar.dismiss();
                                                    }

                                                }
                                            });
                                        });
                                        /*rootRef.child("All Doctors").child(currentPhone).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    sendUserToMainActivity();
                                                    Toast.makeText(com.adcure.adcuredoctorapp.OtpActivity.this, "Loggedin successfully..", Toast.LENGTH_SHORT).show();
                                                    loadingbar.dismiss();
                                                }else{
                                                    Toast.makeText(com.adcure.adcuredoctorapp.OtpActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                                    loadingbar.dismiss();
                                                }
                                            }
                                        });*/
                                    }else{
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(data -> {

                                            String token = data.getResult().getToken();
                                            HashMap<String, Object> hashMap1 = new HashMap<>();
                                            hashMap1.put("token",token);
                                            String token1=FirebaseInstanceId.getInstance().getToken();
                                            //  hashMap1.put("token1",token1);
                                            hashMap1.put("phone", phone);
                                            rootRef.child("AdminToken").child("token").setValue(token);
                                            rootRef.child("AdminToken").child("auth").setValue(currentUserid);
                                            rootRef.child("Admins").child(currentUserid).child("Details").updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){

                                                        sendUserToMainActivity();
                                                        loadingbar.dismiss();
                                                        Toast.makeText(OtpActivity.this, "Loggedin successfully..", Toast.LENGTH_SHORT).show();

                                                    }else {
                                                        Toast.makeText(OtpActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                                                        loadingbar.dismiss();
                                                    }

                                                }
                                            });
                                        });
                                        sendUserToMainActivity();
                                        loadingbar.dismiss();
                                        Toast.makeText(OtpActivity.this, "Loggedin successfully..", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            loadingbar.dismiss();
                           // Toast.makeText(OtpActivity.this, "Congratulations,you're logged in successfully..", Toast.LENGTH_SHORT).show();
                         //   sendUserToMainActivity();
                        }else {
                            String msg=task.getException().toString();
                            Toast.makeText(OtpActivity.this, "Error:"+msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//
    }
    private void sendUserToMainActivity() {
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(OtpActivity.this,AdminCategoryActivity.class);
        startActivity(intent);
       finish();
    }
}