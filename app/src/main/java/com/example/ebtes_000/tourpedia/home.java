package com.example.ebtes_000.tourpedia;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageButton;

import java.util.Timer;
import java.util.TimerTask;

public class home extends AppCompatActivity {


    public Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_home);


        MyTimerTask myTask = new MyTimerTask();
        Timer myTimer = new Timer();

        //myTimer.schedule(myTask, 50000, 1500);

        // declaring the img buttons
        ImageButton guideMe = (ImageButton) findViewById(R.id.guideBtn);
        ImageButton Plan = (ImageButton) findViewById(R.id.planBtn);
        ImageButton identify = (ImageButton) findViewById(R.id.identifyBtn);
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);

        // to guide me
        guideMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, guideMe.class);
                startActivity(intent);
            }
        });

        // to plan
        Plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, plans.class);
                startActivity(intent);
            }
        });

        // to identify img
        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage(R.string.isThereGlass)
                        .setTitle(R.string.isThereGlassTitle);



                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        Intent intent=new Intent(context,GlassActivity.class);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No button
                        Intent intent=new Intent(context,CameraActivity.class);
                        startActivity(intent);

                    }
                });


                //Todo: put the check box

                AlertDialog dialog = builder.create();

                dialog.show();


            }
        });

        // to settings
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, settings.class);
                startActivity(intent);
            }
        });

        // to filters
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, filter.class);
                startActivity(intent);
            }
        });
    }

    class MyTimerTask extends TimerTask {
        public void run() {

            generateNotification(getApplicationContext(), "Hello");
        }
    }

    private void generateNotification(Context context, String message) {

        int icon = R.drawable.logoc;
        long when = System.currentTimeMillis();
        String appname = context.getResources().getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Notification notification;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, home.class), 0);

        // To support 2.3 os, we use "Notification" class and 3.0+ os will use
        // "NotificationCompat.Builder" class.
        if (currentapiVersion < android.os.Build.VERSION_CODES.HONEYCOMB) {
            notification = new Notification(icon, message, 0);
            //notification.setLatestEventInfo(context, appname, message, contentIntent);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify((int) when, notification);

        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    context);
            notification = builder.setContentIntent(contentIntent)
                    .setSmallIcon(icon).setTicker(appname).setWhen(0)
                    .setAutoCancel(true).setContentTitle(appname)
                    .setContentText(message).build();

            notificationManager.notify((int) when, notification);

        }

    }
}
