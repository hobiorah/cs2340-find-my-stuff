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
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter);
		  }
		} 

