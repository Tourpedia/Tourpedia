package com.example.ebtes_000.tourpedia;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class addPlan extends AppCompatActivity {
    String planName = "p";
    ArrayList<slot> slots = new ArrayList<slot>();
    ListPopupWindow lpw;
    String[] list;
    EditText place;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    public String[] places;
    // Places List
    PlacesList nearPlaces;
    // GPS Location
    GPSTracker gps;
    // Google Places object
    GooglePlaces googlePlaces;
    // Progress dialog
    ProgressDialog pDialog;

    // filters preferences
    String distancePref  ;
    int ratingPref;


    public Context context=this;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_add_plan);

        // to home
        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addPlan.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);

        //to settings
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addPlan.this, settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //to filters
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addPlan.this, filter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        final EditText planDateTxt = (EditText) findViewById(R.id.planDate);
        planDateTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    datePickerFragment.show(ft, "DatePicker");
                    planDateTxt.setInputType(InputType.TYPE_NULL);
                }
            }
        });

        final EditText startTxt = (EditText) findViewById(R.id.timeFrom);
        startTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerFragment timePickerFragment = new TimePickerFragment(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    timePickerFragment.show(ft, "TimePicker");
                    startTxt.setInputType(InputType.TYPE_NULL);
                }
            }
        });


        final EditText endTxt = (EditText) findViewById(R.id.timeTo);
        endTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerFragment timePickerFragment = new TimePickerFragment(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    timePickerFragment.show(ft, "TimePicker");
                    endTxt.setInputType(InputType.TYPE_NULL);
                }
            }
        });
        // Check if Internet present
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            // stop executing code by return
            return;
        }
        new LoadPlaces().execute();

       // showPlacesList();
// Getting places call
    }// end of onCreate


    public void addEvent (View view) {
        EditText pName = (EditText) findViewById(R.id.planName);
        EditText planDate = (EditText) findViewById(R.id.planDate);
        EditText placeName = (EditText) findViewById(R.id.placeTxt);
        EditText from = (EditText) findViewById(R.id.timeFrom);
        EditText to = (EditText) findViewById(R.id.timeTo);
        if (checkFields(pName, planDate, placeName, from, to)) {
            String place = placeName.getText().toString();
            String timeFrom = from.getText().toString();
            String timeTo = to.getText().toString();

            if(checkTime(timeFrom,timeTo)) {
                slot s = new slot(place, timeFrom, timeTo);
                slots.add(s);


                // clearing information
                placeName.getText().clear();
                placeName.setHint("Place to go");
                from.getText().clear();
                from.setHint("From");
                to.getText().clear();
                to.setHint("To");

                Toast.makeText(getApplicationContext(), "Event Added", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Please enter a correct period of time", Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();

    }


    private Boolean checkFields(EditText name, EditText date, EditText place, EditText start, EditText end) {
        if (name.getText().toString().length() == 0 || date.getText().toString().length() == 0 ||
                place.getText().toString().length() == 0 || start.getText().toString().length() == 0
                || end.getText().toString().length() == 0)
            return false;
        return true;
    }

    public void savePlan(View view){

        // plan information
        EditText pName = (EditText) findViewById(R.id.planName);
        EditText planDate = (EditText) findViewById(R.id.planDate);
        EditText placeName = (EditText) findViewById(R.id.placeTxt);
        EditText from = (EditText) findViewById(R.id.timeFrom);
        EditText to = (EditText) findViewById(R.id.timeTo);
        if (checkFields(pName, planDate, placeName, from, to)) {

            planName = pName.getText().toString();
            String date = planDate.getText().toString();
            if (checkDate(date)) {
                String place = placeName.getText().toString();
                String timeFrom = from.getText().toString();
                String timeTo = to.getText().toString();
                if (place != "" && timeFrom != "" && timeTo != "")
                    if (checkTime(timeFrom, timeTo)) {

               //     date += "  " + getDayOfWeek(date);
                        try {
                          //  OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(planName, MODE_PRIVATE));
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(getDayOfWeek(date), MODE_PRIVATE));
                            // writing name
                            outputStreamWriter.write(planName + "\n");
                            // writing date
                            outputStreamWriter.write(date + "\n");
                            for (int i = 0; i < slots.size(); i++) {
                                slot slot = slots.get(i);
                                outputStreamWriter.write(slot.getaPlace() + "," + slot.getStartTime() + "," + slot.getEndTime() + "\n");
                            }
                            if (place != "" && timeFrom != "" && timeTo != "")
                                outputStreamWriter.write(place + "," + timeFrom + "," + timeTo + "\n");

            /*outputStreamWriter.write(place+"\n");
            outputStreamWriter.write("From: "+timeFrom);
            outputStreamWriter.write(" - To: "+timeTo+"\n");*/

                            outputStreamWriter.close();
                            Toast.makeText(getApplicationContext(), "Plan saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(addPlan.this, plans.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Please enter a correct period of time", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(getApplicationContext(), "Please enter a correct date", Toast.LENGTH_LONG).show();
        }
            else
            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();

    }// end savePlan

    private Boolean checkTime (String from , String to){
        int start = Integer.parseInt(from.substring(0, from.indexOf(":")));
        int end = Integer.parseInt(to.substring(0, to.indexOf(":")));
        if (start < end)
            return true;
        else
        if (start == end){
            int s = Integer.parseInt(from.substring(from.indexOf(":")+1));
            int e = Integer.parseInt(to.substring(to.indexOf(":")+1));
            if (s < e)
                return true;
        }
        return false;

    }// end checkTime

    private Boolean checkDate (String d){
        //EditText date = (EditText) findViewById(R.id.planDate);
        Date date = null;
        try {
            date = (Date) new SimpleDateFormat("MM/dd/yyyy").parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date().before(date);
    }// end checkDate

    private String getDayOfWeek(String date){
        int s1,s2,s3;
        String yr;
        String[] out = date.split("/");
        s1 = Integer.parseInt(out[0]);
        s2 = Integer.parseInt(out[1]) - 1;
        yr = out[2];
        char a, b, c, d;
        a = yr.charAt(0);
        b = yr.charAt(1);
        c = yr.charAt(2);
        d = yr.charAt(3);
        s3 = Character.getNumericValue(a)*1000 +
                Character.getNumericValue(b)*100 +
                Character.getNumericValue(c)*10 +
                Character.getNumericValue(d);
        Calendar cal        = Calendar.getInstance();
        cal.set(s3, s2, s1);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch(day) {
            case 1: return "Sunday, "+out[0]+"-"+out[1]+"-"+out[2];
            case 2: return "Monday, "+out[0]+"-"+out[1]+"-"+out[2];
            case 3: return "Tuesday, "+out[0]+"-"+out[1]+"-"+out[2];
            case 4: return "Wednesday, "+out[0]+"-"+out[1]+"-"+out[2];
            case 5: return "Thursday, "+out[0]+"-"+out[1]+"-"+out[2];
            case 6: return "Friday, "+out[0]+"-"+out[1]+"-"+out[2];
            case 7: return "Saturday, "+out[0]+"-"+out[1]+"-"+out[2];
        }
return "";
    }
    public void showPlacesList(){
        AutoCompleteTextView a = (AutoCompleteTextView) findViewById(R.id.placeTxt);
        String[] countries = {"ac","cb","cv"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,countries);
        a.setAdapter(adapter);
        //a.showDropDown();

    }



   class LoadPlaces extends AsyncTask<String, String, String> {

       /**
        * Before starting background thread Show Progress Dialog
        * */


       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(addPlan.this);
           pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(false);
           pDialog.show();
       }
       /**
        * getting Places JSON
        * */

       protected String doInBackground(String... args) {
           // creating Places class object

           googlePlaces = new GooglePlaces();
           try {
               // Separeate your place types by PIPE symbol "|"
               // If you want all types places make it as null
               // Check list of types supported by google
               //
               String types = "cafe|restaurant|amusement_park|aquarium|art_gallery|campground|city_hall|library|museum|park|rv_park|zoo"; // default type
               // Radius in meters - increase this value if you don't find any places

               getFilters();// to get the filters Criteria
               double radius;
               if (distancePref != ""){
                   radius = Double.parseDouble(distancePref); // taking the radios from the filters if exist
               }
               else
                   radius = 1000; // 1000 meters
               // get nearest places
               nearPlaces = googlePlaces.search(gps.getLatitude(),
                       gps.getLongitude(), radius, types);
               places = new String[nearPlaces.results.size()];
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
           // dismiss the dialog after getting all products
           pDialog.dismiss();
           // updating UI from Background Thread
           runOnUiThread(new Runnable() {
               public void run() {
                   /**
                    * Updating parsed Places into LISTVIEW
                    * */
                   if(nearPlaces != null){
                   // Get json response status
                   String status = nearPlaces.status;

                   // Check for all possible status
                   if(status.equals("OK")){
                       // Successfully got places details
                       if (nearPlaces.results != null) {
                           final AutoCompleteTextView a = (AutoCompleteTextView) findViewById(R.id.placeTxt);
                               for (int i = 0; i < nearPlaces.results.size(); i++) {
                                   if(nearPlaces.results.get(i) != null){
                                       Place temp = nearPlaces.results.get(i);
                                       places[i] = temp.name;
                                   }
                               }

                               ArrayAdapter<String> adapter = new ArrayAdapter<String>(addPlan.this, android.R.layout.select_dialog_singlechoice, places);
                               a.setAdapter(adapter);
                               a.setOnTouchListener(new View.OnTouchListener() {

                                   @Override
                                   public boolean onTouch(View v, MotionEvent event) {
                                       a.showDropDown();
                                       return false;
                                   }
                               });
                       }
                   }
                   }
               }
           });
       }
   }
    public void getFilters(){

        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        distancePref = SP.getString("Distance", "");
        ratingPref = SP.getInt("rating", 0);


    }
}


