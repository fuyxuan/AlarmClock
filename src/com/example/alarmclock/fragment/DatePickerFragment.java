package com.example.alarmclock.fragment;

import java.util.Calendar;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

@SuppressLint("NewApi")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	private int 
		year,
		month,
		day;
	
	public interface OnDatePicked{
		void onDatePicked(int year, int month, int day);
	}

	private OnDatePicked listener;
	public DatePickerFragment(OnDatePicked listener) {
		this.listener = listener;
	}

	@SuppressLint("NewApi")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceSateate) {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		month++;
		listener.onDatePicked(year, month, day);
	}
	
}
