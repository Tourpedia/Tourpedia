package com.example.ebtes_000.tourpedia.imgurmodel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
//import retrofit.mime.TypedFile;

/*
 * This code is reused and it is created by AKiniyalocts
 * This class responsible for connection with imgur API
 */

public interface ImgurAPI {
    String server = "https://api.imgur.com";


    // upload method
    @POST("/3/image")
    void postImage(
            @Header("Authorization") String auth,
            @Query("title") String title,
            @Query("description") String description,
            @Query("album") String albumId,
            @Query("account_url") String username,
            @Body TypedFile file,
            Callback<ImageResponse> cb
    );
}
