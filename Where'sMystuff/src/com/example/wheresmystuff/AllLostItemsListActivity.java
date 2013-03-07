package com.example.wheresmystuff;


import java.util.ArrayList;
import java.util.Calendar;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AllLostItemsListActivity extends ListActivity{

	LostItem[] items;
	ArrayAdapter<LostItem> adapter ;
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };

		new GetItemsAttemptTask().execute();
	}
	private class GetItemsAttemptTask extends AsyncTask<Void, Void, SimpleQueryResult>{
		private ProgressDialog pd;

		AllLostItemsQuery all;
		protected void onPreExecute(){
			pd = ProgressDialog.show(AllLostItemsListActivity.this, null, "Getting Lost Items...", true);
		}

		protected SimpleQueryResult doInBackground(Void... params) {
			all = new AllLostItemsQuery();
			return all.getAll();
		}

		protected void onPostExecute(SimpleQueryResult sres){
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
			}

			if (sres == SimpleQueryResult.OK){
				items = all.getList();
				adapter = new ArrayAdapter<LostItem>(AllLostItemsListActivity.this,android.R.layout.simple_list_item_1, items);
				setListAdapter(adapter);
			} else {
				popUp(x);
			}
		}
	}

	public void onListItemClick( ListView parent, View v, int position, long id)
	{ 
		popUp(items[position].getName().toString(),items[position]);
	}

	public void popUp(CharSequence name,LostItem a){
		String present = "";
		present += "Reward is: "+ a.getReward() + "\n Category: " + a.getCategory() + " \n Date Lost: " + a.getDateEntered().serialize()
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

