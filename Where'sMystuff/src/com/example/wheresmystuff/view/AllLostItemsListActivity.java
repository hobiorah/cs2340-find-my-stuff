package com.example.wheresmystuff.view;


import java.util.ArrayList;
import java.util.Calendar;


import com.example.wheresmystuff.R;
import com.example.wheresmystuff.controller.AllItemsQueryResult;
import com.example.wheresmystuff.controller.AllLostItemsQuery;
import com.example.wheresmystuff.model.Category;
import com.example.wheresmystuff.model.Date;
import com.example.wheresmystuff.model.Item;
import com.example.wheresmystuff.model.Location;
import com.example.wheresmystuff.model.*;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * Screen to show all the lost items in the database
 * 
 */
public class AllLostItemsListActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener{

	LostItem[] items;
	 ArrayList<Item> adapterItems = new ArrayList<Item>();
	ArrayAdapter<Item> adapter;
	ListView listL;

	int[] dateChange;
	int change = 0;// if 1 its category, if 2 its date, if 3 its location
	String cat;
	String city; 
	String state;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_all_lost_items);
		dateChange = new int[3];
		listL = (ListView)findViewById(R.id.listL);
		listL.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				popUp(adapterItems.get(position).getName().toString(),adapterItems.get(position));
			}});



		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		new GetItemsAttemptTask().execute();
	}


	/**
	 * brings up pop up for user to choose how he or she wants to filter
	 * @param view
	 */
	public void filter(View view){
		popUp();
	}

	/**
	 * filters item by category
	 * @param category category user chooses
	 */
	public void filterCategory(Category category){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){
			
			if (items[a].getCat() == category) adapterItems.add(items[a]);

			
		}
		
		adapter.notifyDataSetChanged();


	}
	
	/**
	 * filter items by date
	 * @param date date user wants
	 */
	public void filterDate(Date date){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){
			
			if (items[a].getDate().compare(date)) adapterItems.add(items[a]);

			
		}
		
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * filters by location
	 * @param loc location user wants
	 */
	public void filterLocation(Location loc){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){
			
			if (items[a].getLocation().compare(loc)) adapterItems.add(items[a]);

			
		}
		
		adapter.notifyDataSetChanged();	
	}


	/**
	 * pop up that allows user to filter how he or she wants
	 */
	public void popUp(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Filter by What?");
		builder.setItems(R.array.filter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(which ==0){
					chooseCategory();
					//change = 1;
				}else if(which == 1){
					final DialogFragment newFragment = new DatePickerFragment();
					newFragment.show(getSupportFragmentManager(), "DatePicker");
					//change =2;
				}else{
					chooseLocation();
					//change =3;
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

	/**
	 * gives user option to choose category he or she wants to filter by
	 */
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
				default:
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


	/**
	 * givers user optioin to enter location he or she wants to filter by
	 */
	public void chooseLocation(){
		View promptsView=null;
		LayoutInflater li = LayoutInflater.from(AllLostItemsListActivity.this);
		
        	promptsView = li.inflate(R.layout.choose_location, null);
        	
		

		final EditText cityE = (EditText) promptsView.findViewById(R.id.choose_city);
		final EditText stateE = (EditText) promptsView.findViewById(R.id.choose_state);
		
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(promptsView);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Where?");

		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
				city = cityE.getText().toString();
				state = stateE.getText().toString();

				filterLocation(new Location(city,state));
				
			
		}});
		
		builder.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });
		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
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
			return new DatePickerDialog(getActivity(), (AllLostItemsListActivity)getActivity(), year, month, day);
		}


	}

	/**
	 * When user picks date the filter method is called to show items from date picked
	 */
	public void onDateSet(DatePicker view, int year, int month, int day) {
		//do some stuff for example write on log and update TextField on activity
		Log.w("DatePicker","Date = " + year);
		dateChange[0] = year;
		dateChange[1] = month + 1;
		dateChange[2] = day;
		filterDate(new Date(month+1,day, year));
		//change = 2;
	}

	/**
	 * shows datepicker Dialog
	 * @param v
	 */
	public void showDatePickerDialog(View v) {

		final DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "DatePicker");
		//setDate();

	}

	/**
	 * Allows app to query the database for the lost items in an AsyncTask as to not interfere with the thread android is using
	 */
	private class GetItemsAttemptTask extends AsyncTask<Void, Void, AllItemsQueryResult>{

		private ProgressDialog pd;

		AllLostItemsQuery all;

		/**
		 * Method that shows progress bar as query is being executed
		 */
		protected void onPreExecute(){
			pd = ProgressDialog.show(AllLostItemsListActivity.this, null, "Getting Lost Items...", true);
		}

		/**
		 * Creates object to execute query and then executes the query
		 * @return  the result of the query
		 */
		protected AllItemsQueryResult doInBackground(Void... params) {
			all = new AllLostItemsQuery();
			return all.getAll();
		}

		/**
		 * Receives the result of the query and checks if it was ok, if it was
		 * it gets all the items in the database and puts it into an array for the array adapter
		 * which then sets the array adapter for the activity to the list from the dataabse
		 */
		protected void onPostExecute(AllItemsQueryResult sres){
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
			default:
				break;
			}


			if (sres == AllItemsQueryResult.OK){
				items = all.getList();
				//original = all.getList();
				adapterItems.clear();
				for(int a = 0; a<items.length; a++){
					adapterItems.add(items[a]);
				}
				adapter = new ArrayAdapter<Item>(AllLostItemsListActivity.this,android.R.layout.simple_list_item_1, adapterItems);
				listL.setAdapter(adapter);
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
	 * @param item the lost item reference 
	 */
	public void popUp(CharSequence name,Item item){
		String present = "";
		present += " Reward is: "+ item.getReward() + "\n Category: " + item.getCategory() + " \n Date Lost: " + item.getDateEntered().serialize()
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

