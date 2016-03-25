package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_settings);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(settings.this, home.class);
                startActivity(intent);


            }
        });
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(settings.this, settings.class);
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
                Intent intent = new Intent(settings.this, filter.class);
                startActivity(intent);


            }
        });
    }
}
