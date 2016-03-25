package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class plans extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_plans);

        ImageButton add = (ImageButton) findViewById(R.id.addPlanBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);


            }
        });
        ImageButton suggest = (ImageButton) findViewById(R.id.suggestPlanBtn);
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);


            }
        });
        ImageButton edit = (ImageButton) findViewById(R.id.editPlanBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);


            }
        });
        ImageButton delete = (ImageButton) findViewById(R.id.deletePlanBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(plans.this, addPlan.class);
                startActivity(intent);


            }
        });
        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(plans.this, home.class);
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
                Intent intent = new Intent(plans.this, settings.class);
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
                Intent intent = new Intent(plans.this, filter.class);
                startActivity(intent);


            }
        });
    }
}
