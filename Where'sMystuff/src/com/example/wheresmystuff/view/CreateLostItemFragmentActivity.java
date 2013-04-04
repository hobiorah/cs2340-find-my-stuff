package com.example.wheresmystuff.view;

import java.util.Calendar;

import com.example.wheresmystuff.R;
import com.example.wheresmystuff.model.CreateLostItemQuery;
import com.example.wheresmystuff.model.Date;
import com.example.wheresmystuff.model.Location;
import com.example.wheresmystuff.model.LostItem;
import com.example.wheresmystuff.model.SimpleQueryResult;
import com.example.wheresmystuff.model.Utils;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
//import android.app.DialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Screen that allows user to submit a lost item
 * @author HarryO
 *
 */
public class CreateLostItemFragmentActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener{

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
	
	/**
	 * used to create a datepicker dialog for user to pick a date
	 * @author HarryO
	 *
	 */
	public static class DatePickerFragment extends DialogFragment 
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), (CreateLostItemFragmentActivity)getActivity(), year, month, day);
		}


	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		//do some stuff for example write on log and update TextField on activity
		Log.w("DatePicker","Date = " + year);
		date[0] = year;
		date[1] = month +1;
		date[2] = day;
		  Toast.makeText(getApplicationContext(), "month" + date[1], Toast.LENGTH_LONG).show();

		((TextView) findViewById(R.id.date_lost)).setText(date[1] + "/" + date[2] + "/" + date[0]);
	}
	

	public void showDatePickerDialog(View v) {

		final DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "DatePicker");
		//setDate();

	}


	/**
	 * When user hits submit item the query to submit the item will be executed
	 * @param view
	 */
	public void submitItem(View view) {
	  new CreateItemAttemptTask().execute();		
	
	}
	
	private boolean valid() {
		TextView name =((TextView) findViewById(R.id.item_name));
		TextView desc =((TextView) findViewById(R.id.item_description));
		TextView reward =((TextView) findViewById(R.id.item_reward));
		Spinner categoryChooser = (Spinner) findViewById(R.id.category_selector);
		String category = String.valueOf(categoryChooser.getSelectedItem());
		if(name.getText().toString()!=null && desc.getText().toString()!=null &&  Utils.convertCategoryBack(category)!=null && reward.getText().toString()!=null){
			return true;
		}else{
		return false;
		}
	}
	
	/**
	 * takes user to screen showing all the lost items
	 * @param veiw
	 */
	public void goToAllLostItems(View veiw){
		Intent lostItemList = new Intent(CreateLostItemFragmentActivity.this, AllLostItemsListActivity.class);
		  // Start signuppage activity, using the Intent
		 startActivity(lostItemList);
		 finish();
	}
	
	
	/**
	 * Executes query to submit a new lost item
	 * @author HarryO
	 *
	 */
	private class CreateItemAttemptTask extends AsyncTask<Void, Void, SimpleQueryResult>{
		private ProgressDialog pd;

		protected void onPreExecute(){
			pd = ProgressDialog.show(CreateLostItemFragmentActivity.this, null, "Submitting Item...", true);
		}

		protected SimpleQueryResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			CreateLostItemQuery query = new CreateLostItemQuery();
			TextView name =((TextView) findViewById(R.id.item_name));
			  TextView desc =((TextView) findViewById(R.id.item_description));
			  TextView reward =((TextView) findViewById(R.id.item_reward));
			  TextView state =((TextView) findViewById(R.id.state_location));
			  TextView city =((TextView) findViewById(R.id.city_location));
			  Spinner categoryChooser = (Spinner) findViewById(R.id.category_selector);
			  String category = String.valueOf(categoryChooser.getSelectedItem());
			  LostItem lost = new LostItem(name.getText().toString(), desc.getText().toString(), 
					  Utils.convertCategoryBack(category), reward.getText().toString(), new Date(date[1],date[2],date[0]),
					  new Location(city.getText().toString(),state.getText().toString()));
			  return query.create(lost);

		}

		protected void onPostExecute(SimpleQueryResult res){
			pd.dismiss();

			String x = "defaults";
			switch (res){
			case DB_ERROR:
				x = "db error";
				break;
			case NETWORK_ERROR:
				x = "network";
				break;
			}

			if (res == SimpleQueryResult.OK){
				Toast.makeText(getApplicationContext(), "Item Submitted Successfully", Toast.LENGTH_LONG).show();
				Intent lostItemList = new Intent(CreateLostItemFragmentActivity.this, AllLostItemsListActivity.class);
				  // Start signuppage activity, using the Intent
				 startActivity(lostItemList);
				finish();
			} else {
				popUp(x);
			}
		}
	}
	
	/**
	 * If a problem occured during query a pop showing what happened will show
	 * @param problem what went wrong with query
	 */
	public void popUp(CharSequence problem){
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage("Please try again").setTitle(problem);

		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
			}
		});
		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}




}
