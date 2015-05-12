package com.example.alarmclock.fragment;

import java.util.Calendar;

import com.example.alarmclock.MainActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

@SuppressLint("NewApi")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	
	private int hour,minute;
	public interface OnTimePicked{
	    void onTimePicked(int hour,int minute);
	}
	private OnTimePicked listener;
	public TimePickerFragment(OnTimePicked listener){
		this.listener = listener;
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		listener.onTimePicked(hourOfDay, minute);
		Log.i("msg","hourOfDay:" + hourOfDay + "  minute:"+minute);
	}
	
}