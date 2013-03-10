package com.example.wheresmystuff;


import java.io.IOException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;



/**
 * this signup screen for the user to make a new account
 * @author HarryO
 *
 */
public class SignupPageActivity extends Activity {

	TextView userName;
	TextView password;
	TextView email;
	RegisterQuery reg;
	
	/**
	 * executes query to register a new user
	 * @author HarryO
	 *
	 */
	private class RegisterAttemptTask extends AsyncTask<Void, Void, RegisterResult>{
		private ProgressDialog pd;

		protected void onPreExecute(){
			pd = ProgressDialog.show(SignupPageActivity.this, null, "Creating user...", true);
		}

		protected RegisterResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			reg = new RegisterQuery();
			String user =  userName.getText().toString();
			String pass =  password.getText().toString();
			String em = email.getText().toString();
			return reg.register(user, pass, em);

		}

		protected void onPostExecute(RegisterResult params){
			pd.dismiss();

			String x = "User created.";

			switch(params){
			case INVALID_USERNAME:
				x = "Invalid Username";
				break;
			case INVALID_PASS:
				x = "Invalid Password";
				break;
			case DB_ERROR:
				x = "Database Error";
				break;
			case USER_ALREADY_EXISTS:
				x = "User already exists";
				break;
			case NETWORK_ERROR:
				x = "Network Error";
				break;
			case INVALID_EMAIL:
				x = "Invalid email";
				break;
			}


			if (params == RegisterResult.ACCEPTED){
				Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();
				finish();
			} else {
				popUp(x);
			}

		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_page);
		userName = (TextView)this.findViewById(R.id.userName_register);
		password = (TextView)this.findViewById(R.id.password_register);
		email = (TextView)this.findViewById(R.id.email_register);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}
	
	/**
	 * runs when user hits register button which takes information from username and password field and attempts to make
	 * new user in the database through the RegisterAttemptTask class
	 * @param view
	 */
	public void register(View view){	
		new RegisterAttemptTask().execute();
		
	}


	/**
	 * pop up what what went wrong with registering new account
	 * @param problem CharSequence representation of the problem
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
