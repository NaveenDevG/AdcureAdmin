package com.adcure.adminactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adcure.adminactivity.Constants.AllConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InvoiceLabtest extends AppCompatActivity {



     private TextView paid,addr,gnme,gnum,dte,test_name,txt_sample_y_n,totalprice,reports;
     String dirpath,pid,currentUserid;
     FirebaseAuth mAuth;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_invoice_labtest);
             final Calendar c = Calendar.getInstance();
test_name=findViewById(R.id.test_name);
relativeLayout=findViewById(R.id.sc);
txt_sample_y_n=findViewById(R.id.txt_sample_y_n);
totalprice=findViewById(R.id.totalprice);
gnme=findViewById(R.id.name);
gnum=findViewById(R.id.num);
reports=findViewById(R.id.reports);
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

            String formatted = format1.format(c.getTime());
            dte=findViewById(R.id.dateTdy);
            dte.setText("Date : "+getIntent().getStringExtra("date"));


            paid=findViewById(R.id.paid);
            addr=findViewById(R.id.addres);
            gnme=findViewById(R.id.name);
            gnum=findViewById(R.id.num);

            pid=getIntent().getStringExtra("pid");


            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Pathalogy").
                    child("All Bookings").child(pid);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

//            hashMap.put("labtest_name",tn);
//            hashMap.put("labtests_included",ti);
//            hashMap.put("lab_name",ln);
//            hashMap.put("payment_id",razorpayPaymentID);
//            hashMap.put("name", nme);
//            hashMap.put("email_id", mail);
//            hashMap.put("gender",value );
//            hashMap.put("pickudate", date);
//            hashMap.put("pickuptime", time);
//            hashMap.put("pickedup","n");
//            hashMap.put("reports","n");
//            hashMap.put("delay","n");
//            hashMap.put("number", num);
//            hashMap.put("user_id", currentUserid);
//            hashMap.put("date",saveCurrentdate);
//            hashMap.put("time",saveCurentTime);
//            hashMap.put("state",state.getSelectedItem().toString());
//            hashMap.put("city",city.getSelectedItem().toString());
//            hashMap.put("address", addr);
//            hashMap.put("paid", amnt);
                        String tn=snapshot.child("labtest_name").getValue().toString();
                        test_name.setText(tn);
                        String sYN=  snapshot.child("pickedup").getValue().toString();

                        if(sYN.equals("n")){
                            txt_sample_y_n.setText("Not Picked");
 reports.setText("Sample Not Collected yet.");
                        }
                        else{
                            txt_sample_y_n.setText("Picked up");
                            if(snapshot.child("reports").getValue().toString().equals("n")){
                                reports.setText("Under Process");

                            }else{
                                reports.setText("Generated");

                            }

                        }
String tp=snapshot.child("paid").getValue().toString();
totalprice.setText(tp);
            paid.setText(tp);
            String nme=snapshot.child("name").getValue().toString();
            gnme.setText(nme);
 String mail=snapshot.child("email_id").getValue().toString();
String num=snapshot.child("number").getValue().toString();
            gnum.setText(mail+"\n"+num);

            String addre=snapshot.child("address").getValue().toString();
           String  st=snapshot.child("state").getValue().toString();
           String cty=snapshot.child("city").getValue().toString();
           if(addre==null){
               addr.setText(st+","+cty);

           }else{
               addr.setText(addre+","+st+","+cty);

           }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void layoutToImage() {
        // get view group using reference
        // convert view group to bitmap
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
       Bitmap bm = relativeLayout.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

//        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+"AdCure" + File.separator + "Order_"+pid+".jpg");
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), AllConstants.FOLDER);
        if (!directory.exists()) {
            boolean result = directory.mkdir();
        }
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator + AllConstants.FOLDER
                + File.separator +  "Order_"+pid+".jpg");

        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+AllConstants.FOLDER.toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath +"/"+"Order_"+pid+".pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator +AllConstants.FOLDER+File.separator+ "Order_"+pid+".jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully in AdCure Directory!..\nPath : "+dirpath+"/"+pid+"/.pdf", Toast.LENGTH_LONG).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent downloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
//                    downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(downloadIntent);
//                }},2000);

        } catch (Exception e) {

        }
    }


    public void toPrint(View view) {
//        getToken("Hi","rfe");
if(pid.contains(":")){pid=pid.replace(":","");}
        if(pid.contains(" ")){pid=pid.replace(" ","");}
        if(pid.contains(",")){pid=pid.replace(",","");}

        layoutToImage();
        try {
            imageToPDF();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
