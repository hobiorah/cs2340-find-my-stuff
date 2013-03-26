package com.example.wheresmystuff;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllFoundItemsListActivity extends Activity {

	FoundItem[] items;
	
	ArrayAdapter<FoundItem> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_found_items_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_found_items_list, menu);
		return true;
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
	public void popUp(CharSequence name,LostItem a){
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
