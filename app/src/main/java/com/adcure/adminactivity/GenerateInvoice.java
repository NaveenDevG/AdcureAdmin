package com.adcure.adminactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adcure.adminactivity.Constants.AllConstants;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GenerateInvoice extends AppCompatActivity {
    private long count;private float orderfina;
    Button nextbtn;
    RecyclerView recyclerView;

    ImageView toprof;int i=0;
    String shippedstatus,pid;
    RecyclerView.LayoutManager layoutManager;
    private TextView paid,addr,gnme,gnum,dte,ship,itemcharge,deliverycharge,invId;
    private float ic,dc;
    String dirpath;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);
         relativeLayout=findViewById(R.id.sc);
            final Calendar c = Calendar.getInstance();
invId=findViewById(R.id.invoiceId);
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

            String formatted = format1.format(c.getTime());
         dte=findViewById(R.id.dateTdy);

         dte.setText("Date : "+(getIntent().getStringExtra("date")));
        recyclerView= findViewById(R.id.orderlis);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        pid=getIntent().getStringExtra("pid").toString();
        recyclerView.setLayoutManager(layoutManager);
        cartList();
        paid=findViewById(R.id.paid);
        addr=findViewById(R.id.addres);
        gnme=findViewById(R.id.name);
        gnum=findViewById(R.id.num);
        itemcharge=findViewById(R.id.icharge);
        deliverycharge=findViewById(R.id.scharges);
        if(getIntent().getStringExtra("paid").contains("COD")){
            paid.setText(getIntent().getStringExtra("paid").toString());

        }else{
            paid.setText(getIntent().getStringExtra("paid").toString()+"/-");
            if(Float.parseFloat(getIntent().getStringExtra("paid") )< 599){
                ic=Float.parseFloat(getIntent().getStringExtra("paid") )-49;
                dc=49;
            }else{
                ic=Float.parseFloat(getIntent().getStringExtra("paid") );
                dc=0;
            }

        }
        itemcharge.setText(String.valueOf(ic)+"/-");
        deliverycharge.setText(String.valueOf(dc)+"/-");

        addr.setText( getIntent().getStringExtra("addr").toString());
        gnme.setText( getIntent().getStringExtra("nme").toString());
        gnum.setText(getIntent().getStringExtra("num").toString());

        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void cartList(){
        try {
            DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("Orders").child(pid);
            Query query=productRef.orderByChild("Carts");

            productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        FirebaseRecyclerOptions<Carts> options =
                                new FirebaseRecyclerOptions.Builder<Carts>()
                                        .setQuery(productRef , Carts.class).build();
                        FirebaseRecyclerAdapter<Carts, InvoiceViewHoldfer> adapter =
                                new FirebaseRecyclerAdapter<Carts, InvoiceViewHoldfer>(options) {
                                    @Override
                                    protected void onBindViewHolder(@NonNull InvoiceViewHoldfer holder, int position, @NonNull final Carts model) {
                                      orderfina=0;
                                        i=i+1;
                                        holder.productname.setText(model.getPname());
                                        //   holder.dc.setText("delivery charge : " + model.getDeliverycharge());
 holder.sl.setText(String.valueOf(i));
                                        holder.qty.setText( model.getQuantity());
                                        holder.up.setText(String.valueOf(Float.parseFloat(model.getPrice())));

                                        float onetypeProductpr= (Float.parseFloat(model.getPrice())) * Float.parseFloat(model.getQuantity());
                                        orderfina=  orderfina + onetypeProductpr;
                                    holder.tp.setText(String.valueOf(Float.parseFloat(String.valueOf(orderfina))));
                                    }
                                    @NonNull
                                    @Override
                                    public InvoiceViewHoldfer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(GenerateInvoice.this).inflate(R.layout.invoice_view, parent, false);
                                        InvoiceViewHoldfer viewHolder = new InvoiceViewHoldfer(view);
                                        return viewHolder;
                                    }
                                };
                        recyclerView.setAdapter(adapter);
                        adapter.startListening();
                    }  else{
                        //lvnext.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            if(getIntent().getStringExtra("inv")!=null){
                invId.setVisibility(View.VISIBLE);
                invId.setText("Invoice No : "+getIntent().getStringExtra("inv"));

            }else{
                invId.setVisibility(View.GONE);            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("excc", e.getMessage());
            Toast.makeText(GenerateInvoice.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void layoutToImage() {
        // get view group using reference
        // convert view group to bitmap
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

//        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+"AdCure" + File.separator + "Order_"+pid+".jpg");
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), AllConstants.FOLDER);
        if (!directory.exists()) {
            boolean result = directory.mkdir();
        }
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator + AllConstants.FOLDER
                + File.separator +  "Order_"+pid+".jpg");

        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+AllConstants.FOLDER.toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath +"/"+"Order_"+pid+".pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator +AllConstants.FOLDER+File.separator+ "Order_"+pid+".jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully in AdCure Directory!..\nPath : "+dirpath+"/"+pid+"/.pdf", Toast.LENGTH_LONG).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent downloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
//                    downloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(downloadIntent);
//                }},2000);

        } catch (Exception e) {

        }
    }
    private void getToken(String title,String message) {
        try{


            DatabaseReference database = FirebaseDatabase.getInstance().getReference("AdminToken");

            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String token = snapshot.child("token").getValue().toString();

                    String uid = snapshot.child("auth").getValue().toString();

                    JSONObject to = new JSONObject();
                    JSONObject data = new JSONObject();
                    try {
                        data.put("title", title);
                        data.put("message", message);
                        data.put("hisID", uid);
                        //data.put("hisImage", myImage);
                        to.put("to", token);
                        to.put("data", data);

                        sendNotification(to);

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendNotification(JSONObject to) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AllConstants.NOTIFICATION_URL, to, response -> {
            Log.d("notification", "sendNotification: " + response);
        }, error ->  {
            Log.d("notification", "sendNotification: " + error);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "key=" + AllConstants.SERVER_KEY);
                map.put("Content-Type", "application/json");
                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public void toPrint(View view) {
//        getToken("Hi","rfe");
        layoutToImage();
        try {
            imageToPDF();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
