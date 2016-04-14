package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class guideMe extends AppCompatActivity {
    private static final float BYTES_PER_PX = 4.0f;
    ImageView guideMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // to hide the actionBar
        setContentView(R.layout.activity_guide_me);

        //restaurant button
        ImageButton restaurant = (ImageButton) findViewById(R.id.restaurantsBtn);
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "restaurant"); // to send the type when calling the class
                startActivity(intent);
            }
        });
        //attractions button
        ImageButton attractions = (ImageButton) findViewById(R.id.attractionsBtn);
        attractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "attractions"); // to send the type when calling the class
                startActivity(intent);
            }
        });
        //restroom button
        ImageButton restroom = (ImageButton) findViewById(R.id.restroomsBtn);
        restroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "restroom"); // to send the type when calling the class
                startActivity(intent);
            }
        });
        //Transportation button
        ImageButton Transportations = (ImageButton) findViewById(R.id.transportationsBtn);
        Transportations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "Transportation"); // to send the type when calling the class
                startActivity(intent);
            }
        });
        //Home button
        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guideMe.this, home.class);
                startActivity(intent);
            }
        });
        //setting and filters buttons
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guideMe.this, settings.class);
                startActivity(intent);
            }
        });
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guideMe.this, filter.class);
                startActivity(intent);
            }
        });


    }
}
