package com.example.wheresmystuff;


import java.util.Calendar;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

public class AllFoundItemsListActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

	FoundItem[] items;
	
	ArrayAdapter<FoundItem> adapter;
	ListView list;
	 String[] values;
	 int[] dateChange;
	 int change = 0;// if 1 its category, if 2 its date, if 3 its location
	 ArrayAdapter<FoundItem> filtered;
	 String cat;
	 String city;
	 String state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_found_items_list);	
		list = (ListView)findViewById(R.id.list);
			values = new String[3];
			dateChange = new int[3];
	         values[0] = "ok";
	         values[1]="ash money";
	         values[2]="lets get it";
	        
	         a =new ArrayAdapter(this, android.R.layout.simple_list_item_1, values);
	        list.setAdapter(a);
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_found_items_list, menu);
		return true;
	}
	
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
			return new DatePickerDialog(getActivity(), (AllFoundItemsListActivity)getActivity(), year, month, day);
		}


	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		//do some stuff for example write on log and update TextField on activity
		Log.w("DatePicker","Date = " + year);
		dateChange[0] = year;
		dateChange[1] = month + 1;
		dateChange[2] = day;
		
	}
	

	public void showDatePickerDialog(View v) {

		final DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "DatePicker");
		//setDate();

	}


	public void filter(View view){
		popUp();
		
		if(change == 1){
			FoundItem[] categoryFilter = new FoundItem[items.length];
			
			for(int a = 0; a< categoryFilter.length; a++){
				if(Utils.convertCategory(items[a].getCategory()).equalsIgnoreCase(cat)){
					categoryFilter[a] = items[a];
				}
			}
			filtered = new ArrayAdapter<FoundItem>(AllFoundItemsListActivity.this,android.R.layout.simple_list_item_1, categoryFilter);
			list.setAdapter(adapter);
			
		}else if(change ==2){
			FoundItem[] dateFilter = new FoundItem[items.length];
			String dateCompare = dateChange[1] + " " + dateChange[0] + ", " + dateChange[2];
			for(int a = 0; a< dateFilter.length; a++){
				if(dateCompare.equalsIgnoreCase(items[a].getDateEntered().toString())){
					dateFilter[a]=items[a];
				}
			}
			filtered = new ArrayAdapter<FoundItem>(AllFoundItemsListActivity.this,android.R.layout.simple_list_item_1, dateFilter);
			list.setAdapter(adapter);
			//go through and compare dates and add to array that match dat
		}else if (change ==3){
			String compareLoc = city + ", " + state; 
			FoundItem[] locationFilter = new FoundItem[items.length];
			for(int a = 0; a< locationFilter.length; a++){
				if(compareLoc.equalsIgnoreCase(items[a].getLocation().toString())){
					locationFilter[a]=items[a];
				}
			}
			filtered = new ArrayAdapter<FoundItem>(AllFoundItemsListActivity.this,android.R.layout.simple_list_item_1, locationFilter);
			list.setAdapter(adapter);
			}
		else{
			
		}
		change = 0;
	}
	
	
	

	
	
	public void popUp(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Filter by What?");
	           builder.setItems(R.array.filter, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	              if(which ==0){
	            	final DialogFragment newFragment = new DatePickerFragment();
	          		newFragment.show(getSupportFragmentManager(), "DatePicker");
	          		change = 2;
	              }else if(which == 1){
	            	chooseCategory();
	            	
	              }else{
	            	  chooseLocation();
	              }
	           }
	    });
	           builder.setNegativeButton("Cancel",
	 				  new DialogInterface.OnClickListener() {
	 				    public void onClick(DialogInterface dialog,int id) {
	 					dialog.cancel();
	 				    }
	 				  });
	           
	           AlertDialog dialog = builder.create();
	   		dialog.show();
	}
	
	public void chooseCategory(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Choose Category");
	           builder.setItems(R.array.categories, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	              if(which == 0){
	            	  cat = "Miscellaneous";
	              }else if(which ==1){
	            	  cat = "Heirloom";
	              }else if(which == 2){
	            	  cat ="Keepsakes";
	              }
	            	change = 1;
	           }
	    });
	           builder.setNegativeButton("Cancel",
	 				  new DialogInterface.OnClickListener() {
	 				    public void onClick(DialogInterface dialog,int id) {
	 					dialog.cancel();
	 				    }
	 				  });
	           
	           AlertDialog dialog = builder.create();
	   		dialog.show();
	}
	
	public void chooseLocation(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.choose_location, null))
	    // Add action buttons
	           .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   TextView c = (TextView)findViewById(R.id.choose_city);
	                   city = c.getText().toString();
	                   TextView s = (TextView)findViewById(R.id.choose_state);
	                   state = s.getText().toString();
	                   change = 2;
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });      
	    AlertDialog dialog = builder.create();
  		dialog.show();
	}


	
	
	
	/**
	 * Allows app to query the database for the found items in an AsyncTask as to not interfere with the thread android is using
	 */
	private class GetItemsAttemptTask extends AsyncTask<Void, Void, AllLostItemsQueryResult>{
		
		private ProgressDialog pd;

		AllLostItemsQuery all;
		
		/**
		 * Method that shows progress bar as query is being executed
		 */
		protected void onPreExecute(){
			pd = ProgressDialog.show(AllFoundItemsListActivity.this, null, "Getting Found Items...", true);
		}

		/**
		 * Creates object to execute query and then executes the query
		 * @return  the result of the query
		 */
		protected AllLostItemsQueryResult doInBackground(Void... params) {
			all = new AllLostItemsQuery();
			return all.getAll();
		}

		/**
		 * Receives the result of the query and checks if it was ok, if it was
		 * it gets all the items in the database and puts it into an array for the array adapter
		 * which then sets the array adapter for the activity to the list from the dataabse
		 */
		protected void onPostExecute(AllLostItemsQueryResult sres){
			pd.dismiss();
			String x = "defaults";
			
			switch (sres){
			case OK:
				x = "ok";
				break;
			case DB_ERROR:
				x = "db error";
				break;
			case NETWORK_ERROR:
				x = "network";
				break;
			case EMPTY:
				x = "No available items.";
				break;
			}

			if (sres == AllLostItemsQueryResult.OK){
				items = all.getList();
				adapter = new ArrayAdapter<LostItem>(AllFoundItemsListActivity.this,android.R.layout.simple_list_item_1, items);
				setListAdapter(adapter);
			} else {
				popUp(x);
			}
		}
	}

	/**
	 * When a user clicks on an item a pop up will show up
	 */
	public void onListItemClick( ListView parent, View v, int position, long id)
	{ 
		popUp(items[position].getName().toString(),items[position]);
	}

	/**
	 * Pop up dialog to show the user the attributes of the item they clicked
	 *
	 * @param name the name of the item
	 * @param a the Found item reference 
	 */
	public void popUp(CharSequence name,FoundItem a){
		String present = "";
		present += "Reward is: "+ a.getReward() + "\n Category: " + a.getCategory() + " \n Date Found: " + a.getDateEntered().serialize()
				+ "\n Status : " + a.getStatus() + "\n Location: " + a.getLocation().toString();
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(present).setTitle(name);

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

	/**
	 * Pop up dialog that shows user what went wrong with the query
	 *
	 * @param problem the problem
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
