package com.example.ebtes_000.tourpedia.utils;

import android.util.Log;

import com.example.ebtes_000.tourpedia.Constants;

//import akiniyalocts.imgurapiexample.Constants;

/**
 * Created by AKiniyalocts on 1/16/15.
 *
 * Basic logger bound to a flag in Constants.java
 */
public class aLog {
  public static void w (String TAG, String msg){
    if(Constants.LOGGING) {
      if (TAG != null && msg != null)
        Log.w(TAG, msg);
    }
  }

}
