package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adcure.adminactivity.Prevalent.Medicine;
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

public class ViewMedicines extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      try{super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicines);
        recyclerView=(RecyclerView)findViewById(R.id.product_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txt=(TextView)findViewById(R.id.nos);
//        if(getIntent().getStringExtra("PID").equals("D")){
//            productRef= FirebaseDatabase.getInstance().getReference().child(getIntent().getStringExtra("SID")).child("All Products");
//
//        }
//        else{
            productRef= FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines");
//        }
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Query query=snapshot.getRef();
                    FirebaseRecyclerOptions<Products> options=
                            new FirebaseRecyclerOptions.Builder<Products>()
                                    .setQuery(productRef,Products.class).build();
                    FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=
                            new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                                    Picasso.get().load(model.getImg1()).into(holder.productImage);
                                    String n=model.getName();

                                    holder.prNo.setText("No : "+model.getNo());
                                    holder.productname.setText("Name : "+model.getName());
                                    holder.proctPrice.setText("Discount Price : ₹ "+model.getPrice());
                                    holder.productQnty.setText("Quantity : "+model.getStock());
                                    holder.addedtime.setText("Added Time : "+model.getTime());
                                    holder.addeddate.setText("Added Date : "+model.getDate());
//

                                    holder.update.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent=new Intent(ViewMedicines.this,UpdateMedicines.class);
                                            intent.putExtra("pid",model.getPid());
                                            startActivity(intent);
                                        }
                                    });
                                    holder.delete.setOnClickListener( new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new AlertDialog.Builder(ViewMedicines.this)
                                                    .setMessage("Delete product")
                                                    .setPositiveButton("Yes",
                                                            (dialog, id) -> {
                                                                // User wants to try giving the permissions again.
                                                                DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Medicines").child(model.getPid());
                                                                DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("Sub Category").child(model.getSubcategory()).child(model.getPid());
                                                                                                                              DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(model.getCategory()).child(model.getSubcategory()).child(model.getPid());
//                                                                DatabaseReference db5=FirebaseDatabase.getInstance().getReference().child(Constants.ADMIN_ID).child("Products in Sub-Category").child(model.getCategory()).child(model.getSub_category()).child(model.getPid());
//                                                                DatabaseReference db6=FirebaseDatabase.getInstance().getReference().child(Constants.ADMIN_ID).child("Products in Category").child(model.getCategory()).child(model.getPid());
                                                                databaseReference4.removeValue();
                                                                databaseReference5.removeValue();
                                                                databaseReference6.removeValue();
                                                                dialog.cancel();

                                                            })
                                                    .setNegativeButton("No",
                                                                        (dialog, id) -> {
                                                                // User doesn't want to give the permissions.
                                                                dialog.cancel();
                                                            })
                                                    .show();
                                            //Intent intent=new Intent(ViewMedicines.this,ProductDetailsActivity.class);
//                                intent.putExtra("pid",model.getPid());
//                                intent.putExtra("pimage",model.getPimage());
//
                                            //startActivity(intent);
                                        }
                                    });
                                }

                                @NonNull
                                @Override
                                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_view,parent,false);
                                    ProductViewHolder viewHolder=new ProductViewHolder(view);
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
        });}catch (Exception e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    } }

    @Override
    protected void onStart() {

        super.onStart();



    }

    public void toProductAll(View view) {
//        Toast.makeText(this, "This site is in under construction", Toast.LENGTH_SHORT).show();
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS);
//                                                                                             File path =new File(Environment.getExternalStoragePublicDirectory(
//                                                                                                     Environment.DIRECTORY_DOWNLOADS),"test.csv");
                    try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(path,"All Medicine List.csv")))) {

                        StringBuilder sb = new StringBuilder();
                        sb.append("Added Date");
                        sb.append(',');
                        sb.append("Product NO");
                        sb.append(',');
                        sb.append("Product Id");
                        sb.append(',');
                        sb.append("Product Name");
                        sb.append(',');
                        sb.append("Category");
                        sb.append(',');
                        sb.append("Sub-Category");
                        sb.append(',');
                        sb.append("Brand or Company");
                        sb.append(',');
                        sb.append("Actual Price");
                        sb.append(',');
                        sb.append("Flat Discount");
                        sb.append(',');
                        sb.append("Extra Discount");
                        sb.append(',');

                        sb.append("Discount Price");
                        sb.append(',');


                        sb.append("Quantity");
                        sb.append(',');


                        sb.append('\n');
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Medicine products =dataSnapshot.getValue(Medicine.class);

                            sb.append(products.getDate().replace(",",""));
                            sb.append(',');
                            sb.append(products.getNo());
                            sb.append(',');
                            sb.append(products.getPid().replace(",",""));
                            sb.append(',');


                            sb.append(products.getName().replace(",",""));
                            sb.append(',');
                            sb.append(products.getCategory().replace(",",""));
                            sb.append(',');
                            sb.append(products.getSubcategory().replace(",",""));
                            sb.append(',');
                            sb.append(products.getCompany().replace(" ",""));
                            sb.append(',');
                            sb.append(products.getPrice());
                            sb.append(',');
                            sb.append(products.getFlat_discount().replace(",",""));
                            sb.append(',');
                            sb.append(products.getExtra_discount().replace(",",""));
                            sb.append(',');
                            sb.append(products.getDiscount_price().replace(",",""));

                            sb.append(',');
                            sb.append(products.getStock().replace(",",""));
                            sb.append(',');
                            sb.append('\n');

                        }





                        writer.write(sb.toString());
                        Toast.makeText(ViewMedicines.this, "File saved as:  "+ path.getAbsolutePath()+"/All Products List.csv", Toast.LENGTH_SHORT).show();

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
                }else {
                    Toast.makeText(ViewMedicines.this, "Not having any Medicines", Toast.LENGTH_SHORT).show();
                }
//

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
    }


}