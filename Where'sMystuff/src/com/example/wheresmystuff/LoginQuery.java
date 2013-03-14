package com.example.wheresmystuff;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoginQuery {
	
	protected User user;
	
	/**
	 * Queries server and attempts to login
	 * @return returns the result for whether the user was logged in
	 */
	public LoginResult login(String username, String hash){
		Document doc = null;
		LoginResult log = LoginResult.NETWORK_ERROR;
		
		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/login.php").data("name", username).data("hash", hash).timeout(15*1000).get();
		} catch (IOException e) {
			return log;
		}
		
		String[] array = doc.text().split(":");

		if (!array[0].equals("valid"))
			log = LoginResult.INVALID;
		else if (!array[1].equals("success"))
			log = LoginResult.DB_ERROR;
		else if (!array[2].equals("okuser"))
			log = LoginResult.INVALID;
		else if (!array[4].equals("ok"))
			log = LoginResult.LOCKED;
		else if (!array[3].equals("okpass"))
			log = LoginResult.INVALID;
		else{
			log = LoginResult.ACCEPTED;
			if (array[5].equalsIgnoreCase("admin"))
				user = new Admin(username, hash,"d");
			else
				user = new User(username, hash,"d");
			
		}
		
		return log;
	}
	
	public User getUser(){
		return user;
	}
}
