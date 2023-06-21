package com.adcure.adminactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TodayOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    private String saveCurrentdate;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_orders);
        recyclerView=(RecyclerView)findViewById(R.id.tdyorder_list);
         layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

         txt=(TextView)findViewById(R.id.no);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate1 = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentdate = currentDate1.format(calendar.getTime());

        productRef= FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("Today Payments").child(saveCurrentdate);
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Query query=snapshot.getRef();
                    FirebaseRecyclerOptions<Orders> options=
                            new FirebaseRecyclerOptions.Builder<Orders>()
                                    .setQuery(productRef,Orders.class).build();
                    FirebaseRecyclerAdapter<Orders, OrderViewHolder> adapter=
                            new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull final Orders model) {

                                    holder.nme.setText("Name : "+model.getGname());
                                    holder.itemcount.setText("Items : "+model.getItems());

                                    holder.paid.setText("Paid : ₹ "+model.getPaid());
                                    holder.payid.setText("Payment id : "+model.getPaymentid());
                                    holder.num.setText("Mobile Number : "+model.getGphone());
                                    holder.orderDate.setText("Ordered Date : "+model.getDate());

                                    holder.toViewPro.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent=new Intent(TodayOrders.this,ShipOrder.class);
                                            intent.putExtra("pid",model.getPaymentid());
                                            intent.putExtra("addr",model.getGaddress());
                                            intent.putExtra("num",model.getGphone());
                                            intent.putExtra("nme",model.getGname());
                                            intent.putExtra("paid",model.getPaid());
                                            intent.putExtra("uid",model.getUid());
                                            intent.putExtra("date",model.getDate());
                                            if(model.getInvoiceid()!=null){
                                                intent.putExtra("inv",model.getInvoiceid());
                                            }
                                             startActivity(intent);                                            // intent.putExtra("pid",model.getPid());
//                                            startActivity(intent);
                                        }
                                    });
                                }

                                @NonNull
                                @Override
                                public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view,parent,false);
                                    OrderViewHolder viewHolder=new OrderViewHolder(view);
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

    }catch (Exception e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    } }
}