
package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adcure.adminactivity.Prevalent.DoctorDetails;
import com.adcure.adminactivity.Prevalent.TaskMode;
import com.adcure.adminactivity.Prevalent.Users;
import com.adcure.adminactivity.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.opencsv.CSVReader;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import io.paperdb.Paper;

public class AddDoctorActivity extends AppCompatActivity {
    private static final String SHARED_PREF_FILE = "";
    private static final int ACTIVITY_CHOOSE_FILE1 = 4;
    private SearchableSpinner spinner1,spinner2,amnt_spin;
    private ImageView img;
    private String  saveCurrentdate,saveCurentTime;

private EditText qua,nme_dtcr,hsptl,hsptlAdres;
private Button btn_submit;
private Uri imageUri;
    private ProgressDialog dialog;private static int GALLERYINTENT=1;
    private String doctorRandomKey, DownloadUri,availyn;

    private StorageReference imageRef;
    private EditText cost,spincountry,state_spin,city_spin,from,to,no_of_patients;
    private ArrayList<String> arrayList;
    private AlertDialog.Builder alertDialog ;
    DatabaseReference root;
    private EditText num,emailid;
    LinearLayout timingll;
    TextView yesavialbtn,nobtn;
    private DatabaseReference dctrref,dctrrefAdmin,availRef,addedDcotorRef;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        spinner1 = (SearchableSpinner) findViewById(R.id.sp1);
        hsptl=(EditText)findViewById(R.id.hsptl_nme);
        yesavialbtn=findViewById(R.id.yesavail);
        nobtn=findViewById(R.id.noavial);
        hsptlAdres=(EditText)findViewById(R.id.adress_hsptl);
      num=(EditText)findViewById(R.id.number);
      timingll=(LinearLayout)findViewById(R.id.timings);
      yesavialbtn.setOnClickListener(new View.OnClickListener() {
          @SuppressLint("ResourceAsColor")
          @Override
          public void onClick(View view) {
//              OpenFiley();
              yesavialbtn.setBackgroundColor(Color.YELLOW);
              yesavialbtn.setBackgroundColor(R.color.colorAccent);
              nobtn.setBackgroundColor(Color.parseColor("#23000000"));
              availyn="yes";
              timingll.setVisibility(View.GONE);



          }
      });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                availyn="no";
                nobtn.setBackgroundColor(Color.YELLOW);
                nobtn.setBackgroundColor(R.color.colorAccent);
                yesavialbtn.setBackgroundColor(Color.parseColor("#23000000"));


                timingll.setVisibility(View.VISIBLE);
            }
        });
      emailid=(EditText)findViewById(R.id.email);
        spinner2 = (SearchableSpinner) findViewById(R.id.sp2);
        spincountry = (EditText) findViewById(R.id.country_nme);
        city_spin = (EditText) findViewById(R.id.city_nme);
        state_spin= (EditText) findViewById(R.id.state_nme);
         cost = (EditText) findViewById(R.id.amount);
        img=(ImageView)findViewById(R.id.dctr_img);
        qua=(EditText)findViewById(R.id.qual_dctr);
        btn_submit=(Button) findViewById(R.id.submit_btn);
        dialog=new ProgressDialog(this);
        // spinner3=(Spinner)findViewById(R.id.sp3);
        alertDialog = new AlertDialog.Builder(this);
        nme_dtcr=(EditText)findViewById(R.id.name_dctr);
    from =(EditText)findViewById(R.id.from);
    to=(EditText)findViewById(R.id.to);
    no_of_patients=(EditText)findViewById(R.id.noof_patients);
     spinner1.setTitle("Select Specialist Type..");
//        String namekey= Paper.book().read(Prevalent.usersPhoneKey);
        dctrref= FirebaseDatabase.getInstance().getReference().child("Doctors");
        dctrrefAdmin = FirebaseDatabase.getInstance().getReference().child("Admins");
        availRef = FirebaseDatabase.getInstance().getReference().child("Available Doctors");
        addedDcotorRef = FirebaseDatabase.getInstance().getReference().child("Added Doctors");

        imageRef = FirebaseStorage.getInstance().getReference().child("Doctor Images");
        root = FirebaseDatabase.getInstance().getReference().child("Specialists");

        img.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         OpenGallery();
     }
 });

        arrayList = new ArrayList<>();
        arrayList.add("Specialists");

        arrayList.add("Add Specialist Type");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
       spinner1.setTitle("Select Specialist Type..");
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
                final String[] sp1 = {parent.getSelectedItem().toString()};
                if (sp1[0].equals("Add Specialist Type")) {
                    alertDialog.setTitle("Add Specialist");
                    final EditText input = new EditText(AddDoctorActivity.this);
                    alertDialog.setView(input);
                    input.setHint("Enter Specialist Type");
                      alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
//                                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                               final String string=input.getText().toString();

                                      if (TextUtils.isEmpty(string)){
                                          Toast.makeText(AddDoctorActivity.this, "you forget to write specialist Type..", Toast.LENGTH_SHORT).show();
                                      }else{
                                          final DatabaseReference rootdef;
                                          rootdef= FirebaseDatabase.getInstance().getReference().child("Specialists");
                                          rootdef.child(string).setValue(0);
                                           //dialog.dismiss();

                                          Toast.makeText(AddDoctorActivity.this, "added..", Toast.LENGTH_SHORT).show();
                                      }
                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
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

        final String[] itms2 = new String[]{ "Experience", "1", "2",
                "3", "4", "5", "6", "7", "8", "9", "10", "12", "13", "14", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
        ArrayAdapter <String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itms2);
        spinner2.setAdapter(adapter2);
        spinner2.setTitle("Select Experience..");
//
           btn_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               arrayList.set(0,"Specialist");
               VlidateProduct();

           }
       });


    }catch(Exception e){e.getMessage();} }

    @Override
    protected void onStart() {
        super.onStart();
        try{

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnap: snapshot.getChildren()){
                    String temp=childsnap.getKey();

                    if(!arrayList.contains(temp)){
                        arrayList.add(temp);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        final String[] itms5 = new String[]{ "City", "Hyderabad","Varangal","Secunderabad" };
//        final ArrayAdapter <String> adapter5= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itms5);
//
//        final String[] itms6 = new String[]{ "City", "vizag","amaravathi","vijaywada" };
//        final ArrayAdapter <String> adapter6= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itms6);
//        city_spin.setTitle("Select City..");
//        final String[] sp = {state_spin.getSelectedItem().toString()};
//        city_spin.setAdapter(adapter5);
//        if(sp.equals("Andhra Pradesh")){
//            city_spin.setAdapter(adapter5);
//        }else if(sp.equals("Telangana")){
//            city_spin.setAdapter(adapter6);
//        }

    }catch(Exception e){e.getMessage();}  }

    private void VlidateProduct() {
        String sp1=spinner1.getSelectedItem().toString();
        String sp2=spinner2.getSelectedItem().toString();
        String sp3=spincountry.getText().toString();
        String sp4=state_spin.getText().toString();
        String sp5=city_spin.getText().toString();
        String nme=nme_dtcr.getText().toString();
        String hnme=hsptl.getText().toString();
        String numbr=num.getText().toString();
        String email=emailid.getText().toString();
        String hadr=hsptlAdres.getText().toString();
        String qual=qua.getText().toString();
        String amnt=cost.getText().toString();
        Paper.init(this);

        String phoneKey= Paper.book().read(Prevalent.usersPhoneKey);
        if(imageUri==null){
            Toast.makeText(this,"image manditory",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(nme) || TextUtils.isEmpty(qual) || TextUtils.isEmpty(hnme) || TextUtils.isEmpty(hadr) ||
                TextUtils.isEmpty(numbr) || TextUtils.isEmpty(email) || TextUtils.isEmpty(sp3)|| TextUtils.isEmpty(sp4)|| TextUtils.isEmpty(sp5) ||
                availyn.equals("")||  TextUtils.isEmpty(no_of_patients.getText().toString() )){
            Toast.makeText(this,"please fill All the details...",Toast.LENGTH_SHORT).show();
        } else if(numbr.length()<11 && numbr.length()>11){
            Toast.makeText(this,"Enter Correct phone number..",Toast.LENGTH_SHORT).show();
        }else if(sp1.equals("SPECIALISTS") && sp2.equals("Experience")){
            Toast.makeText(this, "Select specialist Type & Experience..", Toast.LENGTH_SHORT).show();
        }else if(availyn.equals("no")){
            if(!(from.getText().toString().contains("am") || (from.getText().toString().contains("pm")))){
                Toast.makeText(this, "From Time should be in am or pm.. Like 9am or 9pm", Toast.LENGTH_SHORT).show();

            }
            else if(!(to.getText().toString().contains("am") || (to.getText().toString().contains("pm")))){
                Toast.makeText(this, "To Time should be in am or pm.. Like 9am or 9pm", Toast.LENGTH_SHORT).show();

            }
            else if(sp1.equals("SPECIALISTS")){
                Toast.makeText(this,"Select specialist Type..",Toast.LENGTH_SHORT).show();

            }

            else if(sp2.equals("Experience")){
                Toast.makeText(this,"Select Experience..",Toast.LENGTH_SHORT).show();
            }

//        else if(sp3.equals("Country")){
//            Toast.makeText(this,"Select Country Name..",Toast.LENGTH_SHORT).show();
//
//        }
//        else if(sp4.equals("State")){
//            Toast.makeText(this,"Select State..",Toast.LENGTH_SHORT).show();
//
//        }
//        else if(sp5.equals("City")){
//            Toast.makeText(this,"Select City..",Toast.LENGTH_SHORT).show();
//           }
            else if(TextUtils.isEmpty(amnt)){
                Toast.makeText(this,"Enter Amount..",Toast.LENGTH_SHORT).show();
            }


            else{
                StoreDoctorInfo();
            }
        }

        else if(sp1.equals("SPECIALISTS")){
            Toast.makeText(this,"Select specialist Type..",Toast.LENGTH_SHORT).show();

        }

        else if(sp2.equals("Experience")){
            Toast.makeText(this,"Select Experience..",Toast.LENGTH_SHORT).show();
        }

//        else if(sp3.equals("Country")){
//            Toast.makeText(this,"Select Country Name..",Toast.LENGTH_SHORT).show();
//
//        }
//        else if(sp4.equals("State")){
//            Toast.makeText(this,"Select State..",Toast.LENGTH_SHORT).show();
//
//        }
//        else if(sp5.equals("City")){
//            Toast.makeText(this,"Select City..",Toast.LENGTH_SHORT).show();
//           }
        else if(TextUtils.isEmpty(amnt)){
            Toast.makeText(this,"Enter Amount..",Toast.LENGTH_SHORT).show();
        }


  else{
            StoreDoctorInfo();
        }
    }

    private void StoreDoctorInfo() {
        dialog.setTitle("Adding Doctor Profile");
        dialog.setMessage("Dear Admin, Please wait while we are checking the credintials.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentdate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurentTime=currentTime.format(calendar.getTime());
       doctorRandomKey=saveCurrentdate+saveCurentTime;
        final StorageReference filePath=imageRef.child(imageUri.getLastPathSegment()+doctorRandomKey+".jpg");
        final UploadTask uploadTask=filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg=e.toString();
                Toast.makeText(AddDoctorActivity.this,"Error:",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddDoctorActivity.this,"Doctor Image Uploaded Successfully...",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw  task.getException();
                        }
                        DownloadUri=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            DownloadUri=task.getResult().toString();
                            Toast.makeText(AddDoctorActivity.this,"got the Doctor Image saved to databse...",Toast.LENGTH_SHORT).show();
//                            saveProductInfoToDatabase();

                            final HashMap<String,Object> productMap=new HashMap<>();
                            final String sp1=spinner1.getSelectedItem().toString();
                           // DownloadUri=filePath.getDownloadUrl().toString();
                            final String sp2=spinner2.getSelectedItem().toString();
                            final String sp3=spincountry.getText().toString();
                            final String sp4=state_spin.getText().toString();
                            final String sp5=city_spin.getText().toString();
                            final String nme=nme_dtcr.getText().toString();
                            final String hnme=hsptl.getText().toString();
                            final String hadr=hsptlAdres.getText().toString();
                            final String qual=qua.getText().toString();
                            final String amnt=cost.getText().toString();
                            final String numbr=num.getText().toString();
                            final String email=emailid.getText().toString();
                            Paper.init(AddDoctorActivity.this);

                          final   String userkey= Paper.book().read(Prevalent.usersPhoneKey);

                            if(availyn.equals("yes")){
                                productMap.put("Id",doctorRandomKey);
                                productMap.put("Name","Dr. "+nme);
                                productMap.put("Hospital_Name",hnme);
                                productMap.put("Hospital_Address",hadr);
                                productMap.put("Number","+91"+numbr);
                                productMap.put("Image",DownloadUri);
                                productMap.put("Email",email);
                                productMap.put("Qualification",qual);
                                productMap.put("Specialist",sp1);
                                productMap.put("Experience",sp2);
                                productMap.put("Price",amnt);
                                productMap.put("Country",sp3);
                                productMap.put("State",sp4);
                                productMap.put("City",sp5);
                                productMap.put("Date",saveCurrentdate);
                                productMap.put("Time",saveCurentTime);
                               productMap.put("A24hours","yes");
                                productMap.put("Available_From","0hrs");
                                productMap.put("Available_to","24hrs");
                                productMap.put("No_of_patients",no_of_patients.getText().toString());
                                availRef.child(doctorRandomKey).updateChildren(productMap);

                            }else{
                                productMap.put("Id",doctorRandomKey);
                                productMap.put("Name","Dr. "+nme);
                                productMap.put("Hospital_Name",hnme);
                                productMap.put("Hospital_Address",hadr);
                                productMap.put("Number","+91"+numbr);
                                productMap.put("Image",DownloadUri);
                                productMap.put("Email",email);
                                productMap.put("Qualification",qual);
                                productMap.put("Specialist",sp1);
                                productMap.put("Experience",sp2);
                                productMap.put("Price",amnt);
                                productMap.put("Country",sp3);
                                productMap.put("State",sp4);
                                productMap.put("City",sp5);
                                productMap.put("Date",saveCurrentdate);
                                productMap.put("Time",saveCurentTime);

                                productMap.put("No_of_patients",no_of_patients.getText().toString());

                                productMap.put("A24hours","no");

                                productMap.put("Available_From",from.getText().toString());
                                productMap.put("Available_to",to.getText().toString());

                            }


                            //     productMap.put("Added_by",Prevalent.currentUserData.getName());
                            addedDcotorRef.child(doctorRandomKey).updateChildren(productMap);
                            dctrref.child(sp1).child(doctorRandomKey).updateChildren(productMap).
                                  addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseAuth auth=FirebaseAuth.getInstance();

                                        dctrrefAdmin.child(auth.getCurrentUser().getUid()).child("My Doctors").
                                                child(doctorRandomKey).updateChildren(productMap).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    Intent inten = new Intent(AddDoctorActivity.this, AdminCategoryActivity.class);


                                                   startActivity(inten);
                                                       finish();
                                                    dialog.dismiss();
                                                    Toast.makeText(AddDoctorActivity.this, "Doctor is added SuccessFully..", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    dialog.dismiss();
                                                    String message = task.getException().toString();
                                                    Toast.makeText(AddDoctorActivity.this, "Error..", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                                    }
                                } });
                        }
                    }
                });
            }
        }); {

        }
    }

    private void saveProductInfoToDatabase() {

        }
//        dctrrefAdmin.child(doctorRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull TaskMode<Void> task) {
//                if(task.isSuccessful()){
//                    Intent inten=new Intent(AddDoctorActivity.this,AdminCategoryActivity.class);
//                    startActivity(inten);
//
//                    dialog.dismiss();
//                    Toast.makeText(AddDoctorActivity.this,"Doctor is added SuccessFully..",Toast.LENGTH_SHORT).show();
//
//                }else {
//                    dialog.dismiss();
//                    String message=task.getException().toString();
//                    Toast.makeText(AddDoctorActivity.this,"Error..",Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });


    private void OpenGallery() {

        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GALLERYINTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERYINTENT && resultCode==RESULT_OK &&   data!=null){
            imageUri=data.getData();
            img.setImageURI(imageUri);
        }
        if(requestCode==ACTIVITY_CHOOSE_FILE1 && resultCode==RESULT_OK &&   data!=null){

            imageUri=data.getData();



           proImportCSV(new File(imageUri.getPath()));


            readDataLineByLine(data.getData().getPath());

        }
    }



    // Java code to illustrate reading a
// CSV file line by line
    public static void readDataLineByLine(String file)
    {

        try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void OpenFiley() {
        try{

            Intent chooseFile;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
         //// intent.addCategory(Intent.CATEGORY_OPENABLE);
           intent.setType("text/*");
            startActivityForResult(intent
                    , ACTIVITY_CHOOSE_FILE1);

        }
        catch (Exception e){
            e.getMessage();
        }
    }
    public void proImportCSV(File from){
        try {
            // Delete everything above here since we're reading from the File we already have
           // ContentValues cv = new ContentValues();
            // reading CSV and writing table
            CSVReader dataRead = new CSVReader(new FileReader(from)); // <--- This line is key, and why it was reading the wrong file

           // SQLiteDatabase db = mHelper.getWritableDatabase(); // LEt's just put this here since you'll probably be using it a lot more than once
            String[] vv = null;
            while((vv = dataRead.readNext())!=null) {
                //cv.clear();
                SimpleDateFormat currFormater  = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

                String eDDte;
                try {
                    Date nDate = currFormater.parse(vv[0]);
                    eDDte = postFormater.format(nDate);
                    //cv.put(Table.DATA,eDDte);
                }
                catch (Exception e) {
                }


               ////db.insert(Table.TABLE_NAME,null,cv);
            } dataRead.close();

        } catch (Exception e) { Log.e("TAG",e.toString());

        }
    }

}