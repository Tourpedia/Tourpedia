package com.example.ebtes_000.tourpedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlanDetails extends AppCompatActivity {
String planName; // should be date
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);
        getSupportActionBar().hide();
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

        //  getFilesDir();
        FileInputStream fileInputStream = openFileInput(pName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Log.d("trace1", "after stream");
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        ArrayList<slot> slots = new ArrayList<slot>();
        String splits[] = null;
        slot s = null;
        while ((planDetails = bufferedReader.readLine()) != null)
        {
            Log.d("trace1",lineNum+"");
            //stringBuffer.append(planDetails + "\n");
            if (lineNum == 1){
                Log.d("trace1", "inside if 1");
                pname = planDetails;
                EditText name = (EditText) findViewById(R.id.planName);
                Log.d("trace1", "heeeere");
                stringBuffer.append(pname + "\n");
                name.setText(stringBuffer.toString());
                name.setContentDescription(stringBuffer.toString());

            }
            else if (lineNum == 2)
                pdate = planDetails;
          /*  else{
            splits = planDetails.split(","); // to split event info
            s = new slot(splits[0], splits[1], splits[2]);
            slots.add(s);
            }*/
Log.d("trace1","after first line");
            lineNum++;
            Log.d("trace1",lineNum+"");
        }

       // EditText name = (EditText) findViewById(R.id.planName);
        Log.d("trace1","after name");
        EditText date = (EditText) findViewById(R.id.planDate);
        Log.d("trace1", "after date");

      //  name.setText(pname);
        //stringBuffer = null;
       // if (pdate != null)
        //date.setText(stringBuffer1.append(pdate).toString());
        //TextView textView = (TextView) findViewById(R.id.date);
        //textView.setText(stringBuffer.toString());
        //textView.setVisibility(View.VISIBLE);

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }}
}
