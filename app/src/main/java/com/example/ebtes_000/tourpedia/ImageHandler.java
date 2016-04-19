package com.example.ebtes_000.tourpedia;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DELL on 12/03/16.
 */
public class ImageHandler {

    static File imageFile;

    public static final int MEDIA_TYPE_IMAGE = 1;

    public static File saveImage(int type) {
        imageFile =getOutputMediaFile(type);
       return imageFile;
    }

    public static void deleteImage(){

       //todo:delete the image from mobile storage.
        imageFile.delete();
    }

      /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "DCIM/Tourpedia");
        // This location works best if you want the created images to be shared
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("debug", "failed to create directory");
                return null;
            }
        }

        // Create a media file name

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "tourpedia_Identify_Image.jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

  }



