package com.example.ebtes_000.tourpedia.helpers;

import android.app.Activity;
import android.content.Intent;

/**
 * this code is reused and it is created by AKiniyalocts
 */
public class IntentHelper {
  public final static int FILE_PICK = 1001;


  public static void chooseFileIntent(Activity activity){
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    activity.startActivityForResult(intent, FILE_PICK);
  }
}
