package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {


    public File pictureFile;
    public static final int MEDIA_TYPE_IMAGE = 1;


    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create Preview view and set it as the content of the frame.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);


    }


    //Capture picture
    public void captureImage(View v) {
        // get an image from the camera
        mCamera.takePicture(null, null, mPicture);
    }

    public void uploadImage(){//Call upload activity in order to
        Intent intent = new Intent(this, imgDescription.class);
        startActivity(intent);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            pictureFile = ImageHandler.saveImage(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d("debug", "Error creating media file, check storage permissions! ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                uploadImage();
            } catch (FileNotFoundException e) {
                Log.d("debug", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("debug", "Error accessing file: " + e.getMessage());
            }
        }
    };

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.d("debug","getCameraInstance(): Camera is not available (in use or does not exist)");

        }
        return c; // returns null if camera is unavailable
    }

}
