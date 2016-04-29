package com.example.ebtes_000.tourpedia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

//        try{
//        rotateImage(imageFile.getAbsolutePath());}
//
//        catch (Exception e){
//
//            Log.e("debug","error in rotation");
//        }
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
//
//    public static void rotateImage(String file) throws IOException {
//
//        BitmapFactory.Options bounds = new BitmapFactory.Options();
//        bounds.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(file, bounds);
//
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        Bitmap bm = BitmapFactory.decodeFile(file, opts);
//
//        int rotationAngle =90;
//
//        Matrix matrix = new Matrix();
//        matrix.postRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
//        FileOutputStream fos=new FileOutputStream(file);
//        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        fos.flush();
//        fos.close();
//    }
//
//    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
//        int rotate = 0;
//        try {
//           // context.getContentResolver().notifyChange(imageUri, null);
//            File imageFile = new File(imagePath);
//            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
//            int orientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_UNDEFINED);
//
//                    rotate = 90;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rotate;
//    }


  }



