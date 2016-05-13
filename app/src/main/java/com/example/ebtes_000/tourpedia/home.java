package com.example.ebtes_000.tourpedia;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class home extends AppCompatActivity {


    public Context context=this;
    EditText place;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    String[] places;
    // Places List
    PlacesList nearPlaces;
    // GPS Location
    GPSTracker gps;
    // Google Places object
    GooglePlaces googlePlaces;
    // Progress dialog
    ProgressDialog pDialog;
    Place near;
    // filters preferences
    String distancePref  ;
    int ratingPref;

    //plan
    String planName; // should be date
    ListView listOfEvents;
    ArrayList<slot> slots = null;
    String splits[] = null;
    LinearLayout allInfo;
    LinearLayout eventSec;
    String oldName = "";
    String oldDate = "";

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    //Settings Shares values
    Boolean isGoogleGlassExist = false;
    Boolean isAlertPlansOn = false;
    Boolean isAroundMeOn = false;

    ArrayList<String> planEventsTime = null;
    ArrayList<String> planEventName = null;

    String NextEventName;
    String NextEventTime;
    String NextPlan;
    Boolean repeated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //finish(); // Exit
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_home);
        GetSetting(); // Getting the Setting values from the Shared Preferences
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
                /*Intent intent = new Intent(home.this, guideMe.class);
                startActivity(intent);*/
                Intent intent = new Intent(home.this, guideMe.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.putExtra("EXIT", true);
                startActivity(intent);
                //finish();
            }
        });
        // to plan
        Plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, plans.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        // to identify img
        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if(isGoogleGlassExist){
               /* builder.setMessage(R.string.isThereGlass)
                        .setTitle(R.string.isThereGlassTitle);
                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {*/
                        // User clicked Yes button
                        Intent intent=new Intent(context,GlassActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(context, "Change settings if you want to use mobile camera", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    /*}
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No button
                        Intent intent=new Intent(context,CameraActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                //Todo: here is identify
                AlertDialog dialog = builder.create();
                dialog.show();*/}

                else{
                    Intent intent=new Intent(context,CameraActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(context, "Change settings if you have Google glass", Toast.LENGTH_SHORT).show();
                    startActivity(intent);



                }
            }
        });
        // to settings
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        // to filters
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, filter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // Around Me Function
        if(isAroundMeOn){
        // Check if Internet present
            isInternetPresent = ConnectionDetector.isConnectingToInternet(getApplicationContext());
            if (!isInternetPresent) {
                // Internet Connection is not present
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Please connect to working Internet connection").setTitle("Internet Connection Error");
                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        Intent intent = new Intent(context, attractionsList.class);
                        startActivity(intent);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                // stop executing code by return
                return;
            }
            // creating GPS Class object
            gps = new GPSTracker(this);
            // check if GPS location can get
            if (gps.canGetLocation()) {
                Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Couldn't get location information. Please enable GPS").setTitle("GPS Status");
                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        Intent intent = new Intent(context, attractionsList.class);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                // stop executing code by return
                return;
            }
                AroundME myTask = new AroundME(); // Generating Around Me task
                Timer myTimer = new Timer(); // Timer
                myTimer.schedule(myTask, 50000, 50000); // 50000 means 5 minutes
        }

        // Alert Plan Function
        if(isAlertPlansOn){

            String CurrentPlan = ""; // Current Plan Date
            String[] SavedPlans = getApplicationContext().fileList(); // getting list of files names
            // looking for today's plan if exist

            // reading plan file
            String planDetails;
            int lineNum=1;
            slots = new ArrayList<slot>();
            Boolean found = false;
            try {
                    //Streams
                    for(int i = 0; i<SavedPlans.length; i++){
                        Log.d("Trace Plan","Inside loop");
                        Log.d("Trace Plan",SavedPlans[i]);
                            CurrentPlan = SavedPlans[i];
                            Log.d("Trace Plan",CurrentPlan);
                            FileInputStream fileInputStream = openFileInput(CurrentPlan);
                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            Log.d("Trace Plan", "File opened");
                            slot s = null; //initial
                            while ((planDetails = bufferedReader.readLine()) != null) {
                                Log.d("Trace Plan", "File inside while");
                                Log.d("Trace Plan", lineNum+"");
                                if (lineNum == 1) {
                                        NextPlan = CurrentPlan;
                                    Log.d("Trace Plan", "Inside 1");
                                    // first line
                                }
                                else if (lineNum == 2){
                                    Log.d("Trace Plan", "Inside 2");
                                    Log.d("Trace Plan", planDetails);
                                    Boolean n = checkDateNew(planDetails);
                                    Log.d("Trace Plan", n+"");
                                    if(checkDateNew(planDetails)){
                                        Log.d("Trace Plan", "Inside checked");
                                        found = true;
                                    }
                                    }
                                else {
                                    Log.d("Trace Plan", "Inside else");
                                    if (planDetails != "") { //other lines if exist are for slots
                                        if(found) {
                                            Log.d("Trace Plan", "Slots split");
                                            splits = planDetails.split(","); // to split event info
                                            s = new slot(splits[0], splits[1], splits[2]);
                                            slots.add(s);
                                            break;
                                        }
                                    }
                                }
                                lineNum++;
                            }
                        lineNum = 1;
                        }

                     //end while

                    planEventsTime = new ArrayList<String>();
                    planEventName = new ArrayList<String>();

                    // saving starting time of each event
                    if (slots != null) {
                        Log.d("Trace Plan", "Slots not null");
                        Log.d("Trace Plan", slots.size()+"");
                        Log.d("Trace Plan", slots.toString());
                        for (int i = 0; i < slots.size(); i++) {
                            planEventsTime.add(slots.get(i).getStartTime());
                            Log.d("Trace Plan", planEventsTime.get(i));
                            planEventName.add(slots.get(i).getaPlace());
                            Log.d("Trace Plan", planEventName.get(i));
                        }
                    }


                }catch(IOException e){
                    e.printStackTrace();
                }
            Log.d("Trace Plan", "File Done");
            // Timer initialization
            PlanAlert PlanTask = new PlanAlert(); // Generating Plan Alert task
            Timer myTimer = new Timer(); // Timer
            if(planEventsTime.size() > 0){
                    Log.d("Trace Plan", "fist time not null");
                    myTimer.schedule(PlanTask, 10000, 10000); // 10000 means 1 minutes


            }
        }
    }

    private Boolean checkDateNew (String d){
        Calendar t = Calendar.getInstance();
        if(!d.contains("!")){
            String withoutD = d.substring(d.indexOf("/")+1,d.length());
            String withoutM = withoutD.substring(withoutD.indexOf("/")+1,withoutD.length());
            int year = Integer.parseInt(withoutM);
            int month = Integer.parseInt(withoutD.substring(0,withoutD.indexOf("/")));
            int dayOfMonth = Integer.parseInt(d.substring(0, d.indexOf("/")));
            Log.d("PP", "" + year + "-" + month + "-" + dayOfMonth);
            // test your condition
            if (year == t.get(Calendar.YEAR)) {
                Log.d("Trace Plan", ""+t.get(Calendar.YEAR));
                Log.d("Trace Plan", ""+(t.get(Calendar.MONTH)+1)); // don't know why it's before by 1 month
                Log.d("Trace Plan", ""+t.get(Calendar.DAY_OF_MONTH));
                if(month == (t.get(Calendar.MONTH)+1)) // don't know why it's before by 1 month
                    if(dayOfMonth == t.get(Calendar.DAY_OF_MONTH))
                        return true;
            } else {
                return false;
            }
        }
            return false;
    }
    private Boolean checkDate (String d){
        Date date = null;
        try {
            Log.d("Trace Plan C","CheckDate");
            date = new SimpleDateFormat("MM/dd/yyyy").parse(d);
            Log.d("Trace Plan C",date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Trace Plan C", "catch");
        }
        return new Date().equals(date);
    }// end checkDate

    class AroundME extends TimerTask {
        public void run() {

            new LoadPlaces().execute(); // loading nearby places from google places
            if(near != null){
                if(!repeated)
                    generateNotificationForAroundMe(getApplicationContext(), near.name + " is around you");
            }
        }
    }

    class PlanAlert extends TimerTask {
        public void run() {
            Calendar c =Calendar.getInstance();
            if (planEventsTime.size() > 0 && planEventsTime != null) {
                if ((Integer.parseInt(planEventsTime.get(0).substring(0, planEventsTime.get(0).indexOf(":"))) - c.get(Calendar.HOUR_OF_DAY)) <= 1) {
                    NextEventName = planEventName.remove(0);
                    NextEventTime = planEventsTime.remove(0);
                    generateNotificationForPlan(getApplicationContext(), "You should be in " + NextEventName + " at " + NextEventTime);
                }
            }
        }
    }

    // to Generate the notification
    private void generateNotificationForPlan(Context context, String message) {
        int icon = R.drawable.logoc; // notification logo
        long when = System.currentTimeMillis();
        String appname = context.getResources().getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Notification notification;
        Intent i = new Intent(context, PlanDetails.class);
        i.putExtra("planName",NextPlan);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,i
                , 0);
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
    } // end generateNotificationForPlan

    private void generateNotificationForAroundMe(Context context, String message) {
        int icon = R.drawable.logoc; // notification logo
        long when = System.currentTimeMillis();
        String appname = context.getResources().getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Notification notification;
        Intent i = new Intent(context, attractionDescription.class);
        i.putExtra("ref",near.reference);
        i.putExtra("Type","Around You");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,i
                , 0);
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
    } // end generateNotificationForAroundMe

    public void GetSetting() {
        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        isGoogleGlassExist = SP.getBoolean("GoogleGlass", false);
        isAlertPlansOn = SP.getBoolean("PlanAlert", false);
        isAroundMeOn = SP.getBoolean("AroundMe", false);
    } // end GetSetting

    class LoadPlaces extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlaces();
            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                String types = "cafe|restaurant|amusement_park|aquarium|art_gallery|campground|city_hall|library|museum|park|rv_park|zoo"; //type of places to search for
                // Radius in meters
                double radius;
                radius = 1000; // 100 meters
                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(),
                        gps.getLongitude(), radius, types);
            } catch (Exception e) {
                e.printStackTrace();}
            return null;
        }// end doInBackground
        protected void onPostExecute(String file_url) {
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    String status = nearPlaces.status;
                    // Check for all possible status
                    if(status.equals("OK")){
                        // Successfully got places details
                        if (nearPlaces.results.size() > 0) {
                            if(near == null)
                                near = nearPlaces.results.get(0);
                            else if(! (near.name).equals(nearPlaces.results.get(0).name)) {
                                near = nearPlaces.results.get(0);
                                repeated = false;
                            }
                            else
                                repeated = true;
                        }
                        else
                            near = null;
                    }
                }
            });
        } // end onPostExecute
    }
}