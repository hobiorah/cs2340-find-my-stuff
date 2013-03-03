package com.example.wheresmystuff;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AllLostItems extends ListActivity{

		  public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2" };
		    Item[] items = {new Item("wow", "so", "annoying", "yo", "man")};
		    ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this,
		        android.R.layout.simple_list_item_1, items);
		    setListAdapter(adapter);
		  }
		  
		  public void onListItemClick( ListView parent, View v, int position, long id)
		  { 
			  String a = "better " +  position;
		  Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
		  }
		} 

