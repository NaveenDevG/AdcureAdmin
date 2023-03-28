package com.adcure.adminactivity;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TotalOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_total_orders);
            recyclerView = (RecyclerView) findViewById(R.id.totalorders_list);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            txt = (TextView) findViewById(R.id.no);

            productRef = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("All Payments");

            try{
            productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Query query = snapshot.getRef();
                        FirebaseRecyclerOptions<Orders> options =
                                new FirebaseRecyclerOptions.Builder<Orders>()
                                        .setQuery(productRef, Orders.class).build();
                        FirebaseRecyclerAdapter<Orders, OrderViewHolder> adapter =
                                new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {

                                    @Override
                                    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull final Orders model) {

                                        holder.nme.setText("Name : " + model.getGname());
                                        holder.itemcount.setText("Items : " + model.getItems());
                                        if (model.getPaid().contains("COD")) {
                                            holder.paid.setText(model.getPaid());

                                        } else {
                                            holder.paid.setText("Paid : ₹ " + model.getPaid());

                                        }
                                        holder.payid.setText("Payment id : " + model.getPaymentid());
                                        holder.num.setText("Mobile Number : " + model.getGphone());
                                        if(model.getShipped().equals("y") && model.getDelivered().equals("y")){}
                                        else if (model.getShipped().equals("y")) {
                                            holder.shipstate.setText("shipping state: shipped");
                                        } else if (model.getDelivered().equals("y")) {
                                            holder.shipstate.setText("shipping state: Delivered");

                                        } else {
                                            holder.shipstate.setText("shipping state: Not shipped");

                                        }
                                        holder.orderDate.setText("Ordered Date : " + model.getDate());
//                                        if (getIntent().getStringExtra("PID").equals("D")) {
//                                            holder.toViewPro.setVisibility(View.GONE);
//                                        }
                                        holder.toViewPro.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(TotalOrders.this, ShipOrder.class);

                                                intent.putExtra("pid",model.getPaymentid());
                                                intent.putExtra("addr",model.getGaddress());
                                                intent.putExtra("num",model.getGphone());
                                                intent.putExtra("nme",model.getGname());
                                                intent.putExtra("paid",model.getPaid());
                                                intent.putExtra("uid",model.getUid());
                                                 intent.putExtra("date",model.getDate());
                                                startActivity(intent);
//                                            startActivity(intent);
                                            }
                                        });
                                    }

                                    @NonNull
                                    @Override
                                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view, parent, false);
                                        OrderViewHolder viewHolder = new OrderViewHolder(view);
                                        return viewHolder;
                                    }
                                };
                        recyclerView.setAdapter(adapter);
                        adapter.startListening();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
                Log.e("error",e.getMessage());
            }
    }
        catch(Exception e){
        e.getMessage();
    }}
}