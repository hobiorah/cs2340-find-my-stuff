package com.example.wheresmystuff;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * the screeen an admin of the app goes to
 * @author HarryO
 *
 */
public class AdminScreenActivity extends Activity {

	final int delete = 100;
	final int create = 200;
	
	String adminUser;
	String adminPass;
	String adminEmail;
	String usernameDelete;
	
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
	
	/**
	 * deletes a user by username
	 * @param view
	 */
	public void deleteUser(View view){
		
		popUp("Delete User", delete);
	}
	
	public void unlockUsers(View view){
		
		//unlockusers query
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
        case create:   promptsView = li.inflate(R.layout.delete_user_prompt, null);
                 break;
			
		}
		

		final EditText aUser = (EditText) promptsView.findViewById(R.id.admin_user);
		final EditText aPass = (EditText) promptsView.findViewById(R.id.admin_pass);
		final EditText aEmail = (EditText) promptsView.findViewById(R.id.admin_email);
		final EditText userDelete = (EditText) promptsView.findViewById(R.id.username_delete);
		 
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
				//	createAdminQuery
					
				}else{
					usernameDelete = userDelete.getText().toString();
					//delete user
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
}
