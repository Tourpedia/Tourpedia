package com.example.ebtes_000.tourpedia;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class plans extends AppCompatActivity { //implements PlanInterface {

    ListView listSavedPlans;
    String[] SavedPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_plans);


        // declare add plan img button
        ImageButton add = (ImageButton) findViewById(R.id.addPlanBtn);
        // to add plan
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);
            }
        });
        // declare suggest plan img button
   /*     ImageButton suggest = (ImageButton) findViewById(R.id.suggestPlanBtn);
        // to suggest plan
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);
            }
        });*/

    /*    ImageButton edit = (ImageButton) findViewById(R.id.editPlanBtn);
        // to edit plan
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);
            }
        });

        ImageButton delete = (ImageButton) findViewById(R.id.deletePlanBtn);
        // to delete plan
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);
            }
        });
*/
        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        // to home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plans.this, home.class);
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
                Intent intent = new Intent(plans.this, settings.class);
                startActivity(intent);
            }
        });

        // to filters
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plans.this, filter.class);
                startActivity(intent);
            }
        });

        listSavedPlans = (ListView)findViewById(R.id.list);
        ShowSavedFiles();

        listSavedPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String planName = (String) listSavedPlans.getItemAtPosition(position);
                Intent intent = new Intent(plans.this, PlanDetails.class);
                intent.putExtra("planName", planName);
                startActivity(intent);


            }
        });


    }
    public void ShowSavedFiles(){
        SavedPlans = getApplicationContext().fileList();
        if (SavedPlans.length == 0){
            TextView noplan = (TextView) findViewById(R.id.noPlan);
            noplan.setVisibility(View.VISIBLE);
        }
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                SavedPlans);

        listSavedPlans.setAdapter(adapter);

    }
}
