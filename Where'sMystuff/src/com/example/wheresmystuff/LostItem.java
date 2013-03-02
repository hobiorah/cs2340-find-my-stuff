package com.example.wheresmystuff;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
//import android.app.DialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class LostItem extends FragmentActivity implements DatePicker.OnDateChangedListener {

	private static final int MY_DATE_DIALOG_ID = 3;
	static int[] date= new int[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lost_item, menu);
		return true;
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

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
		}
	}

	public void showDatePickerDialog(View v) {
		
	    final DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	    setDate();
	    
	}
		
	
	
	
	
	
	public void setDate() {
		TextView lost = (TextView) findViewById(R.id.date_lost);
		lost.setText(date[0] + "/" + date[1] + "/" + date[2]);
		
		
	}

	public void submitItem(View view) {
		//send it to thing
		//Spinner categoryChooser = (Spinner) findViewById(R.id.category_selector);
		//String category = String.valueOf(categoryChooser.getSelectedItem());
		finish();

	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		TextView lost = (TextView) findViewById(R.id.date_lost);
		lost.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
		
				
	}

}
