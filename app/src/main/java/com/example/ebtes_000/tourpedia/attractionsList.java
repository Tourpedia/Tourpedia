package com.example.ebtes_000.tourpedia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class attractionsList extends AppCompatActivity {
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Google Places
    GooglePlaces googlePlaces;

    // Places List
    PlacesList nearPlaces;

    // GPS Location
    GPSTracker gps;

    // Button
    Button btnShowOnMap;

    // Progress dialog
    ProgressDialog pDialog;

    // Places Listview
    ListView lv;

    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();

    //type of button chosen , attraction, restroom ...
    String Guideingtype;

    // filters preferences
    String distancePref  ;
    int ratingPref;
    public Context context=this;

    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_attractions_list);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(attractionsList.this, home.class);
                startActivity(intent);
            }
        });
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(attractionsList.this, settings.class);
                startActivity(intent);
            }
        });
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(attractionsList.this, filter.class);
                startActivity(intent);

            }
        });

        getFilters();// to get the filters Criteria

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Guideingtype= "";
            } else {
                Guideingtype= extras.getString("attractionsList");
            }
        } else {
            Guideingtype= (String) savedInstanceState.getSerializable("attractionsList");
        }
        TextView lbl_name = (TextView) findViewById(R.id.textView);
        lbl_name.setText(Guideingtype + " List");
        lbl_name.setContentDescription(Guideingtype + " List");
        

        //
        //
        //the function code
        //
        //


        // Check if Internet present
        isInternetPresent = ConnectionDetector.isConnectingToInternet(getApplicationContext());
        if (!isInternetPresent) {
            // Internet Connection is not present
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Please connect to working Internet connection").setTitle("Internet Connection Error");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked Yes button
                    Intent intent = new Intent(context, attractionsList.class);
                    startActivity(intent);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            // stop executing code by return
            return;
        }

        // creating GPS Class object
        gps = new GPSTracker(this);

        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Couldn't get location information. Please enable GPS").setTitle("GPS Status");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked Yes button
                    Intent intent = new Intent(context, attractionsList.class);
                    startActivity(intent);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            // stop executing code by return
            return;
        }

        // Getting listview
        lv = (ListView) findViewById(R.id.list);

        // calling background Async task to load Google Places
        // After getting places from Google all the data is shown in listview
        new LoadPlaces().execute();

        /**
         * ListItem click event
         * On selecting a listitem attractionDescription is launched
         * */
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        attractionDescription.class);

                // Sending place refrence id to single place activity
                // place refrence id used to get "Place full details"
                in.putExtra(KEY_REFERENCE, reference);
                in.putExtra("Type", Guideingtype);
                startActivity(in);
            }
       });
    }
    /**
     * Background Async Task to Load Google places
     * */
    class LoadPlaces extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(attractionsList.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlaces();

            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //



                String types = "cafe|restaurant"; // default type
                //switch cases for the chosen place to guide to
                if(Guideingtype != null){
                    Log.d("inside",Guideingtype);
                    switch (Guideingtype) {
                        case "restaurant":
                            types = "cafe|restaurant"; // Listing places only cafes, restaurants
                            break;
                        case "attractions":
                            types = "amusement_park|aquarium|art_gallery|campground|city_hall|library|museum|park|rv_park|zoo"; // Listing attractions
                            break;
                        case "restroom":
                            types = "restroom|bathroom"; // Listing restrooms
                            break;
                        case "Transportation":
                            types = "bus_station|car_rental|subway_station|taxi_stand|train_station|transit_station"; // Listing transportation places
                            break;
                        default:
                            types = "cafe|restaurant";
                    }
                }

                // Radius in meters - increase this value if you don't find any places
                double radius;
                if (distancePref != ""){
                    radius = Double.parseDouble(distancePref); // taking the radios from the filters if exist
                }
                else
                    radius = 1000; // 1000 meters


                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(),
                        gps.getLongitude(), radius, types);




            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
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
                    // Get json response status
                    String status = nearPlaces.status;

                    // Check for all possible status
                    if(status.equals("OK")){
                        // Successfully got places details
                        if (nearPlaces.results != null) {
                            // loop through each place
                            for (Place p : nearPlaces.results) {
                                HashMap<String, String> map = new HashMap<String, String>();

                                // Place reference is used to get "place full details"
                                map.put(KEY_REFERENCE, p.reference);

                                // Place name
                                map.put(KEY_NAME, p.name);


                                // adding HashMap to ArrayList
                                placesListItems.add(map);
                            }
                            //list adapter
                            ListAdapter adapter = new SimpleAdapter(attractionsList.this, placesListItems,
                                   R.layout.list_item,
                                    new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                                    R.id.reference, R.id.name });

                            // Adding data into listview
                            lv.setAdapter(adapter);
                            lv.setContentDescription(adapter.toString());
                        }
                    }
                    else if(status.equals("ZERO_RESULTS")){
                        // Zero results found
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sorry no places found. Try to choose another places type").setTitle("Near Places");
                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                Intent intent = new Intent(context, attractionsList.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                    else if(status.equals("UNKNOWN_ERROR"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sorry unknown error occurred.").setTitle("Places Error");
                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                Intent intent = new Intent(context, attractionsList.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else if(status.equals("OVER_QUERY_LIMIT"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sorry query limit to google places is reached").setTitle("Places Error");
                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                Intent intent = new Intent(context, attractionsList.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else if(status.equals("REQUEST_DENIED"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sorry error occurred. Request is denied").setTitle("Places Error");
                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                Intent intent = new Intent(context, attractionsList.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else if(status.equals("INVALID_REQUEST"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sorry error occurred. Invalid Request").setTitle("Places Error");
                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                Intent intent = new Intent(context, attractionsList.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sorry error occurred.").setTitle("Places Error");
                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                Intent intent = new Intent(context, attractionsList.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });

        }


    }

    public void getFilters(){

        SharedPreferences SP = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        distancePref = SP.getString("Distance", "");
        ratingPref = SP.getInt("rating", 0);


    }

}
