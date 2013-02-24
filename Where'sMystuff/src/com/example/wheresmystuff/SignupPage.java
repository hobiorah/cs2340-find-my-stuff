package com.example.wheresmystuff;


import java.io.IOException;




import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;




public class SignupPage extends Activity {

	TextView userName;
	TextView password;
	RegisterQuery reg;
//	Outcome outcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_page);
		//Button register = (Button)this.findViewById(R.id.register_button);
		//register.setOnClickListener(new Login());
		 userName = (TextView)this.findViewById(R.id.userName_register);
		 password = (TextView)this.findViewById(R.id.password_register);
		 reg = new RegisterQuery();
		// outcome = new Outcome();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}
	
	public void register(View view){	
		String user =  userName.getText().toString();
		String pass =  password.getText().toString();
		RegisterResult result = reg.register(user, pass);
		
		String x = "";

		
			
			
			//case NETWORK_ERROR:
				//x = "network error";
				//break;
			}
		
	
	
	public void popUp(CharSequence problem){
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage("Please try again").setTitle(problem);

		// Add the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });
		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
	}

}
