package com.example.ebtes_000.tourpedia.utils;

import android.util.Log;

import com.example.ebtes_000.tourpedia.Constants;

//import akiniyalocts.imgurapiexample.Constants;

/**
 *  this code is reused and it is created by AKiniyalocts
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
