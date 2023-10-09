package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adcure.adminactivity.Prevalent.Medicine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PharaCategory extends AppCompatActivity {
ProgressDialog loadingbar;
    private  static ArrayList<Medicine> detailsArrayList=new ArrayList<Medicine>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phara_category);
        loadingbar=new ProgressDialog(this);

    }

    public void viewMedicine(View view) {    startActivity(new Intent(this,ViewMedicines.class));

    }

    public void addMedicine(View view) {

        startActivity(new Intent(this,PharmaSection.class));
    }

    public void toOrder(View view) {
        startActivity(new Intent(this,OrderSection.class));

    }

    public void toOrderPres(View view) {
        startActivity(new Intent(this,OrdersWithPrescription.class));

    }

    public void uploadCSV(View view) {
        {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(PharaCategory.this);
builder.setTitle("Alert");
            builder.setMessage("Did you added the file as PharmaBulkUpload in Download folder ?")
                    .setCancelable(false);
                  builder .setPositiveButton("Yes",
                (dialog, id) -> {
                    // User wants to try giving the permissions again.
            shown();
                    dialog.cancel();

                })
                .setNegativeButton("No",
                        (dialog, id) -> {
                            // User doesn't want to give the permissions.
                            dialog.cancel();
                        });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
//            alert.setTitle("ALERT");
            builder.show();
        }
    }
    public void shown(){
//        alertDialog = new AlertDialog.Builder(this);

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(PharaCategory.this);
        builder.setTitle("ALERT");

        builder.setMessage("Are you sure to upload the PharmaBulkUpload.csv file ?")
                .setCancelable(false);
         builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    // User wants to try giving the permissions again.
                                readDataLineByLine(String.valueOf(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"PharmaBulkUpload.csv")));

                    dialog.cancel();

                })
                .setNegativeButton("No",
                        (dialog, id) -> {
                            // User doesn't want to give the permissions.
                            dialog.cancel();
                        });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        builder.show();
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
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                 loadingbar.dismiss();
                Toast.makeText(this, "Can not found the PharmaBulkUpload.csv file in download folder..", Toast.LENGTH_SHORT).show();
            }
            Log.v("toto",String.valueOf(detailsArrayList.size()));
            Log.v("toto",String.valueOf(detailsArrayList));

            insertDataInto();
            
            loadingbar.dismiss();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Can not found the PharmaBulkUpload.csv file in download folder..", Toast.LENGTH_SHORT).show();
               loadingbar.dismiss();   }
    }

    public void insertDataInto() {

         for (int i=1;i<detailsArrayList.size();i++){
            FirebaseAuth mAuth ;
            DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();

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
                                    if(finalI==detailsArrayList.size()-1){

loadingbar.dismiss();
 Toast.makeText(PharaCategory.this, "Success", Toast.LENGTH_SHORT).show();
 startActivity(new Intent(PharaCategory.this,ViewMedicines.class));
                                    }

//                                    finish();
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