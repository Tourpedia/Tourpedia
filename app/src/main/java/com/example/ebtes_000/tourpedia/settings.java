package com.example.ebtes_000.tourpedia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class settings extends AppCompatActivity {

    Switch GG;
    Switch PA;
    Switch AM;
    Boolean GGG;
    Boolean PP;
    Boolean AA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_settings);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, home.class);
                startActivity(intent);
            }
        });

        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);

            GG = (Switch) findViewById(R.id.googleGlass);
            PA = (Switch) findViewById(R.id.planAlert);
            AM = (Switch) findViewById(R.id.AroundMe);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, settings.class);
                startActivity(intent);
            }
        });


        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, filter.class);
                startActivity(intent);
            }
        });

        show();
        GG.setChecked(GGG);
        PA.setChecked(PP);
        AM.setChecked(AA);
    }//onCreate end


    public void saveSettings(View view) {
        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();

        editor.putBoolean("GoogleGlass", GG.isChecked());
        editor.putBoolean("PlanAlert", PA.isChecked());
        editor.putBoolean("AroundMe", AM.isChecked());
        editor.apply();

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(settings.this, home.class);
        startActivity(intent);

    }// saveFile end


    // take this method to retrive the settings preferences any where
    public void show() {

        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        GGG = SP.getBoolean("GoogleGlass", false);
        PP = SP.getBoolean("PlanAlert", false);
        AA = SP.getBoolean("AroundMe", false);


    }
}
