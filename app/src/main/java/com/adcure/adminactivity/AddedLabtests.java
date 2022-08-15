package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adcure.adminactivity.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class AddedLabtests extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_labtests);
        recyclerView=(RecyclerView)findViewById(R.id.product_list);
        recyclerView.setHasFixedSize(true);
         layoutManager=new GridLayoutManager( AddedLabtests.this,2 );
         recyclerView.setLayoutManager(layoutManager);
        txt=(TextView)findViewById(R.id.nos);
        productRef= FirebaseDatabase.getInstance().getReference().child("Lab Tests").child("All Labtests");
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Query query=snapshot.getRef();
                    FirebaseRecyclerOptions<Labtests> options=
                            new FirebaseRecyclerOptions.Builder<Labtests>()
                                    .setQuery(productRef,Labtests.class).build();
                    FirebaseRecyclerAdapter<Labtests, LabtestViewHolder> adapter=
                            new FirebaseRecyclerAdapter<Labtests, LabtestViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull LabtestViewHolder holder, int position, @NonNull final Labtests model) {

                                     holder.ltname.setText(model.getLabtest_name());
                                    holder.ti.setText(model.getTests_included()+"+");
                                    holder.fdis.setText(model.getFlat_discount()+" OFF");
                                    holder.edis.setText(model.getExtra_discount()+" OFF");
                                    holder.ap.setText("₹"+model.getActual_price());
                                    if(Integer.parseInt(model.getDiscount_price())==0){
                                        holder.dp.setText("Free");

                                    }else{
                                        holder.dp.setText("₹"+model.getDiscount_price());

                                    }
                                    String[] testsCount=model.getTest_names().split(",");
 if (testsCount.length>3){
     String sent= "";
     for (int i=0 ;i<=testsCount.length;i++){
         sent=sent+testsCount[i]+",";
         if(i==2){
//             sent=sent+testsCount[i];

             break;
         }
     }
     holder.ltt.setText(sent+" & More");


 }else if(testsCount.length<2){
     holder.ltt.setText(model.getTest_names()+" & More\n");

 }else{
     holder.ltt.setText(model.getTest_names()+" & More");

 }
                                     holder.ap.setPaintFlags(holder.ap.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
holder.lt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});
//                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Intent intent=new Intent(DisplayingAllProducts.this,ItemViewActivity.class);
//                                            intent.putExtra("pid",model.getPid());
//                                            startActivity(intent);
//                                        }
//                                    });
                                 }

                                @NonNull
                                @Override
                                public LabtestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.labtest_view,parent,false);
                                    LabtestViewHolder viewHolder=new LabtestViewHolder(view);
                                    return viewHolder;
                                }
                            };
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                }else{
                    recyclerView.setVisibility(View.GONE);
                    txt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}

    @Override
    protected void onStart() {

        super.onStart();



    }

//    public void toProductAll(View view) {
//        productRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    File path = Environment.getExternalStoragePublicDirectory(
//                            Environment.DIRECTORY_DOWNLOADS);
////                                                                                             File path =new File(Environment.getExternalStoragePublicDirectory(
////                                                                                                     Environment.DIRECTORY_DOWNLOADS),"test.csv");
//                    try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(path,"All Products List.csv")))) {
//
//                        StringBuilder sb = new StringBuilder();
//
//                        sb.append("Product Name");
//                        sb.append(',');
//                        sb.append("Category");
//                        sb.append(',');
//                        sb.append("Sub-Category");
//                        sb.append(',');
//                        sb.append("Brand or Company");
//                        sb.append(',');
//                        sb.append("Color");
//                        sb.append(',');
//                        sb.append("Actual Price");
//                        sb.append(',');
//                        sb.append("Discount");
//                        sb.append(',');
//
//                        sb.append("Discount Price");
//                        sb.append(',');
//                        sb.append("Added Date");
//                        sb.append(',');
//
//                        sb.append("Quantity");
//                        sb.append(',');
//                        sb.append("Product Id");
//                        sb.append(',');
//
//
//                        sb.append('\n');
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            Products appointments=dataSnapshot.getValue(Products.class);
//                            String pid= appointments.getPid().replace(","," ").toString();
//                            String nme= appointments.getName().toString();
//                            String cat=appointments.getCategory();
//                            String subcat=appointments.getSub_category();
//                            String brand=appointments.getBrand_or_company();
//                            String color=appointments.getColor();
//                            String ap=appointments.getActual_price();
//                            String dis=appointments.getDiscount();
//                            String dp=appointments.getDiscount_price();
//                            String ad=appointments.getDate().replace(","," ");
//                            String q=appointments.getQuantity();
//
//                            sb.append(nme);
//                            sb.append(',');
//                            sb.append(cat);
//                            sb.append(',');
//                            sb.append(subcat);
//                            sb.append(',');
//                            sb.append(brand);
//                            sb.append(',');
//                            sb.append(color);
//                            sb.append(',');
//                            sb.append(ap);
//                            sb.append(',');
//                            sb.append(dis);
//                            sb.append(',');
//                            sb.append(dp);
//                            sb.append(',');
//                            sb.append(ad);
//                            sb.append(',');
//                            sb.append(q);
//                            sb.append(',');
//                            sb.append(pid);
//                            sb.append(',');
//                            sb.append('\n');
//
//                        }
//
//
//
//
//
//                        writer.write(sb.toString());
//                        Toast.makeText(DisplayingAllProducts.this, "File saved as:  "+ path.getAbsolutePath()+"/All Products List.csv", Toast.LENGTH_SHORT).show();
//
////
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent downloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
//                                downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(downloadIntent);
//                            }},2000);
//
//                    } catch (FileNotFoundException e) {
//                        System.out.println(e.getMessage());
//                    }
//                }else {
//                    Toast.makeText(DisplayingAllProducts.this, "Not having any Appointments", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

}