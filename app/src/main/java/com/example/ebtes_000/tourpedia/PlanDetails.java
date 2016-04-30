package com.example.ebtes_000.tourpedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlanDetails extends AppCompatActivity {
    String planName; // should be date
    ListView listOfEvents;
    ArrayList<slot> slots = null;
    String splits[] = null;
    LinearLayout allInfo;
    LinearLayout eventSec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);
        getSupportActionBar().hide(); //hide



        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        // to home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetails.this, home.class);
                startActivity(intent);
            }
        });

        // declaring settings and filters img buttons
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);

        // to settings
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetails.this, settings.class);
                startActivity(intent);
            }
        });

        // to filters
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetails.this, filter.class);
                startActivity(intent);
            }
        });

        allInfo = (LinearLayout) findViewById(R.id.name_date);
        eventSec = (LinearLayout) findViewById(R.id.event);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                planName= null;
            } else {
                planName= extras.getString("planName");
            }
        } else {
            planName= (String) savedInstanceState.getSerializable("planName");
        }

        displayPlanDetails(planName);


    }

    public void displayPlanDetails(String pName){
    try {
        String planDetails;
        String pname="";
        String pdate="";
        int lineNum=1;
        slots = new ArrayList<slot>();


        //  getFilesDir();
        FileInputStream fileInputStream = openFileInput(pName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Log.d("trace1", "after stream");
    //    StringBuffer stringBuffer = new StringBuffer();
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
            }

         /*   else if (lineNum == 2) {
                EditText date = (EditText) findViewById(R.id.planDate);
                stringBuffer.append(planDetails);
                if (planDetails != "")
                date.setText(stringBuffer.toString());
            }
            else{
                if (planDetails != ""){
                splits = planDetails.split(","); // to split event info
                s = new slot(splits[0], splits[1], splits[2]);
                slots.add(s);
            }}*/
            lineNum++;
        } //end while
        if (slots != null)
        showSavedEvents();

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void deletePlan (View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        File dir = getFilesDir();
                        File file = new File(dir, planName);
                        boolean deleted = file.delete();
                        if (deleted) {
                            Toast.makeText(getApplicationContext(), "Plan Deleted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PlanDetails.this, plans.class);
                            startActivity(intent);
Log.d("trace1", "where");
                        dialog.dismiss();
                    }}
                }
        );
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                }
            );
            AlertDialog alert = builder.create();
            alert.show();
        } // end deletePlan

    public void showSavedEvents(){
      //  slots = getApplicationContext().fileList();
        //eventsAdapter = new EventsAdapter(PlanDetails.this, R.layout.activity_events_list, slots);
        listOfEvents = (ListView)findViewById(R.id.list);
        ArrayAdapter<slot> adapter = new ArrayAdapter<slot>(PlanDetails.this, android.R.layout.simple_list_item_1, slots);
        Log.d("trace1", "inside method");
        listOfEvents.setAdapter(adapter);
        showPlanDetails();

    }
    public void showPlanDetails(){
        listOfEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       //         String planName = (String) listOfEvents.getItemAtPosition(position);
                LinearLayout allInfo = (LinearLayout) findViewById(R.id.name_date);
                allInfo.setVisibility(View.INVISIBLE);
                LinearLayout eventSec = (LinearLayout) findViewById(R.id.event);
                eventSec.setVisibility(View.VISIBLE);
                EditText place = (EditText) findViewById(R.id.placeTxt);
                EditText start = (EditText) findViewById(R.id.timeFrom);
                EditText end = (EditText) findViewById(R.id.timeTo);

                place.setText(slots.get(position).getaPlace());
                start.setText(slots.get(position).getStartTime());
                end.setText(slots.get(position).getEndTime());


            }
        });
    }

    }

