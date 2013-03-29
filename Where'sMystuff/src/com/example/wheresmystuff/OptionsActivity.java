package com.example.wheresmystuff;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * Screen shown after user logs in providing them the ability to submit a lost item
 * @author HarryO
 *
 */
public class OptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}
	
	/**
	 * Takes user to screen to submit a lost item
	 * @param view
	 */
	public void newLostItem(View view){
		Intent lostItem = new Intent(this, CreateLostItemFragmentActivity.class);
		  // Start signuppage activity, using the Intent
		  this.startActivity(lostItem);
	}
	
	public void newFoundItem(View view){
		Intent foundItem = new Intent(this, CreateFoundItemFragmentActivity.class);
		  // Start signuppage activity, using the Intent
		  this.startActivity(foundItem);
	}

}
