package com.example.ebtes_000.tourpedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class attractionDescription extends AppCompatActivity {

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Google Places
    GooglePlaces googlePlaces;

    // Place Details
    PlaceDetails placeDetails;

    // Progress dialog
    ProgressDialog pDialog;
    // ListItems data
    ArrayList<HashMap<String, String>> ListItems = new ArrayList<HashMap<String,String>>();

    String Guideingtype;

    String longitude;
    String latitude;
    public Context context=this;
    String ref;

    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_TEXT = "text"; // review of the place
    public static String KEY_NAME = "name"; // name of the author
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_attraction_description);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(attractionDescription.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(attractionDescription.this, settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(attractionDescription.this, filter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        Button guide = (Button) findViewById(R.id.guideMeBtn);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("guide","Clicking???");

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);//TODO:put the locatoin here
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                //  if (mapIntent.resolveActivity(getPackageManager()) != null)
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mapIntent);

            }});
        Intent i = getIntent();

        // Place referece id


        //place Type
        Bundle extras;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                Guideingtype= "";
                ref = "";
            } else {
                Guideingtype= extras.getString("Type");
                if(extras.getString("ref") == null)
                    ref = extras.getString(KEY_REFERENCE);
                else
                    ref = extras.getString("ref");
            }
        } else {
            Guideingtype= (String) savedInstanceState.getSerializable("Type");
            ref = (String) savedInstanceState.getSerializable("ref");
        }
        TextView lbl_name = (TextView) findViewById(R.id.textView);
        lbl_name.setText(Guideingtype);
        lbl_name.setContentDescription(Guideingtype);


        // Calling a Async Background thread
        new LoadattractionDescription().execute(ref);
    }


    /**
     * Background Async Task to Load Google places
     * */
    class LoadattractionDescription extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(attractionDescription.this);
            pDialog.setMessage("Loading profile ...");
            //pDialog.setContentDiscription();
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Profile JSON
         * */
        protected String doInBackground(String... args) {
            String reference = args[0];
            Log.d("ref2",reference);
            // creating Places class object
            googlePlaces = new GooglePlaces();
            if(googlePlaces != null)
            Log.d("gp1",googlePlaces.toString());

            // Check if used is connected to Internet
            try {
                placeDetails = googlePlaces.getPlaceDetails(reference);

                Log.d("ditp",placeDetails.result.toString());


            } catch (Exception e) {
                e.printStackTrace();
                Log.d("catch","catch");
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    if(placeDetails != null){
                        String status = placeDetails.status;
                        Log.d("status1",status);
                        // check place deatils status
                        // Check for all possible status
                        if(status.equals("OK")){
                            if (placeDetails.result != null) {
                                double rate = placeDetails.result.rating;
                                String icon = placeDetails.result.icon;
                                String name = placeDetails.result.name;
                                String address = placeDetails.result.formatted_address;
                                String phone = placeDetails.result.formatted_phone_number;
                                latitude = Double.toString(placeDetails.result.geometry.location.lat);
                                longitude = Double.toString(placeDetails.result.geometry.location.lng);

                                // Displaying all the details in the view
                                // single_place.xml
                                RatingBar ratingBar = (RatingBar) findViewById(R.id.rate);
                                ImageView lbl_icon = (ImageView) findViewById(R.id.attractionImg);
                                TextView lbl_name = (TextView) findViewById(R.id.name);
                                TextView lbl_address = (TextView) findViewById(R.id.address);
                                TextView lbl_phone = (TextView) findViewById(R.id.phone);


                                // Check for null data from google
                                // Sometimes place details might missing

                                icon = icon == null ? null : icon;
                                name = name == null ? "Not present" : name; // if name is null display as "Not present"
                                address = address == null ? "Not present" : address;
                                phone = phone == null ? "Not present" : phone;

                                // show The Image in a ImageView
                                ratingBar.setRating((float) rate);
                                new DownloadImageTask((ImageView) findViewById(R.id.attractionImg))
                                        .execute(icon);
                                lbl_icon.setContentDescription("Icon");

                                // show the other description elements
                                lbl_name.setText(name);
                                lbl_name.setContentDescription(name);
                                lbl_address.setText(address);
                                lbl_address.setContentDescription(address);
                                lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
                                lbl_phone.setContentDescription(Html.fromHtml("<b>Phone:</b> " + phone));

                                // displaying the reviews list
                                if (placeDetails.result.reviews != null)
                                for (Place.Reviews r : placeDetails.result.reviews) {
                                    HashMap<String, String> map = new HashMap<String, String>();

                                    // reviewer name
                                    map.put(KEY_NAME, r.author_name);

                                    // review content
                                    map.put(KEY_TEXT, r.text);


                                    // adding HashMap to ArrayList
                                    ListItems.add(map);
                                }
                                else {
                                    HashMap<String, String> map = new HashMap<String, String>();

                                    // reviewer name
                                    map.put(KEY_NAME, "");

                                    // review content
                                    map.put(KEY_TEXT, "no reviews");
                                    ListItems.add(map);

                                }


                                //list adapter

                                ListAdapter adapter = new SimpleAdapter(attractionDescription.this, ListItems,
                                        R.layout.rev_list,
                                        new String[] { KEY_NAME, KEY_TEXT}, new int[] {
                                        R.id.name, R.id.text });
                                ListView reviews = (ListView) findViewById(R.id.Revlist);
                                // Adding data into listview
                                reviews.setAdapter(adapter);
                                reviews.setContentDescription(adapter.toString());
                                //end reviews list

                            }
                            Log.d("placeDetails.results", "NO");
                        }
                        else if (status.equals("ZERO_RESULTS")) {
                            // Zero results found
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Sorry no places found. Try to choose another places type").setTitle("Near Places");
                            // Add the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked Yes button
                                    Intent intent = new Intent(context, attractionDescription.class);
                                    startActivity(intent);
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else if (status.equals("UNKNOWN_ERROR")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Sorry unknown error occurred.").setTitle("Places Error");
                            // Add the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked Yes button
                                    Intent intent = new Intent(context, attractionDescription.class);
                                    startActivity(intent);
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else if (status.equals("OVER_QUERY_LIMIT")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Sorry query limit to google places is reached").setTitle("Places Error");
                            // Add the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked Yes button
                                    Intent intent = new Intent(context, attractionDescription.class);
                                    startActivity(intent);
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else if (status.equals("REQUEST_DENIED")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Sorry error occurred. Request is denied").setTitle("Places Error");
                            // Add the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked Yes button
                                    Intent intent = new Intent(context, attractionDescription.class);
                                    startActivity(intent);
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else if (status.equals("INVALID_REQUEST")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Sorry error occurred. Invalid Request").setTitle("Places Error");
                            // Add the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked Yes button
                                    Intent intent = new Intent(context, attractionDescription.class);
                                    startActivity(intent);
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Sorry error occurred.").setTitle("Places Error");
                            // Add the buttons
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked Yes button
                                    Intent intent = new Intent(context, attractionDescription.class);
                                    startActivity(intent);
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sorry error occurred. Check your internet Connection and try again").setTitle("Error");
                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                Intent intent = new Intent(context, attractionDescription.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();}
                }
            });
        }

}
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
