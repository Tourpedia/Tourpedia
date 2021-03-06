package com.example.ebtes_000.tourpedia;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;

/**
 * Created by ebtes_000 on 09/04/16.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    EditText timeTxt;
    public TimePickerFragment(View view){ // there was a problem here
        timeTxt = (EditText)view;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
       //timeTxt.setText(timeTxt.getText());
       timeTxt.setText( hourOfDay + ":" + minute);
        timeTxt.setContentDescription( hourOfDay + ":" + minute);
    }
}
