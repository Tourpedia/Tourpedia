package com.example.ebtes_000.tourpedia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
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

public class filter extends AppCompatActivity {
    private int distanceAtt , ratingAtt;
    EditText dis;
    RatingBar rate;
    String dist;
    int rat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_filter);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(filter.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(filter.this, settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(filter.this, filter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dis = (EditText) findViewById(R.id.distance);
        rate = (RatingBar) findViewById(R.id.ratingBar);

        show();
        dis.setText(dist);
        rate.setRating(rat);
    }

    public void saveFilters(View view) {
        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();

        editor.putString("Distance", dis.getText().toString());
        editor.putInt("rating", (int) rate.getRating());
        editor.apply();

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(filter.this, home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }// saveFile end


    // take this method to retrive the filters preferences any where
    public void show(){

        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        dist = SP.getString("Distance", "");
        rat = SP.getInt("rating", 0);
    }
}
