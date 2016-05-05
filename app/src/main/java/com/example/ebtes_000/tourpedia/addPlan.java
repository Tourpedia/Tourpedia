package com.example.ebtes_000.tourpedia;

import android.app.DialogFragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
                startActivity(intent);
            }
        });

        //to filters
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addPlan.this, filter.class);
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
       // showPlacesList();
     /*   final AutoCompleteTextView a = (AutoCompleteTextView) findViewById(R.id.placeText);

        ///
// creating GPS Class object
        GPSTracker gps = new GPSTracker(this);

        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
            alert.showAlertDialog(addPlan.this, "GPS Status",
                    "Couldn't get location information. Please enable GPS",
                    false);
        }
        String types = "cafe|restaurant";
        PlacesList nearPlaces = null;
        // Google Places
        GooglePlaces googlePlaces = new GooglePlaces();

        // Radius in meters - increase this value if you don't find any places
        double radius = 1000; // 1000 meters


        // get nearest places
        try {
            nearPlaces = googlePlaces.search(gps.getLatitude(),gps.getLongitude(), radius, types);
            Log.d("near","inside try");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //


        String[] places = null;
        if (nearPlaces != null)
            Log.d("status","inside");
        for (int i=0 ; i<nearPlaces.results.size() ; i++ ){
            places[i] = nearPlaces.results.get(i).name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,places);
        a.setAdapter(adapter);
        a.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                a.showDropDown();
                return false;
            }
        });*/
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
   /* public void showPlacesList(){
        AutoCompleteTextView a = (AutoCompleteTextView) findViewById(R.id.placeText);
        String[] countries = {"ac","cb","cv"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,countries);
        a.setAdapter(adapter);
        //a.showDropDown();

    }*/
}
