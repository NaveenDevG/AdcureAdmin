package com.adcure.adminactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Notify extends AppCompatActivity {
    Button btn;
    private String url="https://fcm.googleapis.com/fcm/send";
    private RequestQueue requestQueue;
    private EditText title,body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        btn = (Button) findViewById(R.id.txt);
        requestQueue= Volley.newRequestQueue(this);
        title=(EditText)findViewById(R.id.title_name);
        body=(EditText)findViewById(R.id.body_txt);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        // FirebaseMessaging.getInstance().getToken();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        final SimpleDateFormat df1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String fd = df.format(c);
        final String fd1 = df1.format(c);
        Date date=new Date();
        final int min=date.getMinutes();
        onStart();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Date d=new Date();
          Calendar c=Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY,18);
        c.set(Calendar.MINUTE,14);
        c.set(Calendar.SECOND,0);
        Toast.makeText(this, "" +   c.getTime(), Toast.LENGTH_SHORT).show();
        if (System.currentTimeMillis()== c.getTime().getMinutes()) {

            sendNotification();
            title.setText("");
            body.setText("");
        }

    }

    public void sendNotification(){
        JSONObject mainobj=new JSONObject();
        try {
            String tit=title.getText().toString();
            String bdy=body.getText().toString();
            mainobj.put("to","/topics/"+"news");
            JSONObject notifiobj=new JSONObject();
            notifiobj.put("title",tit);
            notifiobj.put("body",bdy);
            JSONObject extaObj=new JSONObject();
            extaObj.put("festivalName","Dussera");
            extaObj.put("Category","festival");
            mainobj.put("notification",notifiobj);
            mainobj.put("data",extaObj);
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url,
                    mainobj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header=new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAr_hdF-Y:APA91bFWJyjv4f-oY0ylTXbGc24GIVba4yKf7Sxrhse0cT-1NR-_GXyxcgUZPOKoU6xGrGRtIP9UDZGrB3HIkmcJ2KXMxtYEzSey3sByv5ailcgPEv5g4AIpoQXcpzCgpFQm0d-g1fGY");
                    return header;
                }
            }; requestQueue.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    //   @Override
//    protected void onStart() {
//        super.onStart();
//      }
//
//    public void notifi(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel=new NotificationChannel("n","n"
//            ,NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager=getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//     NotificationCompat.Builder builder=new NotificationCompat.Builder(this,
//             "n")
//             .setContentText("Whats auto")
//             .setSmallIcon(R.drawable.ic_launcher_background)
//              .setAutoCancel(true)
//             .setContentText("festval date....");
//     NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
//     managerCompat.notify(999,builder.build());
// }
//    public class SndDataService extends Service {
//
//
//        private final  LocalBinder mBinder = new LocalBinder();
//        protected Handler handler;
//        protected Toast mToast;
//
//        public class LocalBinder extends Binder {
//            public SndDataService getService() {
//                return  SndDataService.this;
//            }
//        }
//
//        @Override
//        public IBinder onBind(Intent intent) {
//            return mBinder;
//        }
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//
//        }
//
//        @Override
//        public void onDestroy() {
//            super.onDestroy();
//        }
//
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            handler = new Handler();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Date c = Calendar.getInstance().getTime();
//                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//                    SimpleDateFormat df1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
//                    String fd = df.format(c);
//                    String fd1 = df1.format(c);
//// write your code to post content on server
//
//                     notifi();
//                }
//
//
//
//            });
//            return android.app.Service.START_STICKY;
//        }
//
//    }
}
