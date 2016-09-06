package com.ui.kason_zhang.notifytheuser;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //displayNotification();
        displayProgressNotification();
    }

    private void displayProgressNotification() {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Download")
                .setContentText("Download in progress");
        final NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int step = 0;
                for(int i=0;i<100;i+=5){
                    step = i;
                    mBuilder.setProgress(100,step,false);
                    notificationManager.notify(1,mBuilder.build());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // When the loop is finished, updates the notification
                mBuilder.setContentText("Download complete")
                        // Removes the progress bar
                        .setProgress(0,0,false);
                notificationManager.notify(1, mBuilder.build());
            }
        }).start();
    }

    private void displayNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello, World");

        Intent intent = new Intent(this,NotificationResult.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0
                ,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,mBuilder.build());
    }
}
