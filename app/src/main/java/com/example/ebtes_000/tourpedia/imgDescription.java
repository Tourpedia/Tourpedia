package com.example.ebtes_000.tourpedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class imgDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_img_description);
    }
}
