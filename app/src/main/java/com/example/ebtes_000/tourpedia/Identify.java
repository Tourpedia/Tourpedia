package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Identify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_identify);
    }


   public void useMobileCamera(View view){

       Intent intent=new Intent(this,CameraActivity.class);
       startActivity(intent);
   }

  public void useGlassCamera(View view){

      Intent intent=new Intent(this,GlassActivity.class);
      startActivity(intent);


  }

}
