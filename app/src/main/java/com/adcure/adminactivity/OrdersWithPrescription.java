package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adcure.adminactivity.Prevalent.PrescOrder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class OrdersWithPrescription extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_with_prescription);
        recyclerView=(RecyclerView)findViewById(R.id.totalorders_list);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
txt=findViewById(R.id.no);
        productRef= FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("Order With Prescription");
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Query query=snapshot.getRef();
                    FirebaseRecyclerOptions<PrescOrder> options=
                            new FirebaseRecyclerOptions.Builder<PrescOrder>()
                                    .setQuery(productRef,PrescOrder.class).build();
                    FirebaseRecyclerAdapter<PrescOrder, PresOrderViewHolder> adapter=
                            new FirebaseRecyclerAdapter<PrescOrder, PresOrderViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull PresOrderViewHolder holder, int position, @NonNull final PrescOrder model) {

                                    holder.nme.setText("Name : "+model.getName());
                                    holder.addre.setText("Address : "+model.getAddress());
                                    Picasso.get().load(model.getImgPresc()).into(holder.presImg);
                                    holder.date.setText("Ordered Date : "+model.getDate());
                                    holder.state.setText("State : "+model.getState()+"  City : "+model.getCity());
                                    holder.num.setText("Mobile Number : "+model.getUphone());
                                    holder.mail.setText("Email ID : "+model.getUmail());

                                    holder.toViewPro.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
showPres(model.getImgPresc());
                                        }
                                    });
                                    holder.toMail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent email = new Intent(Intent.ACTION_SEND);
                                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ model.getUmail()});
                                            email.putExtra(Intent.EXTRA_SUBJECT, "Your Prescription Order-"+model.getOid());
                                            email.putExtra(Intent.EXTRA_TEXT, "We have checked your Prescription and Please confirm the Order by revert this mail ");

//need this to prompts email client only
                                            email.setType("message/rfc822");

                                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                                        }
                                    });holder.toDail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                                            callIntent.setData(Uri.parse("tel:"+model.getUphone()));
                                            startActivity(callIntent);
                                        }
                                    });
                                }

                                @NonNull
                                @Override
                                public PresOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.prescriptions_orders,parent,false);
                                    PresOrderViewHolder viewHolder=new PresOrderViewHolder(view);
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
        });


    }
    public void showPres(String uri){
//        getDownload(uri);
        AlertDialog.Builder alertadd = new AlertDialog.Builder(OrdersWithPrescription.this);
        LayoutInflater factory = LayoutInflater.from(OrdersWithPrescription.this);
        final View view = factory.inflate(R.layout.sample, null);
        ImageView img=view.findViewById(R.id.dialog_imageview);
        alertadd.setView(view);
        Picasso.get().load(uri).into(img);
        alertadd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

//        alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dlg, int sumthin) {
//                Picasso.get().load(uri).into(img);
//
//            }
//        });

        alertadd.show();
    }
    public void getDownload(String uri){
        StorageReference storage;
        storage= FirebaseStorage.getInstance().getReference().child("Order With Prescription Images");


        StorageReference islandRef = storage.child(uri);

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Toast.makeText(OrdersWithPrescription.this, "Done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}