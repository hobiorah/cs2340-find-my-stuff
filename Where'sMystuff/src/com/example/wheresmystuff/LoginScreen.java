package com.example.wheresmystuff;

//import com.example.wheresmystuff.SignupPage.RegisterAttemptTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginScreen extends Activity {

	
	TextView userName;
	TextView password;
	LoginQuery log;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		Button login = (Button)this.findViewById(R.id.login_button);
		//login.setOnClickListener(new Login());
		Button signUp = (Button)this.findViewById(R.id.signup_button);
		//signUp.setOnClickListener(new SignUp());
		userName = (TextView)this.findViewById(R.id.username_login);
		password = (TextView)this.findViewById(R.id.password_login); 
		log = new LoginQuery();
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
		new LoginAttemptTask().execute();
	}
	
	private class LoginAttemptTask extends AsyncTask<Void, Void, LoginResult>{
		private ProgressDialog pd;

		protected void onPreExecute(){
			pd = ProgressDialog.show(LoginScreen.this, null, "Logging in...", true);
		}

		protected LoginResult doInBackground(Void... params) {
			// TODO Auto-generated method stub

			String user =  userName.getText().toString();
			String pass =  password.getText().toString();
			return   log.login(user, pass);//.register(user, pass);

		}

		protected void onPostExecute(LoginResult params){
			pd.dismiss();

			String x = "User created.";

			switch(params){
			case INVALID:
				x = "Invalid Username";
				break;
			case DB_ERROR:
				x = "Database Error";
				break;
			case NETWORK_ERROR:
				x = "Network Error";
				break;
			}


			if (params == LoginResult.ACCEPTED){
				Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();
				
			} else {
				popUp(x);
				//finish();
			}

		}
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
	
	
	public void signUp(View view){
		 // Create the Intent
		  Intent register = new Intent(this, SignupPage.class);
		  // Start the other Activity, using the Intent
		  this.startActivity(register);

	}
	
	
	
}
