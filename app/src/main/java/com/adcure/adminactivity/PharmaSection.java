package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.aware.PeerHandle;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PharmaSection extends AppCompatActivity {
ArrayList<String> arrayListweigh,arrayList2;
SearchableSpinner weight,cat,sub_cat,discount,return_policcy;private static final int DATE_DIALOG_ID = 1;private static final int DATE_DIALOG_ID1 = 2;
    private int Year,Month,day;
    TextView disprice;
    RadioGroup radioGroup;    private AlertDialog.Builder alertDialog ;
    private Uri imageUri,imageUri1,imageUri2;
    private String productRandomKey, DownloadUri,DownloadUri2,DownloadUri1;
    private static int GALLERYINTENT=1,GALLERYINTENT1=2,GALLERYINTENT2=3;
    private static int CAM=4,CAM1=5,CAM2=6;    private String  saveCurrentdate,saveCurentTime;
ArrayList<String> arrayList,arrayList1;
    RadioButton ypre,nopre;
    String value="";    private ImageView selectitem,selectitem1,selectitem2;
    private StorageReference ProductImageRef;
    Button pd,ed,sub;ProgressDialog dialog;
    private ImageView img;
EditText edNo,edMN,edCN,edP,edD;
    DatabaseReference rootRef,root1,dailyRoot,subroot,root;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharma_section);
        weight=findViewById(R.id.stock);
        cat=findViewById(R.id.category);
        sub_cat=findViewById(R.id.sub_cat);
          discount = (SearchableSpinner) findViewById(R.id.sp2);
          return_policcy = (SearchableSpinner) findViewById(R.id.retunpol);
           disprice=findViewById(R.id.disprice);
        radioGroup=findViewById(R.id.rg);
        dialog=new ProgressDialog(this);
        ypre=findViewById(R.id.ypre);
          root1=FirebaseDatabase.getInstance().getReference().child("Medicines").child("Category");

          sub=findViewById(R.id.submit_btn);
          alertDialog = new AlertDialog.Builder(this);

          nopre=findViewById(R.id.npre);
        arrayListweigh = new ArrayList<>();
pd=findViewById(R.id.pur_date);
ed=findViewById(R.id.exp_date);
edNo=findViewById(R.id.med_no);

        edP=findViewById(R.id.price_pe);
          ProductImageRef= FirebaseStorage.getInstance().getReference().child("Medicine Images");
rootRef=FirebaseDatabase.getInstance().getReference();
        edMN=findViewById(R.id.med_name);
        edD=findViewById(R.id.des);
        edCN=findViewById(R.id.company);


         dailyRoot= FirebaseDatabase.getInstance().getReference();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.ypre:

                        value="yes";
                        break;
                    case R.id.npre:
                        value="no";
                        break;

                }
               // Toast.makeText(getApplicationContext(), "Gender "+value, Toast.LENGTH_SHORT).show();
            }
        });

        pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID1);
            }
        });
        int j;
        for(int i=1;i<=10000;i++){
            j=i;
            arrayListweigh.add(String.valueOf(j));
        }
          arrayList = new ArrayList<>();
          arrayList.add("Category Type");


          arrayList.add("Add Category");

          arrayList1 = new ArrayList<>();
          arrayList1.add("Sub-Category Type");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(arrayAdapter);
        ArrayAdapter<String> weightadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListweigh);
        weight.setAdapter(weightadapter);
        weight.setTitle("Select Weight..");
          selectitem=(ImageView)findViewById(R.id.select_Image);
          selectitem1=(ImageView)findViewById(R.id.select_Image1);
           selectitem2=(ImageView)findViewById(R.id.select_Image2);

          final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, arrayList1);
          arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          sub_cat.setAdapter(arrayAdapter3);

          cat.setTitle("Select Category Type..");

          root = FirebaseDatabase.getInstance().getReference().child("Medicines").child("Category");

          final String[] itms5 = new String[]{ "Extra Discount", "0%","5%","10%","15%", "20%","25%",
                  "30%", "35%","40%","45%", "50%", "55%","60%", "65%","70%","75%", "80%","85%", "90%", "95%","100%"};
          ArrayAdapter <String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itms5);
          return_policcy.setAdapter(adapter5);
          return_policcy.setTitle("Select Extra Discount..");

          final String[] itms2 = new String[]{ "Flat Discount", "0%","5%","10%","15%", "20%","25%",
                  "30%", "35%","40%","45%", "50%", "55%","60%", "65%","70%","75%", "80%","85%", "90%", "95%","100%"};
          ArrayAdapter <String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itms2);
          discount.setAdapter(adapter2);
          discount.setTitle("Select Flat Discount..");
//
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                arrayList.set(0,"Specialist");
//                VlidateProduct();
//
//            }
//        });
          discount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  if (!return_policcy.getSelectedItem().equals("Select Extra Discount..") && !return_policcy.getSelectedItem().equals("Extra Discount") && (!edP.getText().toString().equals("") || edP.getText().toString().equals(null)) && Integer.parseInt(edP.getText().toString())>100 && !discount.getSelectedItem().equals("Select Flat Discount..") && !discount.getSelectedItem().equals("Flat Discount")){

                      String disd=discount.getSelectedItem().toString().replace("%","");
                      String disf=return_policcy.getSelectedItem().toString().replace("%","");
                      int dis,s;
                      s=100-(Integer.parseInt(disd)+Integer.parseInt(disf));
                      dis = (s*Integer.parseInt(edP.getText().toString()))/100;
                      if (String.valueOf(dis).contains("-")){
                          Toast.makeText(PharmaSection.this, "Your Discount reached 100% ", Toast.LENGTH_LONG).show();
                          disprice.setText("Discount Price : --");

                      }else {
                          disprice.setText("Discount Price : "+String.valueOf(dis));

                      }
                  }
                  else {
                      disprice.setText("Discount Price : --");
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {

              }
          });
          return_policcy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  final String[] sp1 =  {adapterView.getSelectedItem().toString()};

                  if (!sp1.equals("Select Extra Discount..") && !sp1.equals("Extra Discount") && (!edP.getText().toString().equals("") || edP.getText().toString().equals(null)) && Integer.parseInt(edP.getText().toString())>100 && !discount.getSelectedItem().equals("Select Flat Discount..") && !discount.getSelectedItem().equals("Flat Discount")){

                      String disd=discount.getSelectedItem().toString().replace("%","");
                      String disf=return_policcy.getSelectedItem().toString().replace("%","");
                      int dis,s;
                      s=100-(Integer.parseInt(disd)+Integer.parseInt(disf));
                      dis = (s*Integer.parseInt(edP.getText().toString()))/100;
                      if (String.valueOf(dis).contains("-")){
                          Toast.makeText(PharmaSection.this, "Your Discount reached 100% ", Toast.LENGTH_LONG).show();
                          disprice.setText("Discount Price : --");

                      }else {
                          disprice.setText("Discount Price : "+String.valueOf(dis));

                      }
                  }
                  else {
                      disprice.setText("Discount Price : --");
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {

              }
          });
           cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                  String name = parent.getItemAtPosition(position).toString();

//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();


                  final String[] sp1 = {parent.getSelectedItem().toString()};
                  root.child(parent.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {

                          arrayList1.clear();
                          arrayList1.add("Sub-Category Type");


                          arrayAdapter3.notifyDataSetChanged();
                          arrayList1.add("Add Sub-Category");

                          for (DataSnapshot childsnap: snapshot.getChildren()){
                              String temp=childsnap.getKey();

                              if(!arrayList1.contains(temp)){


                                  arrayList1.add(temp);
                              }

                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });
                  if (sp1[0].equals("Add Category") ) {
                      alertDialog.setTitle("Add Category");
                      final EditText input = new EditText(PharmaSection.this);
                      alertDialog.setView(input);
                      input.setHint("Enter Category Type");
                      alertDialog.setPositiveButton("YES",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {
                                      // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                      final String string=input.getText().toString();

                                      if (TextUtils.isEmpty(string)){
                                          Toast.makeText(PharmaSection.this, "you forget to write Category Type..", Toast.LENGTH_SHORT).show();
                                          arrayAdapter.notifyDataSetChanged(); } else{
                                          final DatabaseReference rootdef;
                                          rootdef= FirebaseDatabase.getInstance().getReference().child("Medicines").child("Category");
                                          rootdef.child(string).setValue(0);
                                          //dialog.dismiss();

                                          Toast.makeText(PharmaSection.this, "added..", Toast.LENGTH_SHORT).show();
                                          arrayAdapter.notifyDataSetChanged();
                                      }
                                  }
                              });
                      // Setting Negative "NO" Button
                      alertDialog.setNegativeButton("NO",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {
                                      // Write your code here to execute after dialog

                                      arrayAdapter.notifyDataSetChanged();
                                      cat.setSelection(arrayAdapter.getPosition("Category Type"));
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


          sub_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                  String name = parent.getItemAtPosition(position).toString();

//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();

                  final String[] sp1 = {parent.getSelectedItem().toString()};
                  if (sp1[0].equals("Add Sub-Category") && !cat.getSelectedItem().equals("Add Category") && !cat.getSelectedItem().equals("Category Type")) {
                      alertDialog.setTitle("Add Sub-Category");
                      final EditText input = new EditText(PharmaSection.this);
                      alertDialog.setView(input);
                      input.setHint("Enter Sub-Category Type");
                      alertDialog.setPositiveButton("YES",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {
                                      // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                      final String string=input.getText().toString();

                                      if (TextUtils.isEmpty(string)){
                                          Toast.makeText(PharmaSection.this, "you forget to write Sub-Category Type..", Toast.LENGTH_SHORT).show();
                                          arrayAdapter3.notifyDataSetChanged();
                                      }else{
                                          final DatabaseReference rootdef;
                                          rootdef= FirebaseDatabase.getInstance().getReference().child("Medicines").child("Category").child(cat.getSelectedItem().toString());
                                          rootdef.child(string).setValue(0);
                                          //dialog.dismiss();
                                          arrayAdapter3.notifyDataSetChanged();

                                          Toast.makeText(PharmaSection.this, "added..", Toast.LENGTH_SHORT).show();

                                      }
                                  }
                              });
                      // Setting Negative "NO" Button
                      alertDialog.setNegativeButton("NO",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {
                                      // Write your code here to execute after dialog
                                      sub_cat.setSelection(arrayAdapter3.getPosition("Sub-Category Type"));
                                      dialog.cancel();


                                  }
                              });

                      // closed
                      // Showing Alert Message
                      alertDialog.show();
                      // alertDialog.setCancelable(false);
                  }
                  else if((sp1[0].equals("Add Sub-Category") && cat.getSelectedItem().equals("Add Category")) || (sp1[0].equals("Add Sub-Category") && cat.getSelectedItem().equals("Category Type"))){
                      //  sub_cat.setSelection(arrayAdapter3.getPosition(sp));

                      Toast.makeText(PharmaSection.this, "Select Category...", Toast.LENGTH_SHORT).show();
                      sub_cat.setSelection(arrayAdapter3.getPosition("Sub-Category Type"));
                      //arrayAdapter3.notifyDataSetChanged();
                  }
                  else{
                      arrayAdapter3.notifyDataSetChanged();
                  }
              }


              @Override
              public void onNothingSelected(AdapterView <?> parent) {
              }
          });
          selectitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    alertDialog.setTitle("Add Category");

                alertDialog.setPositiveButton("Capture Image",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                                OpenCam();


                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Gallery",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                OpenGallery();


                            }
                        });

                // closed

                // Showing Alert Message
                alertDialog.show();
            }
        });

        selectitem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.setPositiveButton("Capture Image",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                                OpenCam1(); }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Gallery",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                OpenGallery1();


                            }
                        });

                // closed

                // Showing Alert Message
                alertDialog.show();
            }
        });
        selectitem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alertDialog.setPositiveButton("Capture Image",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                                OpenCam2(); }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Gallery",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                OpenGallery2();


                            }
                        });

                // closed

                // Showing Alert Message
                alertDialog.show();                  }
        });

sub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (validateFields().equals("")){
StoreProductInfo();
        }      else {
            Toast.makeText(PharmaSection.this, validateFields(), Toast.LENGTH_LONG).show();

        }  }
});
    }
    private void OpenGallery1() {

        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GALLERYINTENT1);
    }
    private void OpenGallery2() {

        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GALLERYINTENT2);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                //Edit------------------------------------------//
                // set maximum date to be selected as today

                final Calendar c = Calendar.getInstance();
                Year  = c.get(Calendar.YEAR);
                Month = c.get(Calendar.MONTH);
                day   = c.get(Calendar.DAY_OF_MONTH);
                //Edit------------------------------------------//
                DatePickerDialog datePickerDialog= new DatePickerDialog(this, datePickerListener,
                        Year, Month,day);
               // datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                return datePickerDialog;

            case DATE_DIALOG_ID1:
                // set date picker as current date
                //Edit------------------------------------------//
                // set maximum date to be selected as today

                final Calendar c1 = Calendar.getInstance();
                Year  = c1.get(Calendar.YEAR);
                Month = c1.get(Calendar.MONTH);
                day   = c1.get(Calendar.DAY_OF_MONTH);
                //Edit------------------------------------------//
                DatePickerDialog datePickerDialog1= new DatePickerDialog(this, datePickerListener1,
                        Year, Month,day);
                datePickerDialog1.getDatePicker().setMinDate(c1.getTimeInMillis());
                return datePickerDialog1;
        }
        return null;
    }



    private void OpenCam2() {
        try{
            Intent galleryintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
            startActivityForResult(galleryintent,CAM2);
        }
        catch (Exception e){
            e.getMessage();
        }
    }
    private void OpenCam1() {
        try{
            Intent galleryintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
            startActivityForResult(galleryintent,CAM1);
        }
        catch (Exception e){
            e.getMessage();
        }
    }
    private void OpenCam() {
        try{
            Intent galleryintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
            startActivityForResult(galleryintent,CAM);
        }
        catch (Exception e){
            e.getMessage();
        }
    }




    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Year = selectedYear;
            Month = selectedMonth;
            day = selectedDay;

            final Calendar c = Calendar.getInstance();

            String bookingdate = String.valueOf(Year) + "-" + String.valueOf(Month+1) + "-" + String.valueOf(day);

            Button bt_setdate=(Button) findViewById(R.id.pur_date);
            pd.setText(bookingdate);

        }
    };



    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Year = selectedYear;
            Month = selectedMonth;
            day = selectedDay;

            final Calendar c = Calendar.getInstance();

            String bookingdate = String.valueOf(Year) + "-" + String.valueOf(Month+1) + "-" + String.valueOf(day);

            Button bt_setdate=(Button) findViewById(R.id.exp_date);
            ed.setText(bookingdate);

        }
    };


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAM && resultCode==RESULT_OK &&   data!=null){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageUri=getImageUri(this,photo);
//            Bitmap bmp = null;
//            try {
//                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
//            byte[] data = baos.toByteArray();
//            compressImag2e(String.valueOf(imageUri));
            selectitem.setImageBitmap(photo);

//            Bitmap bitmapImage = BitmapFactory.decodeFile( data.getData());
//            int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//            Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

        }     if(requestCode==CAM1 && resultCode==RESULT_OK &&   data!=null){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageUri1=getImageUri(getApplicationContext(),photo);

            selectitem1.setImageBitmap(photo);

        }   if(requestCode==CAM2 && resultCode==RESULT_OK &&   data!=null){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageUri2=getImageUri(getApplicationContext(),photo);
            selectitem2.setImageBitmap(photo);

        }  if(requestCode==GALLERYINTENT  && resultCode==RESULT_OK &&   data!=null){

            imageUri=data.getData();

            selectitem.setImageURI(imageUri);

        }
        if(requestCode==GALLERYINTENT1  && resultCode==RESULT_OK &&   data!=null){

            imageUri1=data.getData();
            selectitem1.setImageURI(imageUri1);

        }
        if(requestCode==GALLERYINTENT2  && resultCode==RESULT_OK &&   data!=null){
            imageUri2=data.getData();
            selectitem2.setImageURI(imageUri2);
        }

    }
    private void OpenGallery() {
        try{
            Intent galleryintent=new Intent( );
            galleryintent.setAction(Intent.ACTION_GET_CONTENT);
            galleryintent.setType("image/*");
            startActivityForResult(galleryintent,GALLERYINTENT);
        }
        catch (Exception e){
            e.getMessage();
        }
    }
    public String validateFields(){
        String alert="";
        String pdt=pd.getText().toString();
        String edt=ed.getText().toString();
        if(imageUri==null && imageUri1==null && imageUri2==null){
            alert+="image manditory\n";
        }
       else if(edP.getText().toString().equals("") ||  edCN.getText().toString().equals("") || edD.getText().toString().equals("")
                || edMN.getText().toString().equals("") || edNo.getText().toString().equals("") || cat.getSelectedItemPosition()<=0 || weight.getSelectedItemPosition()<=0
        )
        {
           alert+=  "Please Fill All Details carefully \n";

        }

         if (pdt.contains("Purchase Date")){
           alert+="Choose Purchase date\n";
        }  if (edt.equals("Expired Date")){
           alert+="Choose Expired Date\n";
        }else if(disprice.getText().toString().equals("Discount Price : --")){
           alert+= "price should be above 100.. ";

        }
        else if (disprice.getText().toString().equals("Discount Price : -")){
            alert+="Select proper discount and price should be above 100.. ";
        }
         if (value.equals(""))
         {
             alert+="Prescription Required\n";
         }        return alert;
    }
    private void StoreProductInfo() {
        dialog.setTitle("Adding the Medicine");
        dialog.setMessage("Dear Admin ,Please wait while we are checking the credintials.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentdate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentdate+saveCurentTime;
        final StorageReference filePath=ProductImageRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpg");
        final StorageReference filePath1=ProductImageRef.child(imageUri1.getLastPathSegment()+productRandomKey+".jpg");
        final StorageReference filePath2=ProductImageRef.child(imageUri2.getLastPathSegment()+productRandomKey+".jpg");
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();
        //uploading the image
        final UploadTask uploadTask=filePath.putFile(imageUri);
//        UploadTask uploadTask = filePath.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg=e.toString();
                Toast.makeText(PharmaSection.this,"Error:",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PharmaSection.this,"Product Image Uploaded Successfully...",Toast.LENGTH_SHORT).show();
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
                            //         Toast.makeText(PharmaSection.this,"got the Product Image saved to database...",Toast.LENGTH_SHORT).show();
                           
                        }
                    }
                });
            }
        });

        final UploadTask uploadTask1=filePath1.putFile(imageUri1);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg=e.toString();
                Toast.makeText(PharmaSection.this,"Error:"+msg,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask1=uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw  task.getException();
                        }
                        DownloadUri1=filePath1.getDownloadUrl().toString();
                        return filePath1.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            DownloadUri1=task.getResult().toString();
                            Toast.makeText(PharmaSection.this,"got the Product Image1 saved to database...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        final UploadTask uploadTask2=filePath2.putFile(imageUri2);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg=e.toString();
                Toast.makeText(PharmaSection.this,"Error:",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask2=uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw  task.getException();
                        }
                        DownloadUri2=filePath2.getDownloadUrl().toString();
                        return filePath2.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            DownloadUri2=task.getResult().toString();
                            Toast.makeText(PharmaSection.this,"got the Medicine Image saved to database...",Toast.LENGTH_SHORT).show();
                            storeMediccinnDetails();
                        }
                    }
                });
            }
        });
    }

    public void storeMediccinnDetails(){

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentdate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurentTime=currentTime.format(calendar.getTime());
        String userRandomKey = edNo.getText().toString()+saveCurrentdate + saveCurentTime;

        String disd=discount.getSelectedItem().toString().replace("%","");
        String disf=return_policcy.getSelectedItem().toString().replace("%","");
        int dis,s;
        s=100-(Integer.parseInt(disd)+Integer.parseInt(disf));
        dis = (s*Integer.parseInt(edP.getText().toString()))/100;
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("All Medicines").child(userRandomKey).exists())){
                    final HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Img1",DownloadUri);
                    hashMap.put("Img2", DownloadUri1);
                    hashMap.put("Pid", userRandomKey);
                    hashMap.put("Img3", DownloadUri2);
                    hashMap.put("No",edNo.getText().toString() );
                    hashMap.put("Name", edMN.getText().toString());
                    hashMap.put("Category",cat.getSelectedItem().toString() );
                    hashMap.put("Company", edCN.getText().toString());
                    hashMap.put("Description", edD.getText().toString());
                    hashMap.put("Pdate", pd.getText().toString());
                    hashMap.put("Price",edP.getText().toString() );
                    hashMap.put("Edate", ed.getText().toString());
                    hashMap.put("Stock", weight.getSelectedItem().toString());
                    hashMap.put("Prescription", value);

                    hashMap.put("Subcategory",sub_cat.getSelectedItem().toString());
                    hashMap.put("Date",saveCurrentdate);
                    hashMap.put("Time",saveCurentTime);

                    hashMap.put("Flat_discount",discount.getSelectedItem().toString());
                    hashMap.put("Extra_discount",return_policcy.getSelectedItem().toString());
                    hashMap.put( "Discount_price",String.valueOf(dis));
                    rootRef.child("All Medicines").child(userRandomKey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PharmaSection.this, "Success", Toast.LENGTH_SHORT).show();
         startActivity(new Intent(PharmaSection.this,PharaCategory.class));
                                //loadingbar.dismiss();

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





    @Override
    protected void onStart() {
        super.onStart();

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

    }
}