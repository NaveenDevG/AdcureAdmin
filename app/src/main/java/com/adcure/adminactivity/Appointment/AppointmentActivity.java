package com.adcure.adminactivity.Appointment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adcure.adminactivity.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointmentActivity extends AppCompatActivity {
    private DatabaseReference productRef,spe;
    private RecyclerView recyclerView;String currentUserid;
    private TextView tv;  private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        tv=(TextView)findViewById(R.id.tvs);
        recyclerView=(RecyclerView)findViewById(R.id.rv2);
        recyclerView.setHasFixedSize(true);
        currentUserid=getIntent().getStringExtra("did");

        dialog=new ProgressDialog(this);
        productRef = FirebaseDatabase.getInstance().getReference().child("All Doctors")
                .child(currentUserid).child("My Appointments");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
     productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dsnapshot) {
                if (dsnapshot.exists()) {

                    FirebaseRecyclerOptions<Appointments> options =
                            new FirebaseRecyclerOptions.Builder<Appointments>()
                                    .setQuery(productRef
                                            ,Appointments.class).build();
                    FirebaseRecyclerAdapter<Appointments, UserViewHolder> adapter =
                            new FirebaseRecyclerAdapter<Appointments, UserViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull final UserViewHolder holder, int position, @NonNull final Appointments model) {
                                    holder.usr.setText(model.getName());
                                    holder.dte.setText( model.getDate());
                                    holder.tme.setText( model.getTime());
                                    holder.gen.setText( model.getGender());

                                    holder.paid.setText("Paid : "+model.getAmount()+" ₹");
//                                    for (DataSnapshot dataSnapshot : dsnapshot.getChildren()){
//                                    if (dataSnapshot.getKey().equals("consulted")){
//                                       holder.con.setText("Is he Consulted with this patient : yes");
//                                    }
//                                    else {
//                                        holder.con.setText("Is he Consulted with this patient : No");
//
//                                    }
//                                    }

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("All Doctors").child(currentUserid).child("My Appointments");
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                         //   String value = model.getUser_id();
                                         //   Log.i("value", "onDataChange: " + value);


                                             for (DataSnapshot snapshot1:snapshot.getChildren()){
                                                 if (model.getUser_id().equals(snapshot1.child("user_id").getValue().toString())){
                                                if(snapshot1.child("consulted").exists()){
                                                    holder.con.setText("Is he consulted with this patient : Yes");
                                                 } else
                                                {
                                                    holder.con.setText("Is he consulted with this patient : No");
                                        }
                                                 }
                                             }

                                     }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
//
                                 }

                                @NonNull
                                @Override
                                public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_display, parent, false);
                                    UserViewHolder viewHolder = new UserViewHolder(view);
                                    return viewHolder;
                                }
                            };
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {dialog.dismiss();
tv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });dialog.show();
    }
}