package com.example.ebtes_000.tourpedia;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
 
    //private Context _context;
 
   // public ConnectionDetector(Context context){
     //   this._context = context;
  //  }
 
    /**
     * Checking for all possible internet providers
     * **/
    public static boolean isConnectingToInternet(Context _context){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo info = connectivity.getActiveNetworkInfo();  //getAllNetworkInfo();


                          return info != null && info.isConnected();

 
          }
          return false;
    }
}
