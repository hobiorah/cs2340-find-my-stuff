package com.example.wheresmystuff.view;
import com.example.wheresmystuff.util.*;
import com.example.wheresmystuff.util.Debug;


import java.util.ArrayList;

import com.example.wheresmystuff.R;
import com.example.wheresmystuff.controller.AllFoundItemsQuery;
import com.example.wheresmystuff.controller.AllItemsQueryResult;
import com.example.wheresmystuff.controller.AllLostItemsQuery;
import com.example.wheresmystuff.model.Category;
import com.example.wheresmystuff.model.FoundItem;
import com.example.wheresmystuff.model.Item;
import com.example.wheresmystuff.model.LostItem;
import com.example.wheresmystuff.view.AllFoundItemsListActivity.DatePickerFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchedForItemsFragmentActivity extends FragmentActivity {
	String itemName;
	FoundItem[] items;
	LostItem[] itemsL;	
	ArrayAdapter<Item> adapter;
	ListView list;
	ArrayList<Item> adapterItems = new ArrayList<Item>();
	ArrayList<Item> adapterItemsE = new ArrayList<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_searched_for_items);
		list = (ListView)findViewById(R.id.listA);
		new GetItemsAttemptTask().execute();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.searched_for_items, menu);
		return true;
	}

	public void filterCategory(Category category){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){		
			if (items[a].getCat() == category) adapterItems.add(items[a]);
		}
		for(int a = 0; a<itemsL.length; a++){		
			if (itemsL[a].getCat() == category) adapterItems.add(itemsL[a]);
		}

		adapter.notifyDataSetChanged();

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

	public void filterName(String name){
		adapterItems.clear();
		for(int a = 0; a<items.length; a++){
			if (items[a].getName().equalsIgnoreCase(name)) adapterItems.add(items[a]);	
		}
		for(int a = 0; a<itemsL.length; a++){
			if (itemsL[a].getName().equalsIgnoreCase(name)) adapterItems.add(itemsL[a]);	
		}

		adapter.notifyDataSetChanged();	
	}

	public void searchName(){
		View promptsView=null;
		LayoutInflater li = LayoutInflater.from(SearchedForItemsFragmentActivity.this);

		promptsView = li.inflate(R.layout.itemname_search, null);

		final EditText itemN = (EditText) promptsView.findViewById(R.id.itemNameSearch);

		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(promptsView);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("What's the Name of the Item?");

		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				itemName = itemN.getText().toString();

				filterName(itemName);


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

	public void popUp(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Search by What?");
		builder.setItems(R.array.filter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(which ==0){
					chooseCategory();

				}else{
					searchName();
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



	private class GetItemsAttemptTask extends AsyncTask<Void, Void, Void>{
		private ProgressDialog pd;

		AllFoundItemsQuery all;
		AllLostItemsQuery allL;
		AllItemsQueryResult allFoundOk;
		AllItemsQueryResult allLostOk;

		/**
		 * Method that shows progress bar as query is being executed
		 */
		protected void onPreExecute(){
			pd = ProgressDialog.show(SearchedForItemsFragmentActivity.this, null, "Getting Items...", true);
		}

		/**
		 * Creates object to execute query and then executes the query
		 * @return  the result of the query
		 */
		protected Void doInBackground(Void... params) {
			all = new AllFoundItemsQuery();
			allL = new AllLostItemsQuery();
			allFoundOk = all.getAll();
			allLostOk = allL.getAll();
			return null;
		}

		/**
		 * Receives the result of the query and checks if it was ok, if it was
		 * it gets all the items in the database and puts it into an array for the array adapter
		 * which then sets the array adapter for the activity to the list from the dataabse
		 */
		protected void onPostExecute(Void sres){
			pd.dismiss();
			String x = "defaults";

			Debug.log("ON POST EXECUTE");
			
			if (allFoundOk == AllItemsQueryResult.OK  
					&& allLostOk == AllItemsQueryResult.OK){
				Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
				items = all.getList();
				itemsL = allL.getList();
				adapterItems.clear();

				if (allFoundOk != AllItemsQueryResult.EMPTY)
					for(int a = 0; a<items.length; a++){
						adapterItems.add(items[a]);
					}

				if (allLostOk != AllItemsQueryResult.EMPTY)
					for(int b = 0;b<itemsL.length;b++){
						adapterItems.add(itemsL[b]);
					}

				if (items == null)
					Debug.log("ITEMS IS NULL");
				if (itemsL == null){
					Debug.log("ITEMSL IS NULL");
				}
				

				Log.d("ASDF", adapterItems.toString());

				adapter = new ArrayAdapter<Item>(SearchedForItemsFragmentActivity.this, android.R.layout.simple_list_item_1, adapterItems);
				list.setAdapter(adapter);
				//				popUp();
			} else {
				popUp("SHIT WENT WRONG YO");
			}


		}
	}

	/** Pop up dialog that shows user what went wrong with the query
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
