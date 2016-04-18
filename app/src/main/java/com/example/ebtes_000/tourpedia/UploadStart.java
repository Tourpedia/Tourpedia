package com.example.ebtes_000.tourpedia;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import com.example.ebtes_000.tourpedia.CloudSight.ImageIdentifying;
import com.example.ebtes_000.tourpedia.imgurmodel.ImageResponse;
import com.example.ebtes_000.tourpedia.imgurmodel.Upload;
import com.example.ebtes_000.tourpedia.services.UploadService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by DELL on 29/03/16.
 */
public class UploadStart {

    Context mContext;
    File image;
    Upload upload; // Upload object containing image and meta data



    public UploadStart(Context context){

       mContext=context;
       image = ImageHandler.imageFile;//TODO: find a better way to pass the file since this looks unreliable.
    }

    public void uploadImage() {
    /*
      Create the @Upload object
     */
        if (image == null){
            Log.d("debug", "The image is empty");
            return;}
        createUpload(image);

    /*
      Start upload
     */
        new UploadService(mContext).Execute(upload, new UiCallback());
    }

    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;

    }

    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {

            new ImageIdentifying().execute(imageResponse.data.link);//Start identifying the image

        }



        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                // Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(null, "No internet connection", duration);
                toast.show();
            }else {

                if(VariablesAndConstants.isFromGlass) {
                    GlassActivity.sendToGlass("Failed");
                    //=false?
                }
            }
        }
    }




}
