package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddLabTest extends AppCompatActivity {
    private SearchableSpinner spinner1,discount,amnt_spin,sub_cat,return_policcy,state,city;
    private AlertDialog.Builder alertDialog ;
    private ArrayList<String> arrayList,arrayList1,arrayList2,cityAL,stateAL;
    DatabaseReference  root,root1,dailyRoot;    private String saveCurrentdate,saveCurentTime,productRandomKey;

    ProgressDialog dialog;
    EditText priceLT,pc,wtp,ti,tnmes,ltn,labNme,labLoc;
    TextView disprice;
    String lid;
    private DatabaseReference productref,allProductRef,productRf,stateRef,cityRef;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lab_test);
if(getIntent().getStringExtra("lid")!=null){
    lid=getIntent().getStringExtra("lid").toString();


}

            dialog=new ProgressDialog(this);
            // spinner3=(Spinner)findViewById(R.id.sp3);
            alertDialog = new AlertDialog.Builder(this);
            labNme=findViewById(R.id.labname);
            labLoc=findViewById(R.id.testLoc);

            spinner1 = (SearchableSpinner) findViewById(R.id.sp1);
          city=findViewById(R.id.city);
          state=findViewById(R.id.state);

            stateRef = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("States") ;
            cityRef = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Cities") ;

            productref = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Labtests in Category") ;
            productRf = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Labtests in Sub-Category") ;
pc=findViewById(R.id.txt_lt_pc);
wtp=findViewById(R.id.txt_packagewhy);
ti=findViewById(R.id.txt_lt_testsIncluded);
tnmes=findViewById(R.id.txt_lt_testsnames);
btn=findViewById(R.id.submit_btn);
ltn=findViewById(R.id.txt_lt_name);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        validateItem();
    }
});
             allProductRef=FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("All Labtests");
            discount = (SearchableSpinner) findViewById(R.id.sp2);
            return_policcy = (SearchableSpinner) findViewById(R.id.retunpol);
            priceLT=findViewById(R.id.price_lt);
            disprice=findViewById(R.id.disprice);
            sub_cat = (SearchableSpinner) findViewById(R.id.subcat);
            // spinner3=(Spinner)findViewById(R.id.sp3);
            alertDialog = new AlertDialog.Builder(this);
            arrayList = new ArrayList<>();
            arrayList.add("Category Type");


            arrayList.add("Add Category");

            arrayList1 = new ArrayList<>();
            arrayList1.add("Sub-Category Type");

cityAL=new ArrayList<>();
stateAL=new ArrayList<>();
stateAL.add("--State--");
stateAL.add("Add State");

cityAL.add("--City--");



            final String[] itms5 = new String[]{ "Extra Discount", "0%","5%","10%","15%", "20%","25%",
                    "30%", "35%","40%","45%", "50%", "55%","60%", "65%","70%","75%", "80%","85%", "90%", "95%","100%"};
            ArrayAdapter <String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itms5);
            return_policcy.setAdapter(adapter5);
            return_policcy.setTitle("Select Extra Discount..");

            final ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, stateAL);
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            state.setAdapter(stateAdapter);
            final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, cityAL);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city.setAdapter(cityAdapter);

            final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, arrayList1);
            arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sub_cat.setAdapter(arrayAdapter3);

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, arrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(arrayAdapter);
            String sp= (spinner1.getSelectedItem().equals("Category")) ? "yes" :"no " ;
            Toast.makeText(this, sp, Toast.LENGTH_SHORT).show();
            spinner1.setTitle("Select Category Type..");

            root = FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Category");

            dailyRoot=FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Day Wise Prducts");
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        final EditText input = new EditText(AddLabTest.this);
                        alertDialog.setView(input);
                        input.setHint("Enter Category Type");
                        alertDialog.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                        final String string=input.getText().toString();

                                        if (TextUtils.isEmpty(string)){
                                            Toast.makeText(AddLabTest.this, "you forget to write Category Type..", Toast.LENGTH_SHORT).show();
                                            arrayAdapter.notifyDataSetChanged(); } else{
                                            final DatabaseReference rootdef;
                                            rootdef= FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Category");
                                            rootdef.child(string).setValue(0);
                                            //dialog.dismiss();

                                            Toast.makeText(AddLabTest.this, "added..", Toast.LENGTH_SHORT).show();
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
                                        spinner1.setSelection(arrayAdapter.getPosition("Category Type"));
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
                    if (sp1[0].equals("Add Sub-Category") && !spinner1.getSelectedItem().equals("Add Category") && !spinner1.getSelectedItem().equals("Category Type")) {
                        alertDialog.setTitle("Add Sub-Category");
                        final EditText input = new EditText(AddLabTest.this);
                        alertDialog.setView(input);
                        input.setHint("Enter Sub-Category Type");
                        alertDialog.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                        final String string=input.getText().toString();

                                        if (TextUtils.isEmpty(string)){
                                            Toast.makeText(AddLabTest.this, "you forget to write Sub-Category Type..", Toast.LENGTH_SHORT).show();
                                            arrayAdapter3.notifyDataSetChanged();
                                        }else{
                                            final DatabaseReference rootdef;
                                            rootdef= FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("Category").child(spinner1.getSelectedItem().toString());
                                            rootdef.child(string).setValue(0);
                                            //dialog.dismiss();
                                            arrayAdapter3.notifyDataSetChanged();

                                            Toast.makeText(AddLabTest.this, "added..", Toast.LENGTH_SHORT).show();

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
                    else if((sp1[0].equals("Add Sub-Category") && spinner1.getSelectedItem().equals("Add Category")) || (sp1[0].equals("Add Sub-Category") && spinner1.getSelectedItem().equals("Category Type"))){
                        //  sub_cat.setSelection(arrayAdapter3.getPosition(sp));

                        Toast.makeText(AddLabTest.this, "Select Category...", Toast.LENGTH_SHORT).show();
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

            state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                    String name = parent.getItemAtPosition(position).toString();

//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();


                    final String[] sp1 = {parent.getSelectedItem().toString()};
                    stateRef.child(parent.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            cityAL.clear();
                            cityAL.add("--City--");


                            cityAdapter.notifyDataSetChanged();
                            cityAL.add("Add City");

                            for (DataSnapshot childsnap: snapshot.getChildren()){
                                String temp=childsnap.getKey();

                                if(!cityAL.contains(temp)){


                                    cityAL.add(temp);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if (sp1[0].equals("Add State") ) {
                        alertDialog.setTitle("Add State");
                        final EditText input = new EditText(AddLabTest.this);
                        alertDialog.setView(input);
                        input.setHint("Enter State name");
                        alertDialog.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                        final String string=input.getText().toString();

                                        if (TextUtils.isEmpty(string)){
                                            Toast.makeText(AddLabTest.this, "you forget to write State name..", Toast.LENGTH_SHORT).show();
                                            stateAdapter.notifyDataSetChanged();
                                        } else{
                                            final DatabaseReference rootdef;
                                            rootdef= FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("States");
                                            rootdef.child(string).setValue(0);
                                            //dialog.dismiss();

                                            Toast.makeText(AddLabTest.this, "added..", Toast.LENGTH_SHORT).show();
                                            stateAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog

                                        stateAdapter.notifyDataSetChanged();
                                        spinner1.setSelection(stateAdapter.getPosition("--State--"));
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


            city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                    String name = parent.getItemAtPosition(position).toString();

//                dialog.setTitle("Adding Specialist Type");
//                dialog.setMessage("Dear Admin, Please wait while we are adding another Specialist.");
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();

                    final String[] sp1 = {parent.getSelectedItem().toString()};
                    if (sp1[0].equals("Add City") && !state.getSelectedItem().equals("Add State") && !state.getSelectedItem().equals("--State--")) {
                        alertDialog.setTitle("Add City");
                        final EditText input = new EditText(AddLabTest.this);
                        alertDialog.setView(input);
                        input.setHint("Enter City Name");
                        alertDialog.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
//                                    To ,  V ast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                        final String string=input.getText().toString();

                                        if (TextUtils.isEmpty(string)){
                                            Toast.makeText(AddLabTest.this, "you forget to write Sub-Category Type..", Toast.LENGTH_SHORT).show();
                                            cityAdapter.notifyDataSetChanged();
                                        }else{
                                            final DatabaseReference rootdef;
                                            rootdef= FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("States").child(state.getSelectedItem().toString());
                                            rootdef.child(string).setValue(0);
                                            //dialog.dismiss();
                                            cityAdapter.notifyDataSetChanged();

                                            Toast.makeText(AddLabTest.this, "added..", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        city.setSelection(cityAdapter.getPosition("--City--"));
                                        dialog.cancel();


                                    }
                                });

                        // closed
                        // Showing Alert Message
                        alertDialog.show();
                        // alertDialog.setCancelable(false);
                    }
                    else if((sp1[0].equals("Add City") && state.getSelectedItem().equals("Add State")) || (sp1[0].equals("Add City") && state.getSelectedItem().equals("--State--"))){
                        //  sub_cat.setSelection(arrayAdapter3.getPosition(sp));

                        Toast.makeText(AddLabTest.this, "Select State...", Toast.LENGTH_SHORT).show();
                        city.setSelection(cityAdapter.getPosition("--City--"));
                        //arrayAdapter3.notifyDataSetChanged();
                    }
                    else{
                        cityAdapter.notifyDataSetChanged();
                    }

                    if(city.getSelectedItemPosition()<2){
                        labLoc.setBackgroundResource(R.drawable.disable);
                        labLoc.setEnabled(false);
                    }else{labLoc.setBackgroundResource(R.drawable.box_style);
                        labLoc.setEnabled(true);

                    }
                }


                @Override
                public void onNothingSelected(AdapterView <?> parent) {
                }
            });

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
                    if (!return_policcy.getSelectedItem().equals("Select Extra Discount..") && !return_policcy.getSelectedItem().equals("Extra Discount") && (!priceLT.getText().toString().equals("") || priceLT.getText().toString().equals(null)) && Integer.parseInt(priceLT.getText().toString())>100 && !discount.getSelectedItem().equals("Select Flat Discount..") && !discount.getSelectedItem().equals("Flat Discount")){

                        String disd=discount.getSelectedItem().toString().replace("%","");
                        String disf=return_policcy.getSelectedItem().toString().replace("%","");
                        int dis,s;
                        s=100-(Integer.parseInt(disd)+Integer.parseInt(disf));
                        dis = (s*Integer.parseInt(priceLT.getText().toString()))/100;
                        if (String.valueOf(dis).contains("-")){
                            Toast.makeText(AddLabTest.this, "Your Discount reached 100% ", Toast.LENGTH_LONG).show();
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

        if (!sp1.equals("Select Extra Discount..") && !sp1.equals("Extra Discount") && (!priceLT.getText().toString().equals("") || priceLT.getText().toString().equals(null)) && Integer.parseInt(priceLT.getText().toString())>100 && !discount.getSelectedItem().equals("Select Flat Discount..") && !discount.getSelectedItem().equals("Flat Discount")){

            String disd=discount.getSelectedItem().toString().replace("%","");
            String disf=return_policcy.getSelectedItem().toString().replace("%","");
            int dis,s;
            s=100-(Integer.parseInt(disd)+Integer.parseInt(disf));
            dis = (s*Integer.parseInt(priceLT.getText().toString()))/100;
            if (String.valueOf(dis).contains("-")){
                Toast.makeText(AddLabTest.this, "Your Discount reached 100% ", Toast.LENGTH_LONG).show();
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
     priceLT.addTextChangedListener(textWatcher);   }catch (Exception e){
            e.getMessage();
        }
    }
    public static String extractDigits(final String in) {
        final Pattern p = Pattern.compile( "(\\d{6})" );
        final Matcher m = p.matcher( in );
        if ( m.find() ) {
            return m.group( 0 );
        }
        return "";
    }
    private void validateItem() {
        try{

            String tests=tnmes.getText().toString();

            if(TextUtils.isEmpty(ltn.getText().toString()) || TextUtils.isEmpty(ti.getText().toString()) ||  TextUtils.isEmpty(tnmes.getText().toString())
                    ||  TextUtils.isEmpty(pc.getText().toString()) ||  TextUtils.isEmpty(wtp.getText().toString()) ||
                    TextUtils.isEmpty(priceLT.getText().toString()) || TextUtils.isEmpty(labNme.getText().toString())|| TextUtils.isEmpty(labLoc.getText().toString()) ||  spinner1.getSelectedItemPosition()<2 ||  sub_cat.getSelectedItemPosition()<2
                    || state.getSelectedItemPosition()<2 || city.getSelectedItemPosition()<2){
                Toast.makeText(AddLabTest.this, "Please fill all details..", Toast.LENGTH_LONG).show();

            }if(extractDigits(labLoc.getText().toString()).equals("")){
                Toast.makeText(AddLabTest.this, "Please Enter Zipcode in LAB Location..", Toast.LENGTH_LONG).show();

            }
            else if(disprice.getText().toString().equals("Discount Price : --")){
                Toast.makeText(AddLabTest.this, "price should be above 100.. ", Toast.LENGTH_SHORT).show();

            }
          else if (disprice.getText().toString().equals("Discount Price : -")){
                Toast.makeText(AddLabTest.this, "Select proper discount and price should be above 100.. ", Toast.LENGTH_SHORT).show();
            }
          else if(!tests.equals("")){
                String[] testsCount=tests.split(",");
                int tinclud=Integer.parseInt(ti.getText().toString());
                if (testsCount.length!=tinclud){

                    Toast.makeText(AddLabTest.this, "Test names count should be equal to tests included..", Toast.LENGTH_LONG).show();
                }
                else{
                    saveProductInfoToDatabase();
                }

            }
            if(city.getSelectedItemPosition()<2){
                labLoc.setBackgroundResource(R.drawable.disable);
                labLoc.setEnabled(false);
            }else{labLoc.setBackgroundResource(R.drawable.box_style);
                labLoc.setEnabled(true);

            }
        }
        catch (Exception e){
            e.getMessage();
        }

    }
    private void saveProductInfoToDatabase() {
        dialog.setTitle("Adding Labtest");
        dialog.setMessage("Dear Admin ,Please wait while we are checking the details.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentdate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentdate+saveCurentTime;
        String disd=discount.getSelectedItem().toString().replace("%","");
        String disf=return_policcy.getSelectedItem().toString().replace("%","");
        int dis,s;
        s=100-(Integer.parseInt(disd)+Integer.parseInt(disf));
        dis = (s*Integer.parseInt(priceLT.getText().toString()))/100;
        final HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("Lid",productRandomKey);
        productMap.put("Date",saveCurrentdate);
        productMap.put("Time",saveCurentTime);
        productMap.put("Labtest_name",ltn.getText().toString());
         productMap.put("Tests_included",ti.getText().toString());
        productMap.put("Test_names",tnmes.getText().toString());
        productMap.put("Package_covers",pc.getText().toString());
        productMap.put("Description",wtp.getText().toString());
        productMap.put("Actual_price",priceLT.getText().toString());
        productMap.put("Category",spinner1.getSelectedItem().toString().toString());
        productMap.put("Sub_category",sub_cat.getSelectedItem().toString());
        productMap.put("Flat_discount",discount.getSelectedItem().toString());
        productMap.put("Extra_discount",return_policcy.getSelectedItem().toString());
          productMap.put( "Discount_price",String.valueOf(dis));
          productMap.put("Avail_state",state.getSelectedItem().toString());
          productMap.put("Avail_city",city.getSelectedItem().toString());
          productMap.put("Lab_name",labNme.getText().toString());
          productMap.put("Lab_location",labLoc.getText().toString());
         productref.child(spinner1.getSelectedItem().toString()).child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // if(task.isSuccessful()){
                allProductRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //    if (task.isSuccessful()){
                        dailyRoot.child(saveCurrentdate).child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // if (task.isSuccessful()){

                                productRf.child(spinner1.getSelectedItem().toString()).child(sub_cat.getSelectedItem().toString()).child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
//                                                     Products products=new Products();
//                                                     products.setActual_price(pprice);
//                                                     products.setBrand_or_company(brand.getText().toString());
//                                                     products.setDate(saveCurrentdate);
//                                                     products.setTime(saveCurentTime);
//                                                     products.setDescription(pdescrip);
//                                                     products.setName(pname);
//                                                     products.setCategory(spinner1.getSelectedItem().toString());
//                                                     products.setDelivery(dcharge);
//                                                     products.setDiscount(discount.getSelectedItem().toString());
//                                                     products.setSub_category(sub_cat.getSelectedItem().toString());
//                                                     products.setDiscount_price(String.valueOf(dis));
//                                                     products.setPid(productRandomKey);
//                                                     products.setQuantity(qty.getText().toString());
//                                                     products.setPimage(DownloadUri);
                                            Intent inten=new Intent(AddLabTest.this,AdminCategoryActivity.class);
                                            startActivity(inten);
                                        }
                                        else {
                                            dialog.dismiss();
                                            String message=task.getException().toString();
                                            Toast.makeText(AddLabTest.this,"Error.."+message,Toast.LENGTH_SHORT).show();
//
                                        }
                                    }
                                });



                                Toast.makeText(AddLabTest.this,"Product is added SuccessFully..",Toast.LENGTH_SHORT).show();

                                //  }
                            }
                        });

                        //  }
                    }
                });

                dialog.dismiss();
                // }
//                else {
//                    dialog.dismiss();
//                    String message=task.getException().toString();
//                    Toast.makeText(AddProductActivity.this,"Error.."+message,Toast.LENGTH_SHORT).show();
//
//                }
            }

            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    TextWatcher textWatcher=new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {

            if (!return_policcy.equals("Select Extra Discount..") && !return_policcy.equals("Extra Discount") && (!priceLT.getText().toString().equals("") || priceLT.getText().toString().equals(null)) && Integer.parseInt(priceLT.getText().toString())>100 && !discount.getSelectedItem().equals("Select Flat Discount..") && !discount.getSelectedItem().equals("Flat Discount")){

                String disd=discount.getSelectedItem().toString().replace("%","");
                String disf=return_policcy.getSelectedItem().toString().replace("%","");
                int dis,s;
                s=100-(Integer.parseInt(disd)+Integer.parseInt(disf));
                dis = (s*Integer.parseInt(priceLT.getText().toString()))/100;
                if (String.valueOf(dis).contains("-")){
                    Toast.makeText(AddLabTest.this, "Your Discount reached 100% ", Toast.LENGTH_LONG).show();
                    disprice.setText("Discount Price : --");

                }else {
                    disprice.setText("Discount Price : "+String.valueOf(dis));

                }
            }
            else {
                disprice.setText("Discount Price : -");
            }
        }catch (Exception e){
            e.getMessage();
        }

    }
};
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

        stateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnap: snapshot.getChildren()){
                    String temp=childsnap.getKey();

                    if(!stateAL.contains(temp)){
                        stateAL.add(temp);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}