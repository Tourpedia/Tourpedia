package com.example.ebtes_000.tourpedia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;

/**
 * Created by ebtes_000 on 09/04/16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText dateTxt;
    public boolean isDateSet = false;
    public DatePickerFragment(View view){ // there was a problem here
       dateTxt = (EditText)view;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
       dateTxt.setText(day + "/" + (month + 1) + "/" + year);
        dateTxt.setContentDescription(day + "/" + (month + 1) + "/" + year);
        isDateSet = true;

    }
}
