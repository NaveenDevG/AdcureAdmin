package com.adcure.adminactivity.Feedback;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class PatientStories extends AppCompatActivity {
    private DatabaseReference productRef,spe;
    private RecyclerView recyclerView;String currentUserid;
    private TextView tv;  private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_stories);
        tv=(TextView)findViewById(R.id.tvs21);
        recyclerView=(RecyclerView)findViewById(R.id.rf);
        recyclerView.setHasFixedSize(true);
        currentUserid=getIntent().getStringExtra("did");

        dialog=new ProgressDialog(this);
        productRef = FirebaseDatabase.getInstance().getReference().child("All Doctors")
                .child(currentUserid).child("My Feedbacks");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
 productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                if (dsnapshot.exists()) {

                    FirebaseRecyclerOptions<feedbacks> options =
                            new FirebaseRecyclerOptions.Builder<feedbacks>()
                                    .setQuery(productRef
                                            ,feedbacks.class).build();
                    FirebaseRecyclerAdapter<feedbacks, StoryViewHolder> adapter =
                            new FirebaseRecyclerAdapter<feedbacks, StoryViewHolder>(options) {

                                @Override
                                protected void onBindViewHolder(@NonNull final StoryViewHolder holder, int position, @NonNull final feedbacks model) {
                                   holder.unme.setText(model.getUser_name());
                                    holder.uid.setText( model.getUser_id());
                                    holder.tme.setText( model.getTime());
                                    holder.dte.setText( model.getDate());
                              holder.cmnt.setText(model.getComment());

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
//                                            Intent intent=new Intent(PatientStories.this,ChatActivity.class);
//                                            String nme= String.valueOf(holder.usr.getText());
//
//                                            String num=model.getNumber();
//                                            intent.putExtra("unum",num);
//                                            String uid=model.getUser_id();
//                                            String dnum=model.getDoctor_Number();
//                                            intent.putExtra("name",nme);
//                                            intent.putExtra("uid",uid);
//                                            intent.putExtra("num",dnum);
//
//                                            startActivity(intent);
//                                            dialog.dismiss();                                        ////    Toast.makeText(ConsultNowActivity2.this, ""+dctr_num, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @NonNull
                                @Override
                                public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_story, parent, false);
                                    StoryViewHolder viewHolder = new StoryViewHolder(view);
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

    public void Gobck(View view) {
        onBackPressed();
    }
}