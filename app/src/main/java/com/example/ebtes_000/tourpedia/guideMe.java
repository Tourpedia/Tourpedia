package com.example.ebtes_000.tourpedia;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
