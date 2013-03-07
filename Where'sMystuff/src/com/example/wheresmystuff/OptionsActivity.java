package com.example.wheresmystuff;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

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
	
	public void newLostItem(View view){
		Intent lostItem = new Intent(this, CreateLostItemFragmentActivity.class);
		  // Start signuppage activity, using the Intent
		  this.startActivity(lostItem);
	}

}
