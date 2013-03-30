package com.example.wheresmystuff;


import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AllFoundItemsListActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

	FoundItem[] items;
	
	ArrayAdapter<Item> adapter;
	ListView list;
	 String[] values;
	 int[] dateChange;
	 int change = 0;// if 1 its category, if 2 its date, if 3 its location
	 ArrayList<Item> adapterItems = new ArrayList<Item>();
	 String cat;
	 String city;
	 String state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_found_items_list);	
		list = (ListView)findViewById(R.id.list);
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					popUp(adapterItems.get(position).getName().toString(),adapterItems.get(position));
				}});
			dateChange = new int[3];
	         
	        
	         new GetFoundItemsAttemptTask().execute();
	         //a =new ArrayAdapter(this, android.R.layout.simple_list_item_1, values);
	        //list.setAdapter(a);
	
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
		filterDate(new Date(month+1,day, year));
		
	}
	

	public void showDatePickerDialog(View v) {

		final DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "DatePicker");
		//setDate();

	}


	public void filter(View view){
		popUp();
		
	}
	
	public void filterCategory(Category category){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){
			
			if (items[a].getCat() == category) adapterItems.add(items[a]);

			
		}
		
		adapter.notifyDataSetChanged();


	}
	
	public void filterDate(Date date){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){
			
			if (items[a].getDate().compare(date)) adapterItems.add(items[a]);

			
		}
		
		adapter.notifyDataSetChanged();
	}
	
	public void filterLocation(Location loc){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){
			
			if (items[a].getLocation().compare(loc)) adapterItems.add(items[a]);

			
		}
		
		adapter.notifyDataSetChanged();	
	}

	
	

	
	
	public void popUp(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Filter by What?");
	           builder.setItems(R.array.filter, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	              if(which ==0){
		            chooseCategory();
	          		//change = 2;
	              }else if(which == 1){
	            	final DialogFragment newFragment = new DatePickerFragment();
	          		newFragment.show(getSupportFragmentManager(), "DatePicker");
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
				Category tempCat = Category.MISCELLANEOUS;
				
				switch (which){
				case 0:
					tempCat = Category.MISCELLANEOUS;
					break;
				case 1:
					tempCat = Category.HEIRLOOMS;
					break;
				case 2:
					tempCat = Category.KEEPSAKES;
					break;
				}
				filterCategory(tempCat);
				
				
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
				//change = 2;
				filterLocation(new Location(city,state));
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
	private class GetFoundItemsAttemptTask extends AsyncTask<Void, Void, AllFoundItemsQueryResult>{
		
		private ProgressDialog pd;

		AllFoundItemsQuery all;
		
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
		protected AllFoundItemsQueryResult doInBackground(Void... params) {
			all = new AllFoundItemsQuery();
			return all.getAll();
		}

		/**
		 * Receives the result of the query and checks if it was ok, if it was
		 * it gets all the items in the database and puts it into an array for the array adapter
		 * which then sets the array adapter for the activity to the list from the dataabse
		 */
		protected void onPostExecute(AllFoundItemsQueryResult sres){
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

			if (sres == AllFoundItemsQueryResult.OK){
				items = all.getList();
				adapterItems.clear();
				for(int a = 0; a<items.length; a++){
					adapterItems.add(items[a]);
				}
				adapter = new ArrayAdapter<Item>(AllFoundItemsListActivity.this,android.R.layout.simple_list_item_1, adapterItems);
				list.setAdapter(adapter);
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
	 * @param item the Found item reference 
	 */
	public void popUp(CharSequence name,Item item){
		String present = "";
		present += "Reward is: "+ item.getReward() + "\n Category: " + item.getCategory() + " \n Date Found: " + item.getDateEntered().serialize()
				+ "\n Status : " + item.getStatus() + "\n Location: " + item.getLocation().toString();
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
