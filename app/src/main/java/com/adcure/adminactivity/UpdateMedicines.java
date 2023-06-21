package com.adcure.adminactivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateMedicines extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String value,sub,cat;
    private DatabaseReference databaseReference1;
    private String pid;    private StorageReference ProductImageRef;

    CircleImageView Image;                float disp,sd;

    ImageView selectitem,selectitem1,selectitem2;
    TextInputLayout pname,desc,ap,dis,edis,quan,brnd,num,dcharge;
    TextView dp;
    RadioGroup radioGroup,radioGroup1;    private AlertDialog.Builder alertDialog ;
    private Uri imageUri,imageUri1,imageUri2;
    private String productRandomKey, DownloadUri,DownloadUri2,DownloadUri1;
    private static int GALLERYINTENT=1,GALLERYINTENT1=2,GALLERYINTENT2=3;
    private static int CAM=4,CAM1=5,CAM2=6;
    ImageView back;
    Button update;
    boolean img=false;
    ProgressDialog dialog;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medicines);
        pid=getIntent().getStringExtra("pid");
            alertDialog = new AlertDialog.Builder(this);
            ProductImageRef= FirebaseStorage.getInstance().getReference().child("Medicine Images");
dialog=new ProgressDialog(this);
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
        relativeLayout=findViewById(R.id.rl_imgs);
            selectitem=(ImageView)findViewById(R.id.select_Image);
            selectitem1=(ImageView)findViewById(R.id.select_Image1);
            selectitem2=(ImageView)findViewById(R.id.select_Image2);

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
                    if(!dataSnapshot.child("Img1").getValue().toString().equals("")) {
                        img=false;
                        relativeLayout.setVisibility(View.GONE);
                        Image.setVisibility(View.VISIBLE);
                        Picasso.get().load(dataSnapshot.child("Img1").getValue().toString()).into(Image);
                    }else{
                        img=true;
                        relativeLayout.setVisibility(View.VISIBLE);
                        Image.setVisibility(View.INVISIBLE);
                    }

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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(!img) {
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines");
    databaseReference2.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                String value4 = dataSnapshot1.getKey().toString();

                Log.i("value4", "onDataChange: " + value4);

                if (pid.equals(value4)) {


                    DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines").child(value4);
                    //  DatabaseReference db5=FirebaseDatabase.getInstance().getReference().child("").child("Products in Sub-Category").child(dataSnapshot1.child("Category").getValue().toString()).child(dataSnapshot1.child("Sub_category").getValue().toString()).child(value4);
                    // DatabaseReference db6=FirebaseDatabase.getInstance().getReference().child("").child("Products in Category").child(dataSnapshot1.child("Category").getValue().toString()).child(value4);
                    DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("Sub Category").child(sub).child(value4);
                    DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(cat).child(sub).child(value4);
//
                    final HashMap<String, Object> productMap = new HashMap<>();

                    String disd = dis.getEditText().getText().toString().replace("%", "");

                    String dise = edis.getEditText().getText().toString().replace("%", "");

                    sd = 100 - (Float.parseFloat(disd) + Float.parseFloat(dise));
                    disp = (sd * Float.parseFloat(ap.getEditText().getText().toString())) / 100;
                    //  dis.getEditText().setText(disp);
                    productMap.put("Name", pname.getEditText().getText().toString());
                    productMap.put("Stock", quan.getEditText().getText().toString());

                    productMap.put("Description", desc.getEditText().getText().toString());

                    productMap.put("Flat_discount", dis.getEditText().getText().toString());
                    productMap.put("Extra_discount", edis.getEditText().getText().toString());

                    productMap.put("Price", ap.getEditText().getText().toString());
                    productMap.put("Company", brnd.getEditText().getText().toString());
                    productMap.put("No", num.getEditText().getText().toString());
                    productMap.put("Discount_price", String.valueOf(disp));
                    if (String.valueOf(disp).contains("-")) {
                        Toast.makeText(UpdateMedicines.this, "Discount should be less than 100%.", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.i("hashmap", "onDataChange: " + databaseReference4.updateChildren(productMap));
                        Log.i("hashmap", "onDataChange: " + databaseReference5.updateChildren(productMap));
                        Log.i("hashmap", "onDataChange: " + databaseReference6.updateChildren(productMap));

                        Toast.makeText(UpdateMedicines.this, "Your item Updated.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}else {
    if(imageUri==null && imageUri1==null && imageUri2==null){
        Toast.makeText(UpdateMedicines.this, "images manditory", Toast.LENGTH_SHORT).show();
    }else {
        storeImagesInfo();
    }
}

            }
        });


        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }  }

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
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }
    public void storeImagesInfo(){
        dialog=new ProgressDialog(this);
        dialog.setTitle("Adding the Medicine");
        dialog.setMessage("Dear Admin ,Please wait while we are checking the credintials.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
      String  saveCurrentdate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
      String  saveCurentTime=currentTime.format(calendar.getTime());
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
                Toast.makeText(UpdateMedicines.this,"Error:",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UpdateMedicines.this,"Product Image Uploaded Successfully...",Toast.LENGTH_SHORT).show();
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
                            //         Toast.makeText(UpdateMedicines.this,"got the Product Image saved to database...",Toast.LENGTH_SHORT).show();

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
                Toast.makeText(UpdateMedicines.this,"Error:"+msg,Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(UpdateMedicines.this,"got the Product Image1 saved to database...",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UpdateMedicines.this,"Error:",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(UpdateMedicines.this,"got the Medicine Image saved to database...",Toast.LENGTH_SHORT).show();
                            storeMediccinnDetails();
                        }
                    }
                });
            }
        });
    }

    private void storeMediccinnDetails() {
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String value4 = dataSnapshot1.getKey().toString();

                    Log.i("value4", "onDataChange: " + value4);

                    if (pid.equals(value4)) {


                        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines").child(value4);
                        //  DatabaseReference db5=FirebaseDatabase.getInstance().getReference().child("").child("Products in Sub-Category").child(dataSnapshot1.child("Category").getValue().toString()).child(dataSnapshot1.child("Sub_category").getValue().toString()).child(value4);
                        // DatabaseReference db6=FirebaseDatabase.getInstance().getReference().child("").child("Products in Category").child(dataSnapshot1.child("Category").getValue().toString()).child(value4);
                        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("Sub Category").child(sub).child(value4);
                        DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(cat).child(sub).child(value4);
//
                        final HashMap<String, Object> productMap = new HashMap<>();

                        String disd = dis.getEditText().getText().toString().replace("%", "");

                        String dise = edis.getEditText().getText().toString().replace("%", "");

                        sd = 100 - (Float.parseFloat(disd) + Float.parseFloat(dise));
                        disp = (sd * Float.parseFloat(ap.getEditText().getText().toString())) / 100;
                        //  dis.getEditText().setText(disp);
                        productMap.put("Name", pname.getEditText().getText().toString());
                        productMap.put("Stock", quan.getEditText().getText().toString());

                        productMap.put("Description", desc.getEditText().getText().toString());

                        productMap.put("Flat_discount", dis.getEditText().getText().toString());
                        productMap.put("Extra_discount", edis.getEditText().getText().toString());

                        productMap.put("Price", ap.getEditText().getText().toString());
                        productMap.put("Company", brnd.getEditText().getText().toString());
                        productMap.put("No", num.getEditText().getText().toString());
                        productMap.put("Discount_price", String.valueOf(disp));
                        productMap.put("Img1",DownloadUri);
                        productMap.put("Img2", DownloadUri1);
                        productMap.put("Img3", DownloadUri2);
                        if (String.valueOf(disp).contains("-")) {
                            Toast.makeText(UpdateMedicines.this, "Discount should be less than 100%.", Toast.LENGTH_SHORT).show();
dialog.cancel();
                        } else {
                            Log.i("hashmap", "onDataChange: " + databaseReference4.updateChildren(productMap));
                            Log.i("hashmap", "onDataChange: " + databaseReference5.updateChildren(productMap));
                            Log.i("hashmap", "onDataChange: " + databaseReference6.updateChildren(productMap));
dialog.cancel();
                            Toast.makeText(UpdateMedicines.this, "Your item Updated.", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

