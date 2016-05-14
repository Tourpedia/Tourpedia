package com.example.ebtes_000.tourpedia;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class PlanDetails extends AppCompatActivity {
    String planName; // should be date
    ListView listOfEvents;
    ArrayList<slot> slots = null;
    String splits[] = null;
    LinearLayout allInfo;
    LinearLayout eventSec;
    String oldName = "";
    String oldDate = "";
    Boolean isEventEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);
        getSupportActionBar().hide(); //hide


        ImageButton home = (ImageButton) findViewById(R.id.homeBtn);
        // to home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetails.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // declaring settings and filters img buttons
        ImageButton setting = (ImageButton) findViewById(R.id.settingsBtn);
        ImageButton filters = (ImageButton) findViewById(R.id.filterBtn);

        // to settings
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetails.this, settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // to filters
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetails.this, filter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        allInfo = (LinearLayout) findViewById(R.id.name_date);
        eventSec = (LinearLayout) findViewById(R.id.event);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                planName = null;
            } else {
                planName = extras.getString("planName");
            }
        } else {
            planName = (String) savedInstanceState.getSerializable("planName");
        }

        final EditText planDateTxt = (EditText) findViewById(R.id.planDate);
        planDateTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    datePickerFragment.show(ft, "DatePicker");
                    planDateTxt.setInputType(InputType.TYPE_NULL);
                }
            }
        });

        final EditText startTxt = (EditText) findViewById(R.id.timeFrom);
        startTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerFragment timePickerFragment = new TimePickerFragment(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    timePickerFragment.show(ft, "TimePicker");
                    startTxt.setInputType(InputType.TYPE_NULL);
                }
            }
        });

        final EditText endTxt = (EditText) findViewById(R.id.timeTo);
        endTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerFragment timePickerFragment = new TimePickerFragment(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    timePickerFragment.show(ft, "TimePicker");
                    endTxt.setInputType(InputType.TYPE_NULL);
                }
            }
        });
        isEventEdited = false;
        displayPlanDetails(planName);


    }

    public void displayPlanDetails(String pName) {
        try {
            String planDetails;
            int lineNum = 1;
            slots = new ArrayList<slot>();


            FileInputStream fileInputStream = openFileInput(pName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer1 = new StringBuffer();
            slot s = null;
            while ((planDetails = bufferedReader.readLine()) != null) {
                StringBuffer stringBuffer = new StringBuffer();
                //stringBuffer.append(planDetails + "\n");
                if (lineNum == 1) {
                    EditText name = (EditText) findViewById(R.id.planName);
                    stringBuffer.append(planDetails);
                    name.setText(stringBuffer.toString());
                    oldName = stringBuffer.toString();
                } else if (lineNum == 2) {
                    EditText date = (EditText) findViewById(R.id.planDate);
                    stringBuffer.append(planDetails);
                    date.setText(stringBuffer.toString());
                    oldDate = stringBuffer.toString();
                } else {
                    if (planDetails != "") {
                        splits = planDetails.split(","); // to split event info
                        s = new slot(splits[0], splits[1], splits[2]);
                        slots.add(s);
                    }


                }
                lineNum++;
            } //end while
            if (slots != null)
                showSavedEvents();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePlan(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        File dir = getFilesDir();
                        File file = new File(dir, planName);
                        boolean deleted = file.delete();
                        if (deleted) {
                            Toast.makeText(getApplicationContext(), "Plan Deleted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PlanDetails.this, plans.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                }
        );
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                }
        );
        AlertDialog alert = builder.create();
        alert.show();
    } // end deletePlan

    public void showSavedEvents() {
        for (int i = 0; i < slots.size(); i++)
            slots.get(i).setSelected(false);
        listOfEvents = (ListView) findViewById(R.id.list);
        ArrayAdapter<slot> adapter = new ArrayAdapter<slot>(PlanDetails.this, android.R.layout.simple_list_item_1, slots);
        listOfEvents.setAdapter(adapter);
        showEventDetails();

    }

    public void showEventDetails() {
        listOfEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //         String planName = (String) listOfEvents.getItemAtPosition(position);
                LinearLayout allInfo = (LinearLayout) findViewById(R.id.name_date);
                allInfo.setVisibility(View.INVISIBLE);
                LinearLayout eventSec = (LinearLayout) findViewById(R.id.event);
                eventSec.setVisibility(View.VISIBLE);
                ImageButton clearBtn = (ImageButton) findViewById(R.id.AddEventClearingBtn);
                clearBtn.setVisibility(View.INVISIBLE);
                LinearLayout buttons = (LinearLayout) findViewById(R.id.editdelBtns);
                buttons.setVisibility(View.VISIBLE);
                EditText place = (EditText) findViewById(R.id.placeTxt);
                EditText start = (EditText) findViewById(R.id.timeFrom);
                EditText end = (EditText) findViewById(R.id.timeTo);

                place.setText(slots.get(position).getaPlace());
                start.setText(slots.get(position).getStartTime());
                end.setText(slots.get(position).getEndTime());

                slots.get(position).setSelected(true);


            }
        });
    }

    public void editPlan(View view) {
        EditText name = (EditText) findViewById(R.id.planName);
        EditText date = (EditText) findViewById(R.id.planDate);
        String newName = name.getText().toString();
        String newDate = date.getText().toString();
        Boolean flag = false;
        if (!(oldName.equals(newName))) {
            if (newName.length() > 0) {
                updateName(newName);
                flag = true;
            } else
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();

        }

        if (!(oldDate.equals(newDate))) {
            if (newName.length() > 0) {
                updateDate(newDate);
                flag = true;
            } else
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
        if (flag == false) {
            if (isEventEdited == false){
            Toast.makeText(getApplicationContext(), "No edits!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PlanDetails.this, plans.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void updateName(String newName) {
        try {
            String planDetails;
            int lineNum = 1;
            String allTxt = "";


            FileInputStream fileInputStream = openFileInput(planName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            slot s = null;
            while ((planDetails = bufferedReader.readLine()) != null) {
                StringBuffer stringBuffer = new StringBuffer();
                if (lineNum == 1) {
                    planDetails = newName;
                }
                allTxt += (planDetails + "\n");
                lineNum++;
            } //end while

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(planName, MODE_PRIVATE));
            // writing name
            outputStreamWriter.write(allTxt);
            outputStreamWriter.close();

            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PlanDetails.this, plans.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }// end updateName

    private void updateDate(String newDate) {

        try {
            String planDetails;
            int lineNum = 1;
            String allTxt = "";


            FileInputStream fileInputStream = openFileInput(planName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            slot s = null;
            while ((planDetails = bufferedReader.readLine()) != null) {
                StringBuffer stringBuffer = new StringBuffer();
                if (lineNum == 2) {
                    planDetails = newDate;
                }
                allTxt += (planDetails + "\n");
                lineNum++;
            } //end while

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(getDayOfWeek(newDate), MODE_PRIVATE));
            // writing name
            outputStreamWriter.write(allTxt);
            outputStreamWriter.close();
            // delete old plan
            File dir = getFilesDir();
            File file = new File(dir, planName);
            boolean deleted = file.delete();
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PlanDetails.this, plans.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }//end updateDate

    public void deleteEvent(View view) {
        isEventEdited = true;
        for (int i = 0; i < slots.size(); i++)
            if (slots.get(i).getSelected() == true) {
                slots.remove(i);
                return;
            }
        updateFile();
        Toast.makeText(getApplicationContext(), "Event Deleted", Toast.LENGTH_LONG).show();
        displayPlanDetails(planName);
        allInfo.setVisibility(View.VISIBLE);
        LinearLayout eventSec = (LinearLayout) findViewById(R.id.event);
        eventSec.setVisibility(View.INVISIBLE);

    }

    public void updateEvent(View view) {
        EditText place = (EditText) findViewById(R.id.placeTxt);
        EditText start = (EditText) findViewById(R.id.timeFrom);
        EditText end = (EditText) findViewById(R.id.timeTo);

        for (int i = 0; i < slots.size(); i++)
            if (slots.get(i).getSelected() == true) {
                slots.get(i).setaPlace(place.getText().toString());
                slots.get(i).setStartTime(start.getText().toString());
                slots.get(i).setEndTime(end.getText().toString());


                updateFile();
                Toast.makeText(getApplicationContext(), "Event Updated", Toast.LENGTH_LONG).show();
                LinearLayout allInfo = (LinearLayout) findViewById(R.id.name_date);
                displayPlanDetails(planName);
                allInfo.setVisibility(View.VISIBLE);
                LinearLayout eventSec = (LinearLayout) findViewById(R.id.event);
                eventSec.setVisibility(View.INVISIBLE);
            }
    }

    private void updateFile() {
        isEventEdited = true;

        try {
            String planDetails;
            int lineNum = 1;
            String allTxt = "";


            FileInputStream fileInputStream = openFileInput(planName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            slot s = null;
            while (lineNum < 3) {
                planDetails = bufferedReader.readLine();
                allTxt += (planDetails + "\n");
                lineNum++;
            } //end while


            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(planName, MODE_PRIVATE));
            // writing name
            outputStreamWriter.write(allTxt);
            for (int i = 0; i < slots.size(); i++) {
                slot slot = slots.get(i);
                outputStreamWriter.write(slot.getaPlace() + "," + slot.getStartTime() + "," + slot.getEndTime() + "\n");
            }
            outputStreamWriter.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDayOfWeek(String date) {
        int s1, s2, s3;
        String yr;
        String[] out = date.split("/");
        s1 = Integer.parseInt(out[0]);
        s2 = Integer.parseInt(out[1]) - 1;
        yr = out[2];
        char a, b, c, d;
        a = yr.charAt(0);
        b = yr.charAt(1);
        c = yr.charAt(2);
        d = yr.charAt(3);
        s3 = Character.getNumericValue(a) * 1000 +
                Character.getNumericValue(b) * 100 +
                Character.getNumericValue(c) * 10 +
                Character.getNumericValue(d);
        Calendar cal = Calendar.getInstance();
        cal.set(s3, s2, s1);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return "Sunday, " + out[0] + "-" + out[1] + "-" + out[2];
            case 2:
                return "Monday, " + out[0] + "-" + out[1] + "-" + out[2];
            case 3:
                return "Tuesday, " + out[0] + "-" + out[1] + "-" + out[2];
            case 4:
                return "Wednesday, " + out[0] + "-" + out[1] + "-" + out[2];
            case 5:
                return "Thursday, " + out[0] + "-" + out[1] + "-" + out[2];
            case 6:
                return "Friday, " + out[0] + "-" + out[1] + "-" + out[2];
            case 7:
                return "Saturday, " + out[0] + "-" + out[1] + "-" + out[2];
        }
        return "";
    }

    public void addEvent(View view) {
        EditText placeName = (EditText) findViewById(R.id.placeTxt);
        EditText from = (EditText) findViewById(R.id.timeFrom);
        EditText to = (EditText) findViewById(R.id.timeTo);
        // clearing information
        placeName.getText().clear();
        placeName.setHint("Place to go");
        from.getText().clear();
        from.setHint("From");
        to.getText().clear();
        to.setHint("To");
        LinearLayout allInfo = (LinearLayout) findViewById(R.id.name_date);
        allInfo.setVisibility(View.INVISIBLE);
        LinearLayout eventSec = (LinearLayout) findViewById(R.id.event);
        eventSec.setVisibility(View.VISIBLE);
        LinearLayout buttons = (LinearLayout) findViewById(R.id.editdelBtns);
        buttons.setVisibility(View.INVISIBLE);
        ImageButton addEvent = (ImageButton) findViewById(R.id.AddEventClearingBtn);
        addEvent.setVisibility(View.VISIBLE);



    }
    public void ClearingFields(View view){
        EditText placeName = (EditText) findViewById(R.id.placeTxt);
        EditText from = (EditText) findViewById(R.id.timeFrom);
        EditText to = (EditText) findViewById(R.id.timeTo);
        if (checkFields(placeName, from, to)) {
            String place = placeName.getText().toString();
            String timeFrom = from.getText().toString();
            String timeTo = to.getText().toString();

            if(checkTime(timeFrom,timeTo)) {
                slot s = new slot(place, timeFrom, timeTo);
                slots.add(s);
                // clearing information
                placeName.getText().clear();
                placeName.setHint("Place to go");
                from.getText().clear();
                from.setHint("From");
                to.getText().clear();
                to.setHint("To");
                updateFile();
                Toast.makeText(getApplicationContext(), "Event Added", Toast.LENGTH_LONG).show();
                displayPlanDetails(planName);
                allInfo.setVisibility(View.VISIBLE);
                eventSec.setVisibility(View.INVISIBLE);

            }
            else
                Toast.makeText(getApplicationContext(), "Please enter a correct period of time", Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
    }


    private Boolean checkFields(EditText place, EditText start, EditText end) {
        if ( place.getText().toString().length() == 0 || start.getText().toString().length() == 0
                || end.getText().toString().length() == 0)
            return false;
        return true;
    }
    private Boolean checkTime (String from , String to){
        int start = Integer.parseInt(from.substring(0, from.indexOf(":")));
        int end = Integer.parseInt(to.substring(0, to.indexOf(":")));
        if (start < end)
            return true;
        else
        if (start == end){
            int s = Integer.parseInt(from.substring(from.indexOf(":")+1));
            int e = Integer.parseInt(to.substring(to.indexOf(":")+1));
            if (s < e)
                return true;
        }
        return false;

    }// end checkTime

}

