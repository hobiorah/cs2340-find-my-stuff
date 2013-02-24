package com.example.wheresmystuff;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		Button login = (Button)this.findViewById(R.id.login_button);
		//login.setOnClickListener(new Login());
		Button signUp = (Button)this.findViewById(R.id.signup_button);
		//signUp.setOnClickListener(new SignUp());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}
	
	
	/**
	 * Runs when the login 
	 * @param view
	 */
	public void login(View view){
		
	}
	
	public void signUp(View view){
		 // Create the Intent
		  Intent register = new Intent(this, SignupPage.class);
		  // Start the other Activity, using the Intent
		  this.startActivity(register);

	}
	
	
	
}
