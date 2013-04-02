package com.example.wheresmystuff;

import java.util.Calendar;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateFoundItemFragmentActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener{
	static int[] date= new int[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_found_item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.found_item, menu);
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
			return new DatePickerDialog(getActivity(), (CreateFoundItemFragmentActivity)getActivity(), year, month, day);
		}


	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		//do some stuff for example write on log and update TextField on activity
		Log.w("DatePicker","Date = " + year);
		date[0] = year;
		date[1] = month +1;
		date[2] = day;
		 // Toast.makeText(getApplicationContext(), "month" + date[1], Toast.LENGTH_LONG).show();

		((TextView) findViewById(R.id.date_found)).setText(date[1] + "/" + date[2] + "/" + date[0]);
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
	  new CreateFoundItemAttemptTask().execute();		
	
	}
	
	private boolean valid() {
		TextView name =((TextView) findViewById(R.id.item_namef));
		TextView desc =((TextView) findViewById(R.id.item_descriptionf));
		TextView reward =((TextView) findViewById(R.id.item_rewardf));
		Spinner categoryChooser = (Spinner) findViewById(R.id.category_selectorf);
		String category = String.valueOf(categoryChooser.getSelectedItem());
		if(name.getText().toString()!=null && desc.getText().toString()!=null &&  Utils.convertCategoryBack(category)!=null && reward.getText().toString()!=null){
			return true;
		}else{
		return false;
		}
	}
	
	/**
	 * takes user to screen showing all the found items
	 * @param veiw
	 */
	public void goToAllFoundItems(View veiw){
		Intent foundItemList = new Intent(CreateFoundItemFragmentActivity.this, AllFoundItemsListActivity.class);
		  // Start signuppage activity, using the Intent
		 startActivity(foundItemList);
		 finish();
	}
	
	
	/**
	 * Executes query to submit a new found item
	 * @author HarryO
	 *
	 */
	private class CreateFoundItemAttemptTask extends AsyncTask<Void, Void, SimpleQueryResult>{
		private ProgressDialog pd;

		protected void onPreExecute(){
			pd = ProgressDialog.show(CreateFoundItemFragmentActivity.this, null, "Submitting Item...", true);
		}

		protected SimpleQueryResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			CreateFoundItemQuery query = new CreateFoundItemQuery();
			TextView name =((TextView) findViewById(R.id.item_namef));
			  TextView desc =((TextView) findViewById(R.id.item_descriptionf));
			  TextView reward =((TextView) findViewById(R.id.item_rewardf));
			  TextView state =((TextView) findViewById(R.id.state_locationf));
			  TextView city =((TextView) findViewById(R.id.city_locationf));
			  Spinner categoryChooser = (Spinner) findViewById(R.id.category_selectorf);
			  String category = String.valueOf(categoryChooser.getSelectedItem());
			  FoundItem found = new FoundItem(name.getText().toString(), desc.getText().toString(), 
					  Utils.convertCategoryBack(category), reward.getText().toString(), new Date(date[1],date[2],date[0]),
					  new Location(city.getText().toString(),state.getText().toString()));
			  return query.create(found);

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
				Intent foundItemList = new Intent(CreateFoundItemFragmentActivity.this, AllFoundItemsListActivity.class);
				  // Start signuppage activity, using the Intent
				 startActivity(foundItemList);
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
