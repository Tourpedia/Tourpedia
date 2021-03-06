package com.example.ebtes_000.tourpedia.services;

import android.content.Context;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import com.example.ebtes_000.tourpedia.Constants;
//import com.example.ebtes_000.tourpedia.helpers.NotificationHelper;
import com.example.ebtes_000.tourpedia.imgDescription;
import com.example.ebtes_000.tourpedia.imgurmodel.ImageResponse;
import com.example.ebtes_000.tourpedia.imgurmodel.ImgurAPI;
import com.example.ebtes_000.tourpedia.imgurmodel.Upload;
import com.example.ebtes_000.tourpedia.ConnectionDetector;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


/**
 * this code is reused and it is created by AKiniyalocts
 * upload service. This creates restadapter, uploads the image, and notifies us of the response.
 */
public class UploadService {
    public final static String TAG = UploadService.class.getSimpleName();

    private WeakReference<Context> mContext;
    TextView message = imgDescription.message;

    public UploadService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void Execute(Upload upload, Callback<ImageResponse> callback) {
        final Callback<ImageResponse> cb = callback;

        if (!ConnectionDetector.isConnectingToInternet(mContext.get())) {
            //Callback will be called, so we prevent a unnecessary notification
            cb.failure(null);
            return;
        }



      //  final NotificationHelper notificationHelper = new NotificationHelper(mContext.get());
      //  notificationHelper.createUploadingNotification();

        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurAPI.class).postImage(
                Constants.getClientAuth(),
                upload.title,
                upload.description,
                upload.albumId,
                null,
                new TypedFile("image/*", upload.image),
                new Callback<ImageResponse>() {
                    @Override
                    public void success(ImageResponse imageResponse, Response response) {
                        if (cb != null) cb.success(imageResponse, response);
                        if (response == null) {
                            /*
                            Todo:Notify image was NOT uploaded successfully
                            */

                            message.setText("failed to upload image");
                            message.setContentDescription("failed to upload image");
                            //notificationHelper.createFailedUploadNotification();


                            return;
                        }
                        /*
                        Notify image was uploaded successfully
                        */
                        if (imageResponse.success) {
                            //notificationHelper.createUploadedNotification(imageResponse);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null) cb.failure(error);

                        message.setText("failed to upload image");
                        message.setContentDescription("failed to upload image");
                     //  notificationHelper.createFailedUploadNotification();


                    }
                });
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurAPI.server)
                .build();

        /*
        Set rest adapter logging if we're already logging
        */
        if (Constants.LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }
}
