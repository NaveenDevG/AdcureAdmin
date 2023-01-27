package com.adcure.adminactivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;


import com.adcure.adminactivity.Constants.AllConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.poi.ss.formula.functions.Today;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FirebaseNotificationService extends FirebaseMessagingService {


    MediaPlayer mediaPlayer;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> map = remoteMessage.getData();

           // Uri uri = RingtoneManager.getActualDefaultRingtoneUri(FirebaseNotificationService.this, RingtoneManager.TYPE_RINGTONE);


            //videocall
            String title = map.get("title");
            String message = map.get("message");

            String hisID = map.get("hisID");

            //messages
//            String mtitle = map.get("mtitle");
//            String mmessage = map.get("mmessage");
//            String mhisID = map.get("mhisID");
//if (title.equals(null) && message.equals(null) && hisID.equals(null)){
//    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
//        createOreoNotification1(mtitle, mmessage, mhisID);
//    else
//        createNormalNotification1(mtitle, mmessage, mhisID);
//} else{
            if (title.equals("New Message") || title.equals("Order") || title.equals("LabTest") || title.equals("Order With Prescription")) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                    createOreoNotification1(title, message, hisID);
                else
                    createNormalNotification1(title, message, hisID);
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                    createOreoNotification(title, message, hisID);
                else
                    createNormalNotification(title, message, hisID);
            }


//if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
//        createOreoNotification1(mtitle, mmessage, hisID);
//    else
//        createNormalNotification1(mtitle, mmessage, hisID);
//}
//        if (remoteMessage.getData().size() > 0) {
//            Map<String, String> map = remoteMessage.getData();
//
//            String mtitle = map.get("mtitle");
//            String mmessage = map.get("mmessage");
//
//            String hisID = map.get("hisID");
//
//
//
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
//                 createOreoNotification1(mtitle, mmessage, hisID);
//            else
//                createNormalNotification1(mtitle, mmessage, hisID );
//        }
            //   else Log.d("TAG", "onMessageReceived: no data ");


            super.onMessageReceived(remoteMessage);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        updateToken(s);
        super.onNewToken(s);
    }

    private void updateToken(String token) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admins").child(getUID()).child("Details").child("token");
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            databaseReference.updateChildren(map);
        }

    }

    public String getUID() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getUid();
    }

    //Video
    private void createNormalNotification(String title, String message, String hisID) {

    // CommonMediaPlayer.getMediaPlayerInstance().playAudioFile(this,uri);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        CommonMediaPlayer.getMediaPlayerInstance().playAudioFile(this,uri);

       // Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AllConstants.CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.app_lo_foreground)
                .setAutoCancel(false)
                .setOngoing(true)
                .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));


        Intent intent = new Intent(this, TodayOrders.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(new Random().nextInt(85 - 65), builder.build());

       // ringtone.play();

    }

    //Video
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createOreoNotification(String title, String message, String hisID) {

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        CommonMediaPlayer.getMediaPlayerInstance().playAudioFile(this,uri);

  /*      mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();*/

        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);

        NotificationChannel channel = new NotificationChannel(AllConstants.CHANNEL_ID, "Message", NotificationManager.IMPORTANCE_HIGH);
        channel.setShowBadge(true);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(this, TodayOrders.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new Notification.Builder(this, AllConstants.CHANNEL_ID)

                .setContentTitle(title)
                .setContentText(message)
                .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null))
                .setSmallIcon(R.mipmap.app_lo_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();
        manager.notify(100, notification);

       // ringtone.play();

    }

    private void createNormalNotification1(String title, String message, String hisID) {

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AllConstants.CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.app_lo_foreground)
                .setAutoCancel(true)
                .setSound(uri)
                .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));

        Intent intent=new Intent();
        if(title.equals("Order")  ){
            intent = intent.setClass(this, OrderSection.class);

        }else if(title.equals("LabTest")){
            intent = intent.setClass(this, PathalogyOrders.class);
        }else if(title.equals("Order With Prescription")){
            intent=intent.setClass(this,OrdersWithPrescription.class);
        }
        else{

            intent = intent.setClass(this, DisplayingAddedDoctors.class);


        }        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(new Random().nextInt(85 - 65), builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createOreoNotification1(String title, String message, String hisID) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationChannel channel = new NotificationChannel(AllConstants.CHANNEL_ID, "Message", NotificationManager.IMPORTANCE_HIGH);
        channel.setShowBadge(true);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Intent intent=new Intent();
if(title.equals("Order")){
    intent = intent.setClass(this, OrderSection.class);

}else if(title.equals("LabTest")){
    intent = intent.setClass(this, PathalogyOrders.class);
}
else{

        intent = intent.setClass(this, DisplayingAddedDoctors.class);


}
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new Notification.Builder(this, AllConstants.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null))
                .setSmallIcon(R.mipmap.app_lo_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setSound(uri)
                .build();
        manager.notify(100, notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
