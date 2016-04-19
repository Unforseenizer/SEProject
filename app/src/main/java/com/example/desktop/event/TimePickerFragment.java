package com.example.desktop.event;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    OnHeadlineSelectedListener mCallback;

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        try {
            mCallback = (OnHeadlineSelectedListener) this.getTargetFragment();
            Log.e("Callback", "done");
        } catch (ClassCastException e) {
            throw new ClassCastException(c.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getContext(), this, hour, minute,
                false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (mCallback != null) {
            mCallback.onTimeSet(view, hourOfDay, minute);
        }
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }
}