package com.example.ebtes_000.tourpedia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.File;

public class imgDescription extends AppCompatActivity {


    public Context context=this;
    public static ViewFlipper viewFlipper;

    File image;
    UploadStart uploadStart ;
    public static TextView message;
    public static TextView  description;

   // public static ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_img_description);


        viewFlipper =(ViewFlipper)findViewById(R.id.viewFlipper);

        message=(TextView) findViewById(R.id.uploadingMessage);
        description=(TextView) findViewById(R.id.description);

        description.setMovementMethod(LinkMovementMethod.getInstance());

        uploadStart = new UploadStart(context);
        uploadStart.uploadImage();

        image = ImageHandler.imageFile;//TODO: find a better way to pass the file since this looks unreliable.


        Matrix matrix = new Matrix();
        Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        matrix.postRotate(90);
        Bitmap bMapRotate = Bitmap.createBitmap( myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
        ImageView myImage = (ImageView) findViewById(R.id.capturedImg);


        if(!VariablesAndConstants.isFromGlass){
        myImage.setImageBitmap(bMapRotate);}
        else myImage.setImageBitmap(myBitmap);
        myImage.setContentDescription("Image");



        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(imgDescription.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(imgDescription.this, settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(imgDescription.this, filter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });



    }


    public void Restart()
    {
        this.recreate();
    }
}
