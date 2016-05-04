package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class filter extends AppCompatActivity {
    private int distanceAtt , ratingAtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_filter);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(filter.this, home.class);
                startActivity(intent);
            }
        });

        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(filter.this, settings.class);
                startActivity(intent);
            }
        });

        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(filter.this, filter.class);
                startActivity(intent);
            }
        });

        final EditText distanceText = (EditText) findViewById(R.id.distance);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submit = (Button) findViewById(R.id.submitButton);
     //   double rate = ratingBar.getRating();


        distanceText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("res1", "rating:" + s.toString());
                distanceAtt = Integer.parseInt(s.toString());

            }
        });

        //if rating value is changed
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                Log.d("res1", "rating:" + rating);
                ratingAtt = (int) rating;

            }
        });


        //if click on me, then display the current rating value.
        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          // save to file
                                          saveFilterToFile();
                                      }

                                  }

        );


        }// onCreate end

    void saveFilterToFile() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Filter.txt", MODE_PRIVATE));
            outputStreamWriter.write(distanceAtt+"\n");
            outputStreamWriter.write(ratingAtt + "\n");

            outputStreamWriter.close();
        }  // try
        catch (IOException e) {
            e.printStackTrace();
        }
    }// saveFile end


    public filter() {
        retriveFilterFromFile();
    }
    
      void retriveFilterFromFile() {
        try {
            String settingName;
            int lineNum=1;

            FileInputStream fileInputStream = openFileInput("Filter.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Log.d("trace1", "after stream");
            StringBuffer stringBuffer = new StringBuffer();

            while ((settingName = bufferedReader.readLine()) != null) {
                Log.d("trace1",lineNum+"");
                if (lineNum == 1){
                    Log.d("trace1", "inside if 1");
                    distanceAtt = Integer.parseInt(settingName);
                }
                else if (lineNum == 2) {
                    Log.d("trace1","inside if 2");
                    ratingAtt = Integer.parseInt(settingName);
                }

                lineNum++;
            }//while

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//retriveFrom end

    public int getDistance() {
        return distanceAtt;
    }

    public int getRating() {
        return ratingAtt;
    }
}
