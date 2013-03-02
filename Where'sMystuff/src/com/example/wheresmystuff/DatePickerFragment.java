package com.example.wheresmystuff;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	int[] date;
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
		 date[0] = year;
		 date[1] = month;
		 date[2] = day;
		 getDate();
		 
	}
	
	public int[] getDate(){
		//TextView lost = (TextView) findViewById(R.id.date_lost);
		//lost.setText(date[0] + "/" + date[1] + "/" + date[2]);
		return date;
	}
}
// return a datepickerdialog from the oncreateodialog method

// i need to implement the ondateset method cause android wants me to
// my implementation will get the date