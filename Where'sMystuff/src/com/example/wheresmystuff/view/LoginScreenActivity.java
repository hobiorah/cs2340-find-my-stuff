package com.example.wheresmystuff.view;

//import com.example.wheresmystuff.SignupPage.RegisterAttemptTask;

import com.example.wheresmystuff.R;
import com.example.wheresmystuff.model.Admin;
import com.example.wheresmystuff.model.LoginQuery;
import com.example.wheresmystuff.model.LoginResult;
import com.example.wheresmystuff.model.User;

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
/**
 * login screen so that user can log in and make a new user if necessary
 * @author HarryO
 *
 */
public class LoginScreenActivity extends Activity {

	
	protected TextView userName;
	protected TextView password;
	protected LoginQuery log;
	public User user;
	boolean move = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		userName = (TextView)this.findViewById(R.id.username_login);
		password = (TextView)this.findViewById(R.id.password_login); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}
	
	
	/**
	 * Runs when the login button is pressed which then calls the private class LoginAttemptTask 
	 * which attempts to login with the information provided from the user name and password textfields
	 * @param view
	 */
	public void login(View view){
		new LoginAttemptTask().execute();
		
	}
	
	private class LoginAttemptTask extends AsyncTask<Void, Void, LoginResult>{
		private ProgressDialog pd;

		protected void onPreExecute(){
			pd = ProgressDialog.show(LoginScreenActivity.this, null, "Logging in...", true);
		}

		protected LoginResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			log = new LoginQuery();
			String user =  userName.getText().toString();
			String pass =  password.getText().toString();
			return   log.login(user, pass);//.register(user, pass);

		}

		protected void onPostExecute(LoginResult params){
			pd.dismiss();

			String x = "Successfully logged in.";

			switch(params){
			case INVALID:
				x = "Invalid user/password combination";
				break;
			case DB_ERROR:
				x = "Database Error";
				break;
			case NETWORK_ERROR:
				x = "Network Error";
				break;
			case LOCKED:
				x = "This account has been locked";
				break;
			}


			if (params == LoginResult.ACCEPTED){
				move = true;
				user = log.getUser();
				
				Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();
				if(user instanceof Admin){
					Intent admin = new Intent(LoginScreenActivity.this, AdminScreenActivity.class);
					  // Start signuppage activity, using the Intent
					 startActivity(admin);
				}else{
					
					 Intent option = new Intent(LoginScreenActivity.this, OptionsActivity.class);
					  // Start signuppage activity, using the Intent
					 startActivity(option);
				}
				

				
			} else {
				popUp(x);
				//finish();
			}

		}
	}

	/**
	 * displays a pop up what went wrong with the attempted login
	 * @param problem CharSequence representation of the problem
	 */
	public void popUp(CharSequence problem){
		String again = "Please try again";
		String locked = "Please wait for an admin to unlock your account";
		if(problem.toString().equalsIgnoreCase("This account has been locked")){
			again = locked;
		}
		
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(again).setTitle(problem);

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
	 * runs when user hits the sign up button which transistions screen to the sign up activity
	 * @param view
	 */
	public void signUp(View view){
		 // Create the Intent
		  Intent register = new Intent(this, SignupPageActivity.class);
		  // Start signuppage activity, using the Intent
		  this.startActivity(register);

	}
	
	
	
}
