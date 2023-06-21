package com.adcure.adminactivity;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.adcure.adminactivity.Prevalent.Medicine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
private Button submit_num;
private LinearLayout layout1,layout2;
    private EditText phoneNumber,code;
    private Button sendVerCode,verifyBtn;
    private DatabaseReference rootRef;
    private  static ArrayList<Medicine> detailsArrayList=new ArrayList<Medicine>();

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mVerification;
    private FirebaseAuth mAuth;    private ProgressDialog loadingbar;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private static final int MY_PERMISSION_REQUEST_CODE = 100;

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
           };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{ super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
            rootRef= FirebaseDatabase.getInstance().getReference();
            loadingbar=new ProgressDialog(this);
//            readDataLineByLine(String.valueOf(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"PharmaBulkUpload.csv")));
//            insertDataInto();
            requestPermission();
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
//                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                            phone,60, TimeUnit.SECONDS, OtpActivity.this,callbacks
//                    );
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(phone)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(OtpActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
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
    }catch (Exception e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }   }

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

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                if(Environment.isExternalStorageManager()){
                    checkPermissions();
                }else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2296);
                }
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11}

            checkPermissions();
        }
    }
    public void checkPermissions() {
        try {
            if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, permissions[2]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, permissions[3]) != PackageManager.PERMISSION_GRANTED

            ) {
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_REQUEST_CODE);
            } else{

            }
        } catch (Exception e) {

//            Utils.logMsg("MainActivity.checkPermissions()" + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED
                ) {
//                DBManager.initializeInstance(LoginActivity.this);
//                if (Utils.isAnyPendingRecordsInPublisherLog()) {
//                    accessCodePrefered = Utils.getPreferenceData("ACCESSCODE", "", this);
//                    user.setText(accessCodePrefered);
//                }
                Toast.makeText(this, " Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, " Permission denied", Toast.LENGTH_LONG).show();
//                     ActivityCompat.shouldShowRequestPermissionRationale(this, String.valueOf(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}));


                //            Utils.showAlertMessage("Alert","Permission Denied",this,"ok");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                    checkPermissions();
                } else {
//                    Utils.showAlertMessage("Alert","Allow permission for storage access!",this,"Ok");
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                        requestPermission();
                }
            }
        }

    }
    public void readDataLineByLine(String file)
    {

        try {
loadingbar.show();
            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            Medicine medicine;
            // we are going to read data line by line
            try{
                while (( nextRecord=csvReader.readNext()) != null) {

                medicine=new Medicine();

//                for (String cell : nextRecord) {
//                    System.out.print(cell + "\t");
//                }
                    if(!nextRecord[0].equals("") || nextRecord[0].length()!=0) {
//                        medicine.setPid(nextRecord[0]);
                        medicine.setNo(nextRecord[0]);
                        medicine.setName(nextRecord[1]);
                        medicine.setCategory(nextRecord[2]);
                        medicine.setDescription(nextRecord[3]);
                        medicine.setCompany(nextRecord[4]);
                        medicine.setPdate(nextRecord[5]);
                        medicine.setPrice(nextRecord[6]);
                        medicine.setStock(nextRecord[7]);
                        medicine.setSubcategory(nextRecord[8]);
                        medicine.setEdate(nextRecord[9]);
                        medicine.setFlat_discount(nextRecord[10]);
                        medicine.setExtra_discount(nextRecord[11]);
                        medicine.setDiscount_price(nextRecord[12]);
                        medicine.setPrescription(nextRecord[13]);
                        medicine.setDate(nextRecord[14]);
                        medicine.setTime(nextRecord[15]);
                        detailsArrayList.add(medicine);
//                medicine.setFlat_discount(nextRecord[0]);

                        System.out.println();
                    }}
            }catch (Exception e){
                e.printStackTrace();
                loadingbar.dismiss();
                Toast.makeText(this, "Can not found the PharmaBulkUpload.csv file in download folder..", Toast.LENGTH_SHORT).show();
            }
            Log.v("toto",String.valueOf(detailsArrayList.size()));
            Log.v("toto",String.valueOf(detailsArrayList));

insertDataInto();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Can not found the PharmaBulkUpload.csv file in download folder..", Toast.LENGTH_SHORT).show();
     loadingbar.dismiss();   }
    }

    public void insertDataInto() {

        for (int i=1;i<detailsArrayList.size();i++){
            FirebaseAuth mAuth ;
         DatabaseReference   rootRef=FirebaseDatabase.getInstance().getReference();

//            mAuth=FirebaseAuth.getInstance();
            int finalI = i;
            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
          String  saveCurrentdate=currentDate.format(calendar.getTime());
            SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
          String  saveCurentTime=currentTime.format(calendar.getTime());
            String userRandomKey =detailsArrayList.get(i).getNo()+saveCurrentdate + saveCurentTime;
            Medicine med=detailsArrayList.get(finalI);
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(!(snapshot.child("Pharmacy").child("All Medicines").child(userRandomKey).exists())){
                        final HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Img1","");
                        hashMap.put("Img2", "");
                        hashMap.put("Pid", userRandomKey);
                        hashMap.put("Img3", "");
                        hashMap.put("No",med.getNo());
                        hashMap.put("Name", med.getName());
                        hashMap.put("Category",med.getCategory());
                        hashMap.put("Company", med.getCompany());
                        hashMap.put("Description", med.getDescription());
                        hashMap.put("Pdate", med.getPdate());
                        hashMap.put("Price",med.getPrice());
                        hashMap.put("Edate", med.getEdate());
                        hashMap.put("Stock", med.getStock());
                        hashMap.put("Prescription", med.getPrescription());
//hashMap.put("Adminauthid",mAuth.getCurrentUser().getUid());
                        hashMap.put("Subcategory",med.getSubcategory());
                        hashMap.put("Date",med.getDate());
                        hashMap.put("Time",med.getTime());

//                    DecimalFormat df = new DecimalFormat();
//                    df.setMaximumFractionDigits(2);
//                    System.out.println(df.format(dis));

                        hashMap.put("Flat_discount",med.getFlat_discount());
                        hashMap.put("Extra_discount",med.getExtra_discount());
                        hashMap.put( "Discount_price",med.getDiscount_price());
                        rootRef.child("Pharmacy").child(med.getCategory()).child(med.getSubcategory()).child(userRandomKey).updateChildren(hashMap);

                        rootRef.child("Pharmacy").child("Sub Category").child(med.getSubcategory()).child(userRandomKey).updateChildren(hashMap);
                        rootRef.child("Pharmacy").child("All Medicines").child(userRandomKey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(OtpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(OtpActivity.this,PharaCategory.class));
                                    loadingbar.dismiss();
                                    finish();
                                }
                            }
                        });
                    }else {
//
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            }) ;


        }
        loadingbar.dismiss();
    }

}