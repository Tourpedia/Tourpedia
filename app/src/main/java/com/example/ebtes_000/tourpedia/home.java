package com.example.ebtes_000.tourpedia;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //finish(); // Exit
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_home);
        GetSetting(); // Getting the Setting values from the Shared Preferences

        // activating Around Me function only if the user allow it
        if(isAroundMeOn){
            MyTimerTask myTask = new MyTimerTask(); // Generating Around Me task
            Timer myTimer = new Timer(); // Timer
            myTimer.schedule(myTask, 50000, 50000); // 50000 means 5 minutes
            }

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

                builder.setMessage(R.string.isThereGlass)
                        .setTitle(R.string.isThereGlassTitle);



                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        Intent intent=new Intent(context,GlassActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No button
                        Intent intent=new Intent(context,CameraActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        if(isAroundMeOn){
        // Check if Internet present
        isInternetPresent = ConnectionDetector.isConnectingToInternet(getApplicationContext());
        if (!isInternetPresent) {
            // Internet Connection is not present
            Log.d("Tracing places","inside internet else");
            alert.showAlertDialog(home.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // creating GPS Class object
        gps = new GPSTracker(this);

        Log.d("Tracing places","before gps");
        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {

            Log.d("Tracing places","inside gps else");
            // Can't get user's current location
            alert.showAlertDialog(home.this, "GPS Status",
                    "Couldn't get location information. Please enable GPS",
                    false);

            // stop executing code by return
            return;
        }
        }


        if(isAlertPlansOn){

            String planDetails;
            int lineNum=1;
            slots = new ArrayList<slot>();

            try {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.DATE);
                FileInputStream fileInputStream = openFileInput("");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer1 = new StringBuffer();
                slot s = null;
                    while ((planDetails = bufferedReader.readLine()) != null)
                    {
                        StringBuffer stringBuffer = new StringBuffer();
                        //stringBuffer.append(planDetails + "\n");
                        if (lineNum == 1){
                            EditText name = (EditText) findViewById(R.id.planName);
                            stringBuffer.append(planDetails);
                            name.setText(stringBuffer.toString());
                            oldName = stringBuffer.toString();
                        }

                        else if (lineNum == 2) {
                            EditText date = (EditText) findViewById(R.id.planDate);
                            stringBuffer.append(planDetails);
                            date.setText(stringBuffer.toString());
                            oldDate = stringBuffer.toString();
                        }
                        else{
                            if (planDetails != ""){
                                splits = planDetails.split(","); // to split event info
                                s = new slot(splits[0], splits[1], splits[2]);
                                slots.add(s);
                            }


                        }
                        lineNum++;
                    } //end while
                    ArrayList<String> planEventsTime = null;
                    if (slots != null){
                        for (int i = 0; i < slots.size(); i++){
                            planEventsTime.add(slots.get(i).getStartTime());
                        }
                    }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    class MyTimerTask extends TimerTask {
        public void run() {

            new LoadPlaces().execute(); // loading nearby places from google places
            if(near != null){
            generateNotification(getApplicationContext(), near.name + " is around you");
            }
        }
    }

    // to Generate the notification
    private void generateNotification(Context context, String message) {

        int icon = R.drawable.logoc; // notification logo
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

    public void GetSetting() {

        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        isGoogleGlassExist = SP.getBoolean("GoogleGlass", false);
        isAlertPlansOn = SP.getBoolean("PlanAlert", false);
        isAroundMeOn = SP.getBoolean("AroundMe", false);

    }

    class LoadPlaces extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        /**
         * getting Places JSON
         * */

        protected String doInBackground(String... args) {
            // creating Places class object

            Log.d("Tracing places","inside background");
            googlePlaces = new GooglePlaces();
            try {
                Log.d("Tracing places","inside back try");
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                String types = "cafe|restaurant|amusement_park|aquarium|art_gallery|campground|city_hall|library|museum|park|rv_park|zoo"; //type of places to search for
                // Radius in meters
                double radius;
                radius = 100; // 100 meters
                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(),
                        gps.getLongitude(), radius, types);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/


        protected void onPostExecute(String file_url) {
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    // Get json response status


                    String status = nearPlaces.status;
                    Log.d("what s",status);
                    // Check for all possible status
                    if(status.equals("OK")){
                        Log.d("Tracing places","status is OK");
                        // Successfully got places details
                        if (nearPlaces.results != null) {
                            near = nearPlaces.results.get(0);
                        }
                        else
                            near = null;
                    }

                }
            });

        }


    }
}
