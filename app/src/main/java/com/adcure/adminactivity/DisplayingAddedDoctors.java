package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.esotericsoftware.kryo.util.IntMap;
import com.adcure.adminactivity.Appointment.AppointmentActivity;
import com.adcure.adminactivity.Feedback.PatientStories;
import com.adcure.adminactivity.Prevalent.DoctorDetails;
import com.adcure.adminactivity.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import io.paperdb.Paper;

public class DisplayingAddedDoctors extends AppCompatActivity {
    private DatabaseReference productRef,spe;
    private RecyclerView recyclerView;
    DatabaseReference temp;
    String speci,drk,nme;  private ProgressDialog dialog;
    private TextView tw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaying_added_doctors);
//        Paper.init(this);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerVieww);
        recyclerView.setHasFixedSize(true);
        tw=(TextView)findViewById(R.id.tvs);
        dialog=new ProgressDialog(this);
     //   Paper.init(this);

//        drk=getIntent().getExtras().get("spe").toString();
//        speci=getIntent().getExtras().get("Did").toString();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        productRef= FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalent.currentUserData.getName())
//                .child(spe).child(id);
//     nme= Paper.book().read(Prevalent.currentUserData
        FirebaseAuth auth=FirebaseAuth.getInstance();
 //Log.e("nme",nme);
//Log.e("ph",phoneKey);
//        String drk=Paper.book().read(Prevalent.dRK);
//        if(spe==null && drk==null){
// productRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(auth.getCurrentUser().getUid()).child("My Doctors");
 productRef = FirebaseDatabase.getInstance().getReference().child("Added Doctors");
   productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                if (dsnapshot.exists()) {

                    FirebaseRecyclerOptions<DoctorDetails> options =
                            new FirebaseRecyclerOptions.Builder<DoctorDetails>()
                                    .setQuery(productRef
                                            ,DoctorDetails.class).build();
                    FirebaseRecyclerAdapter<DoctorDetails, DoctorViewHolder> adapter =
                            new FirebaseRecyclerAdapter<DoctorDetails, DoctorViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull DoctorViewHolder holder, int position, @NonNull final DoctorDetails model) {
                                    holder.doctorname.setText("Name : " + model.getName());
                                    Picasso.get().load(model.getImage()).into(holder.doctorImage);
                                    holder.doctorSpecialist.setText("Specialist : " + model.getSpecialist());
                                    holder.doctorExp.setText("Experience : " + model.getExperience());
                                    holder.doctorState.setText("State : " + model.getState());
                                    holder.appoi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String number=model.getNumber();
                                            String name=model.getName();

                                            Intent intent=new Intent(DisplayingAddedDoctors.this, AppointmentActivity.class);
                                        intent.putExtra("did",number);
                                startActivity(intent);
                                dialog.dismiss();
                                        }
                                    });
                                    holder.feeds.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String number=model.getNumber();
                                            String name=model.getName();
                                            Intent intent=new Intent(DisplayingAddedDoctors.this, PatientStories.class);
                                            intent.putExtra("did",number);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    });
                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                         String number=model.getNumber();
                                         String name=model.getName();
                                         Intent intent=new Intent();
                                        }
                                    });
                                }
                                @NonNull
                                @Override
                                public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_layout, parent, false);
                                    DoctorViewHolder viewHolder = new DoctorViewHolder(view);
                                    return viewHolder;
                                }
                     };


                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.startListening();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    tw.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   dialog.show();
   }


}
