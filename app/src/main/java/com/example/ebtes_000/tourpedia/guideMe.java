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
      /*  Bitmap bitmap = (Bitmap)this.getIntent().getParcelableExtra("Bitmap");
        getSupportActionBar().hide(); // to hide the actionBar
        setContentView(R.layout.activity_guide_me);

        ImageView guideme = (ImageView) findViewById(R.id.guidemeFuncs);
        guideme.setImageBitmap(bitmap);*/ //causes faild binder

        getSupportActionBar().hide(); // to hide the actionBar
        setContentView(R.layout.activity_guide_me);
      //  guideMe = (ImageView) findViewById(R.id.guidemeFuncs);

       // loadImage();
        ImageButton restaurant = (ImageButton) findViewById(R.id.restaurantsBtn);
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "restaurant");
                startActivity(intent);


            }
        });
        ImageButton attractions = (ImageButton) findViewById(R.id.attractionsBtn);
        attractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "attractions");
                startActivity(intent);


            }
        });
        ImageButton restroom = (ImageButton) findViewById(R.id.restroomsBtn);
        restroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "restroom");
                startActivity(intent);


            }
        });
        ImageButton Transportations = (ImageButton) findViewById(R.id.transportationsBtn);
        Transportations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.guideme1 );
                Intent intent = new Intent();
                intent.setClass(home.this, guideMe.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);*/ // causes faild binder
                Intent intent = new Intent(guideMe.this, attractionsList.class);
                intent.putExtra("attractionsList", "Transportations");
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
                Intent intent = new Intent(guideMe.this, home.class);
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
                Intent intent = new Intent(guideMe.this, settings.class);
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
                Intent intent = new Intent(guideMe.this, filter.class);
                startActivity(intent);


            }
        });


    }
  /*  private void loadImage(){
        if (readBitmapInfo() > memUtils.megabytesFree())
            subSampleImage(32);
        else
            guideMe.setImageResource(R.drawable.guideme1);
    }

    private float readBitmapInfo(){
        final Resources res = this.getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable)
    }*/
}
