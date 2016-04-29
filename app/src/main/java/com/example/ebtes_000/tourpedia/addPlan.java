package com.example.ebtes_000.tourpedia;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class addPlan extends AppCompatActivity {
    String planName = "p";

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


    }

    // to show date picker from the edit text
    public void showDatePickerDialog(View v) { // some examples use (onStart then OnfoucseChange)
        EditText planDateTxt = (EditText) findViewById(R.id.planDate);
        DatePickerFragment datePickerFragment = new DatePickerFragment(v);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        datePickerFragment.show(ft, "DatePicker");
        //   if (datePickerFragment.isDateSet == true)
        //   showEventInfo();


        // DialogFragment newFragment = new DatePickerFragment();
        //newFragment.show(getSupportFragmentManager(), "datePicker");
    /*   final Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               showEventInfo();
           }
       }, 600);*/

    }

    public void showTimePickerDialog(View v) { // some examples use (onStart then OnfoucseChange)
        EditText planDateTxt = (EditText) findViewById(R.id.timeFrom);
        TimePickerFragment timePickerFragment = new TimePickerFragment(v);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        timePickerFragment.show(ft, "DatePicker");

        // DialogFragment newFragment = new DatePickerFragment();
        //newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEventInfo() {

        //     EditText name = (EditText) findViewById(R.id.planName);
        //   EditText date = (EditText) findViewById(R.id.planDate);
        // if (name.getText().equals("") == false){
        //   if (date.getText().equals("") == false){
        Button saveBtn = (Button) findViewById(R.id.savePlanBtn);
        LinearLayout event = (LinearLayout) findViewById(R.id.event);
        LinearLayout name_date = (LinearLayout) findViewById(R.id.name_date);
        name_date.setVisibility(View.INVISIBLE);
        event.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.VISIBLE);
//                }}

    }

    public void addEvent(View v) {
        // stupid way to avoid redundancy when adding multiple events
        if (planName.equals("p")) {
            // plan information
            EditText pName = (EditText) findViewById(R.id.planName);
            EditText planDate = (EditText) findViewById(R.id.planDate);
            planName = pName.getText().toString();
            String date = planDate.getText().toString();
            // event information
            EditText placeName = (EditText) findViewById(R.id.placeTxt);
            EditText from = (EditText) findViewById(R.id.timeFrom);
            EditText to = (EditText) findViewById(R.id.timeTo);
            String place = placeName.getText().toString();
            String timeFrom = from.getText().toString();
            String timeTo = to.getText().toString();


            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(planName, MODE_PRIVATE));
                //   FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
                // writing name
                outputStreamWriter.write(planName + "\n");
                // writing date
                outputStreamWriter.write(date + "\n");

                outputStreamWriter.write("Slot: \n" + place + "\n");
                outputStreamWriter.write("From: " + timeFrom);
                outputStreamWriter.write("To: " + timeTo + "\n");

                outputStreamWriter.close();
                Toast.makeText(getApplicationContext(), "Event Added", Toast.LENGTH_LONG).show();
                // clearing information
                placeName.getText().clear();
                placeName.setHint("Place to go");
                from.getText().clear();
                from.setHint("From");
                to.getText().clear();
                to.setHint("To");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            EditText placeName = (EditText) findViewById(R.id.placeTxt);
            EditText from = (EditText) findViewById(R.id.timeFrom);
            EditText to = (EditText) findViewById(R.id.timeTo);
            String place = placeName.getText().toString();
            String timeFrom = from.getText().toString();
            String timeTo = to.getText().toString();
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(planName, MODE_APPEND));
                //   FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);

                outputStreamWriter.write(place + "," + timeFrom + "," + timeTo + "\n");
                //outputStreamWriter.write("From: " + timeFrom);
                //  outputStreamWriter.write(" - To: " + timeTo + "\n");

                outputStreamWriter.close();
                Toast.makeText(getApplicationContext(), "Event Added", Toast.LENGTH_LONG).show();
                // clearing information
                placeName.getText().clear();
                placeName.setHint("Place to go");
                from.getText().clear();
                from.setHint("From");
                to.getText().clear();
                to.setHint("To");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }// end else
    }

    public void savePlan(View view) {
        // plan information
        EditText pName = (EditText) findViewById(R.id.planName);
        EditText planDate = (EditText) findViewById(R.id.planDate);
        planName = pName.getText().toString();
        String date = planDate.getText().toString();
        // event information
        EditText placeName = (EditText) findViewById(R.id.placeTxt);
        EditText from = (EditText) findViewById(R.id.timeFrom);
        EditText to = (EditText) findViewById(R.id.timeTo);
        String place = placeName.getText().toString();
        String timeFrom = from.getText().toString();
        String timeTo = to.getText().toString();

        // specifying file name
        //String fileName = name;

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(planName, MODE_PRIVATE));
            //   FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            // writing name
            outputStreamWriter.write(planName + "\n");
            // writing date
            outputStreamWriter.write(date + "\n");

            outputStreamWriter.write(place + "\n");
            outputStreamWriter.write("From: " + timeFrom);
            outputStreamWriter.write(" - To: " + timeTo + "\n");

            outputStreamWriter.close();
            Toast.makeText(getApplicationContext(), "Plan saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
///////////////////////////////////
        try {
            String Readdate;
            //  getFilesDir();
            FileInputStream fileInputStream = openFileInput(planName);
            InputStreamReader inputSreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputSreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((Readdate = bufferedReader.readLine()) != null) {
                stringBuffer.append(Readdate + "\n");
            }

            TextView textView = (TextView) findViewById(R.id.date);
            textView.setText(stringBuffer.toString() + getFilesDir());
            textView.setContentDescription(stringBuffer.toString() + getFilesDir());
            Log.d("File1"," "+getFilesDir());
            textView.setVisibility(View.VISIBLE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

