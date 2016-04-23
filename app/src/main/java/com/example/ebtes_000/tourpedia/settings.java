package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class settings extends AppCompatActivity {

    private boolean isBlind ,planAlert , haveGoogleGlass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.activity_settings);

        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, home.class);
                startActivity(intent);
            }
        });


        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);
        try {
        Switch BlindMode = (Switch) findViewById(R.id.blindMode);
        Switch GoogleGlass = (Switch) findViewById(R.id.googleGlass);
        Switch PlanAlert = (Switch) findViewById(R.id.planAlert);
        BlindMode.setChecked(false);
        GoogleGlass.setChecked(false);
        PlanAlert.setChecked(false);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, settings.class);
                startActivity(intent);
            }
        });


        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, filter.class);
                startActivity(intent);
            }
        });


        //attach a listener to check for changes in state
        BlindMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    Log.d("status","Switch is currently ON");
                    isBlind = true;
                    // open blind interface
                } else {
                    Log.d("status", "Switch is currently OFF");
                    isBlind = false;
                }
                saveSettingToFile();
            }
        });

        GoogleGlass.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    Log.d("status","haveGoogleGlass Switch is currently ON");
                    haveGoogleGlass = true;
                } else {
                    Log.d("status", "haveGoogleGlass Switch is currently OFF");
                    haveGoogleGlass = false;
                }
                saveSettingToFile();

            }
        });

        PlanAlert.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    Log.d("status", "planAlert haveGoogleGlass Switch is currently ON");
                    planAlert = true;
                } else {
                    Log.d("status", "planAlert haveGoogleGlass Switch is currently OFF");
                    planAlert = false;
                }
               // saveSettingToFile();
            }
        });

        } catch (Exception e) {
            Log.d("Exc",e.toString());
        }

       // saveSettingToFile();
        //saveSettingToFile();
        //retriveSettingFromFile();
    }//onCreate end


    void saveSettingToFile() {

/*   try {
       Log.d("file","start save to file");
       String fileName = "com.example.ebtes_000.tourpedia'\'Files'\'Setting.txt";
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE));
        Log.d("after","a");
       outputStreamWriter.write("hey \n");
        outputStreamWriter.write(isBlind+"\n");
        outputStreamWriter.write(haveGoogleGlass+"\n");
        outputStreamWriter.write(planAlert+"\n");

        outputStreamWriter.close();
         }  // try
        catch (IOException e) {
            e.printStackTrace();
        }*/
    }// saveFile end

    public settings() {
        retriveSettingFromFile();
    }

    void retriveSettingFromFile() {
     /*   try {
            String settingName;
            int lineNum=1;

            FileInputStream fileInputStream = openFileInput("Setting.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Log.d("trace1", "after stream");
            StringBuffer stringBuffer = new StringBuffer();

            while ((settingName = bufferedReader.readLine()) != null) {
                Log.d("trace1",lineNum+"");
                if (lineNum == 1){
                    Log.d("trace1", "inside if 1");
                    if (settingName.equals("true"))
                    isBlind = true;
                    else
                        isBlind = false;
                }
                else if (lineNum == 2) {
                    Log.d("trace1","inside if 2");
                    if (settingName.equals("true"))
                        haveGoogleGlass = true;
                    else
                        haveGoogleGlass = false;

                }
                else if (lineNum == 3) {
                    Log.d("trace1","inside if 3");
                    if (settingName.equals("true"))
                        planAlert = true;
                    else
                        planAlert = false;

                }
                lineNum++;
            }//while

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } */
    }//retriveFrom end

    public boolean getIsBlind() {
        return isBlind;
    }

    public boolean getGoogleGlass() {
        return haveGoogleGlass;
    }
    public boolean getPlanAlert() {
        return planAlert;
    }
}
