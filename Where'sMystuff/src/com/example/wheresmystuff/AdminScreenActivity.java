package com.example.wheresmystuff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * the screeen an admin of the app goes to
 * @author HarryO
 *
 */
public class AdminScreenActivity extends Activity {

	final int delete = 100;
	final int create = 200;
	User user;
	String adminUser;
	String adminPass;
	String adminEmail;
	String usernameDelete;
	RegisterQuery reg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_screen, menu);
		return true;
	}

	/**
	 * creates a new admin
	 * @param view
	 */
	public void createAdmin(View view){
		
		popUp("Create Admin", create);
	}
	
	private class RegisterAttemptTask extends AsyncTask<Void, Void, RegisterResult>{
		private ProgressDialog pd;

		protected void onPreExecute(){
			pd = ProgressDialog.show(AdminScreenActivity.this, null, "Creating admin...", true);
		}

		protected RegisterResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			reg = new RegisterQuery();
			String userN =  adminUser;
			String pass =  adminPass;
			String em = adminEmail;
			return reg.register(userN, pass, em);

		}

		protected void onPostExecute(RegisterResult params){
			pd.dismiss();

			String x = "Admin created.";

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
				//finish();
			} else {
				popUp(x);
			}

		}
	}

	
	/**
	 * deletes a user by username
	 * @param view
	 */
	public void deleteUser(View view){
		
		popUp("Delete User", delete);
	}
	
	private class DeleteUserTask extends AsyncTask<Void, Void, SimpleQueryResult>{
		//private ProgressDialog pd;

		protected void onPreExecute(){
			//pd = ProgressDialog.show(AdminScreenActivity.this, null, "Deleting users...", true);
		}

		protected SimpleQueryResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			DeleteUserQuery deleteU = new DeleteUserQuery();
			
			return   deleteU.delete(user);//.register(user, pass);

		}

		protected void onPostExecute(SimpleQueryResult params){
		//	pd.dismiss();

			String x = "Successfully logged in.";

			switch(params){
			case DB_ERROR:
				x = "User isn't in System";
				break;
			case NETWORK_ERROR:
				x = "User isn't in System";
				break;
			default:
				break;
			}


			if (params == SimpleQueryResult.OK){
				x = "Deleted user.";
				
				Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();
				
				

				
			} else {
				popUp(x);
			}

		}
	}
	
	
	
	public void unlockUsers(View view){
		popUp("Unlock User",0);
		//new UnlockUserTask().execute();
	}
	
	
	private class UnlockUserTask extends AsyncTask<Void, Void, SimpleQueryResult>{
		private ProgressDialog pd;

		protected void onPreExecute(){
			pd = ProgressDialog.show(AdminScreenActivity.this, null, "Unlocking users...", true);
		}

		protected SimpleQueryResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			UnlockUserQuery unlock = new UnlockUserQuery();
			
			return   unlock.unlock(user);//.register(user, pass);

		}

		protected void onPostExecute(SimpleQueryResult params){
			pd.dismiss();

			String x = "Successfully logged in.";

			switch(params){
			case DB_ERROR:
				x = "User isn't in System";
				break;
			case NETWORK_ERROR:
				x= "User isn't in System";
				break;
			default:
				break;
			}


			if (params == SimpleQueryResult.OK){
				x = "Deleted user.";
				
				Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show();

				
			} else {
				popUp(x);
				
			}

		}
	}
	
	/**
	 * pop up to show textbox to delete a user or create a new admin
	 * @param what
	 * @param which
	 */
	public void popUp(CharSequence what, int which){
		final int make = which;//may have to fix this
		View promptsView=null;
		LayoutInflater li = LayoutInflater.from(AdminScreenActivity.this);
		switch (which) {
        case delete:  promptsView = li.inflate(R.layout.delete_user_prompt, null);
                 break;
        case create:   promptsView = li.inflate(R.layout.create_admin, null);
                 break;
         default:	promptsView = li.inflate(R.layout.unlock_user, null);
        		break;
			
		}
		

		final EditText aUser = (EditText) promptsView.findViewById(R.id.admin_user);
		final EditText aPass = (EditText) promptsView.findViewById(R.id.admin_pass);
		final EditText aEmail = (EditText) promptsView.findViewById(R.id.admin_email);
		final EditText userDelete = (EditText) promptsView.findViewById(R.id.username_delete);
		final EditText userUnlock =  (EditText) promptsView.findViewById(R.id.user_unlock);
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(promptsView);
		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle(what);

		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if(make == create){
					adminUser = aUser.getText().toString();
					adminPass = aPass.getText().toString();
					adminEmail = aEmail.getText().toString();
					new RegisterAttemptTask().execute();
					
				}else if(make == delete){
					user = new User(userDelete.getText().toString(),"l");
					new DeleteUserTask().execute();
				}else{
					user = new User(userUnlock.getText().toString(),"l");
					new UnlockUserTask().execute();
				}
				
			
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
