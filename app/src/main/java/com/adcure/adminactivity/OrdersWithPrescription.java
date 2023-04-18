package com.adcure.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;

import io.socket.client.Manager;

public class OrdersWithPrescription extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    TextView txt;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_with_prescription);
        recyclerView=(RecyclerView)findViewById(R.id.totalorders_list);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
txt=findViewById(R.id.no);
dialog=new ProgressDialog(this);
dialog.setTitle("Please Wait..");
dialog.setCanceledOnTouchOutside(false);
dialog.setMessage("Downloading Prescription...if it's taking long please click on cancel.. download it again..");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//dismiss dialog
            }
        });
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
showPres(model.getImgPresc(),model.getName(),model.getOid());
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
    void savefile(URI sourceuri)
    {
        String sourceFilename= sourceuri.getPath();
        String destinationFilename = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+File.separatorChar+"abc.jpg";

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while(bis.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void showPres(String uri,String name,String oid){
//        getDownload(uri);
        AlertDialog.Builder alertadd = new AlertDialog.Builder(OrdersWithPrescription.this);
        LayoutInflater factory = LayoutInflater.from(OrdersWithPrescription.this);
        final View view = factory.inflate(R.layout.sample, null);
        ImageView img=view.findViewById(R.id.dialog_imageview);
        alertadd.setView(view);
        Picasso.get().load(uri).into(img);

//        saveBitmapIntoSDCardImage(OrdersWithPrescription.this,getContactBitmapFromURI(OrdersWithPrescription.this, Uri.parse(uri)));
//       savefile(Uri.parse(uri));
        alertadd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        alertadd.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                 egtPicassoDowlnload(uri,name,oid);
            }
        });




//        alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dlg, int sumthin) {
//                Picasso.get().load(uri).into(img);
//
//            }
//        });

        AlertDialog a=alertadd.create();
        a.show();
        Button bq = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setBackgroundColor(Color.BLUE);
        bq.setPadding(10,0,10,0);
    }
void egtPicassoDowlnload(String url,String name,String oid){
        dialog.show();
    Picasso.get()
            .load(url)
            .into(new Target() {
                      @Override
                      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                          try {
                              String root = Environment.getExternalStorageDirectory().toString();
                              File myDir = new File(root + "/AdCure Prescriptions");

                              if (!myDir.exists()) {
                                  myDir.mkdirs();
                              }
Date date=new Date();
                              String name1= name+".jpg";
                              myDir = new File(myDir, name1);
                              FileOutputStream out = new FileOutputStream(myDir);

                              bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                              out.flush();
                              out.close();
dialog.dismiss();
                              Intent intent = new Intent(Intent.ACTION_VIEW);
//                              File file = new File(Environment.getExternalStorageDirectory() + "/FolderName/" + "yourFile.apk");
                              Uri data = FileProvider.getUriForFile(OrdersWithPrescription.this, BuildConfig.APPLICATION_ID +".provider",myDir);
                              intent.setDataAndType(data,"image/");
                              intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                              startActivity(intent);
                              Toast.makeText(OrdersWithPrescription.this, "Image saved into "+myDir.getPath(), Toast.LENGTH_LONG).show();

                          } catch(Exception e){
                              // some action
                              dialog.dismiss();
                              Toast.makeText(OrdersWithPrescription.this,e.getMessage(), Toast.LENGTH_LONG).show();
                          }
                      }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }


                      @Override
                      public void onPrepareLoad(Drawable placeHolderDrawable) {
                      }
                  }
            );
}
     public Bitmap getContactBitmapFromURI(Context context, Uri uri) {
        try {

            InputStream input = context.getContentResolver().openInputStream(uri);
            if (input == null) {
                return null;
            }
            return BitmapFactory.decodeStream(input);
        }
        catch (FileNotFoundException e)
        {

        }
        return null;

    }
    public  File saveBitmapIntoSDCardImage(Context context, Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS);
        myDir.mkdirs();

        String fname = "file_name" + ".jpg";
        File file = new File (myDir, fname);

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}