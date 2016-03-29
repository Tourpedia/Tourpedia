package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_home);
        ImageButton guideMe = (ImageButton) findViewById(R.id.guideBtn);
        ImageButton Plan = (ImageButton) findViewById(R.id.planBtn);
        ImageButton idntify = (ImageButton) findViewById(R.id.identifyBtn);
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        guideMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(home.this, guideMe.class);
                startActivity(intent);


            }
        });
        Plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(home.this, plans.class);
                startActivity(intent);


            }
        });
        idntify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(home.this, Identify.class);
                startActivity(intent);


            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(home.this, settings.class);
                startActivity(intent);


            }
        });
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(home.this, filter.class);
                startActivity(intent);


            }
        });
    }
}
