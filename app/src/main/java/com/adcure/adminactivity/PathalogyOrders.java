package com.adcure.adminactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PathalogyOrders extends AppCompatActivity {
    private DatabaseReference productRef,spe;
    private RecyclerView recyclerView;
    String currentUserid;
    private FirebaseAuth mAuth;
    private TextView tw;  private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathalogy_orders);
        recyclerView=(RecyclerView)findViewById(R.id.rv4);
        recyclerView.setHasFixedSize(true);
        tw=(TextView)findViewById(R.id.tvs);

        dialog=new ProgressDialog(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final Intent in=getIntent();
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if (firebaseUser  != null){
            currentUserid=mAuth.getCurrentUser().getUid();
        }
        productRef = FirebaseDatabase.getInstance().getReference().child("Pathalogy").child("All Bookings");

        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                if (dsnapshot.exists()) {

                    FirebaseRecyclerOptions<LabtestBookings> options =
                            new FirebaseRecyclerOptions.Builder<LabtestBookings>()
                                    .setQuery(productRef
                                            , LabtestBookings.class).build();
                    FirebaseRecyclerAdapter<LabtestBookings, PathalogyOrderView> adapter =
                            new FirebaseRecyclerAdapter<LabtestBookings, PathalogyOrderView>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull final PathalogyOrderView holder, int position, @NonNull final LabtestBookings model) {
                                    holder.testName.setText(model.getLabtest_name());
                                    holder.testsIncluded.setText( model.getLabtests_included());
                                    holder.amountpaid.setText( model.getPaid());
                                    holder.date.setText(model.getDate());
                                    holder.time.setText(model.getTime());


                                    holder.btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(PathalogyOrders.this, LabTestOrderedViewActivity.class);


                                            intent.putExtra("pid",model.getPayment_id());
                                            intent.putExtra("uid",model.getUser_id());
                                            startActivity(intent);
                                            dialog.dismiss();
                                            ////    Toast.makeText(ConsultNowActivity2.this, ""+dctr_num, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @NonNull
                                @Override
                                public PathalogyOrderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pathalogy_order_view, parent, false);
                                    PathalogyOrderView viewHolder = new PathalogyOrderView(view);
                                    return viewHolder;
                                }
                            };
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    tw.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });dialog.show();
    }

    public void toBack1(View view) {
        onBackPressed();
    }

}