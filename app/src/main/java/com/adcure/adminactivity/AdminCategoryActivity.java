package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.adcure.adminactivity.Appointment.Appointments;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.paperdb.Paper;

public class AdminCategoryActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    public  String spe,id,name,pswrd;
    private Button button,button1;private DatabaseReference productRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
   button=(Button)findViewById(R.id.btn);
   button1=(Button)findViewById(R.id.btn1);
        productRef= FirebaseDatabase.getInstance().getReference();//app/use

        // Bundle bundle=this.getIntent().getExtras();

       // spe=bundle.getString("SPE");
        //button1.setText(spe);

       // Toast.makeText(this, "  "+spe, Toast.LENGTH_SHORT).show();

//   if(bundle!=null) {
//       spe=bundle.getString("SPE");
//     button1.setText(spe);
//   }
       // id=getIntent().getExtras().get("randmid").toString();
   button.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Intent i= new Intent (AdminCategoryActivity.this,AddDoctorActivity.class);
           startActivityForResult(i, 1);
       }
   });

name=getIntent().getStringExtra("nme");
pswrd=getIntent().getStringExtra("pass");
          //  spe=getIntent().getStringExtra("specialst");
           // id=getIntent().getStringExtra("randmid") ;
//        Toast.makeText(this, spe, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "  "+spe, Toast.LENGTH_SHORT).show();

      //  Toast.makeText(this, ""+spe+" "+id, Toast.LENGTH_SHORT).show();
        button1.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           Intent intent1=new Intent(AdminCategoryActivity.this,DisplayingAddedDoctors.class);
//           intent1.putExtra("spec",spe);
//           intent1.putExtra("id",id);
           startActivity(intent1);
       }
   });
       // Toast.makeText(this, ""+strEditText
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        // , Toast.LENGTH_SHORT).show();

    }catch(Exception e){e.getMessage(); }  }

    public void note(View view) {
        startActivity(new Intent(this,Notify.class));
    }

    public void issues(View view) {
        startActivity(new Intent(AdminCategoryActivity.this,DisplayingIssues.class));
    }

    public void downloadExcel(View view)  {

        productRef.child("Appoinments").child("Not Consulted").addValueEventListener(new ValueEventListener() {
                                                                                         @Override
                                                                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                             File path =Environment.getExternalStoragePublicDirectory(
                                                                                                     Environment.DIRECTORY_DOWNLOADS);
//                                                                                             File path =new File(Environment.getExternalStoragePublicDirectory(
//                                                                                                     Environment.DIRECTORY_DOWNLOADS),"test.csv");
                                                                                             try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(path,"test.csv")))) {

                                                                                                 StringBuilder sb = new StringBuilder();
                                                                                                 sb.append("Doctor Name");
                                                                                                 sb.append(',');
                                                                                                 sb.append("Doctor Number");
                                                                                                 sb.append(',');
                                                                                                 sb.append("Specialist Type");
                                                                                                 sb.append(',');
                                                                                                 sb.append("Appointment Date");
                                                                                                 sb.append(',');
                                                                                                 sb.append("Appointment Time");
                                                                                                 sb.append(',');

                                                                                                 sb.append("Patient Name");
                                                                                                 sb.append(',');
                                                                                                 sb.append("Patient Gender");
                                                                                                 sb.append(',');
                                                                                                 sb.append("Patient Number");
                                                                                                 sb.append(',');
                                                                                                 sb.append("Amount Paid");
                                                                                                 sb.append(',');

                                                                                                 sb.append('\n');
                                                                                             for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                                                                 Appointments appointments=dataSnapshot.getValue(Appointments.class);
                                                                                                 String dnme= appointments.getDoctor_Name().toString();
                                                                                                 String amon= appointments.getAmount().toString();
                                                                                                 String dnum=appointments.getDoctor_Number();
                                                                                                 String unme=appointments.getName();
                                                                                                 String gender=appointments.getGender();
                                                                                                 String num=appointments.getNumber();
                                                                                                 String atime=appointments.getTime();
                                                                                                 String date=appointments.getDate();
                                                                                                 String spec=appointments.getSpecialist_Type();

                                                                                                 Appointments appointments1=new Appointments();
                                                                                                 appointments1.setAmount(amon);
                                                                                                 appointments1.setDoctor_Name(dnme);
                                                                                                 sb.append(dnme);
                                                                                                 sb.append(',');
                                                                                                 sb.append(dnum);
                                                                                                 sb.append(',');
                                                                                                 sb.append(spec);
                                                                                                 sb.append(',');
                                                                                                 sb.append(date);
                                                                                                 sb.append(',');
                                                                                                 sb.append(atime);
                                                                                                 sb.append(',');
                                                                                                 sb.append(unme);
                                                                                                 sb.append(',');
                                                                                                 sb.append(gender);
                                                                                                 sb.append(',');
                                                                                                 sb.append(num);
                                                                                                 sb.append(',');
                                                                                                 sb.append(amon);
                                                                                                 sb.append(',');
                                                                                                 sb.append('\n');

                                                                                             }





                                                                                                 writer.write(sb.toString());
                                                                                                 Toast.makeText(AdminCategoryActivity.this, "File saved as:  "+ path.getAbsolutePath()+"/test.csv", Toast.LENGTH_SHORT).show();

//
                                                                                                 new Handler().postDelayed(new Runnable() {
                                                                                                     @Override
                                                                                                     public void run() {
                                                                                                         Intent downloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                                                                                                         downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                         startActivity(downloadIntent);
                                                                                                     }},2000);

                                                                                             } catch (FileNotFoundException e) {
                                                                                                 System.out.println(e.getMessage());
                                                                                             }

                                                                                         }

                                                                                         @Override
                                                                                         public void onCancelled(@NonNull DatabaseError error) {

                                                                                         }
                                                                                     });

    }


        public void downloadExcl(View view) throws IOException {
        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet
                = workbook.createSheet(" Student Data ");

        // creating a row object
        final XSSFRow[] row = new XSSFRow[1];
        Map<Integer, Object[]> studentData
                = new TreeMap<>();


        // This data needs to be written (Object[])


        productRef.child("Appoinments").child("Not Consulted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                studentData.put(
                        1,
                        new Object[] { "No of app", "NAME", "Year" });
             ArrayList editModelArrayList = new ArrayList<Appointments>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Appointments appointments=dataSnapshot.getValue(Appointments.class);
                   String dnme= appointments.getDoctor_Name().toString();
                    String amon= appointments.getAmount().toString();
                    Appointments appointments1=new Appointments();
                    appointments1.setAmount(amon);
                    appointments1.setDoctor_Name(dnme);
editModelArrayList.add(appointments1);
                   // String pat=dataSnapshot.child("name").getValue().toString();

                 //  for (int i=2;i<=dataSnapshot.getChildrenCount();i++){
                   int i=3;
                    i=i+2;
                    studentData.put(
                i,
                new Object[] { "129", "Narayana", "2nd year" });


                }
                studentData.put(
                        3,
                        new Object[] { "19", "Narayana", "2nd year" });
                Set<Integer> keyid = studentData.keySet();

                int rowid = 0;

                // writing the data into the sheets...

                for (Integer key : keyid) {

                    row[0] = spreadsheet.createRow(rowid++);
                    Object[] objectArr = studentData.get(key);
                    int cellid = 0;

                    for (Object obj : objectArr) {
                        Cell cell = row[0].createCell(cellid++);
                        cell.setCellValue((String)obj);
                    }
                }
               // }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    File path = Environment.getExternalStoragePublicDirectory(
           Environment.DIRECTORY_DOCUMENTS);


        FileOutputStream out = new FileOutputStream(
                new File(path,"anooo.xlsx"));

        workbook.write(out);
        out.close();
    }

    public void downloadbtn(View view) {
        startActivity(new Intent(this,DownloadCategory.class));
    }

    public void viewLabTest(View view) {
        startActivity(new Intent(this,LabTestCategories.class));
    }

    public void pharma(View view) {startActivity(new Intent(this,PharaCategory.class));

    }

    public void logout(View view) {
        FirebaseAuth mauth=FirebaseAuth.getInstance();
        mauth.signOut();
startActivity(new Intent(this,OtpActivity.class));
    }

//   private void exportToExcel(Cursor cursor) {
//        final String fileName = "TodoList.xls";
//
//        //Saving file in external storage
//        File sdCard = Environment.getExternalStorageDirectory();
//        File directory = new File(sdCard.getAbsolutePath() + "/javatechig.todo");
//
//        //create directory if not exist
//        if(!directory.isDirectory()){
//            directory.mkdirs();
//        }
//
//        //file path
//        File file = new File(directory, fileName);
//         WorkbookSettings wbSettings = new WorkbookSettings();
//        wbSettings.setLocale(new Locale("en", "EN"));
//        WritableWorkbook workbook;
//
//        try {
//            workbook = Workbook.createWorkbook(file, wbSettings);
//            //Excel sheet name. 0 represents first sheet
//            WritableSheet sheet = workbook.createSheet("MyShoppingList", 0);
//
//            try {
//                sheet.addCell(new Label(0, 0, "Subject")); // column and row
//                sheet.addCell(new Label(1, 0, "Description"));
//                if (cursor.moveToFirst()) {
//                    do {
//                        String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TODO_SUBJECT));
//                        String desc = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TODO_DESC));
//
//                        int i = cursor.getPosition() + 1;
//                        sheet.addCell(new Label(0, i, title));
//                        sheet.addCell(new Label(1, i, desc));
//                    } while (cursor.moveToNext());
//                }
//                //closing cursor
//                cursor.close();
//            } catch (RowsExceededException e) {
//                e.printStackTrace();
//            } catch (WriteException e) {
//                e.printStackTrace();
//            }
//            workbook.write();
//            try {
//                workbook.close();
//            } catch (WriteException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if(resultCode == RESULT_OK) {
//                String strEditText = data.getStringExtra("spe");
//            }
//        }
//    }
}