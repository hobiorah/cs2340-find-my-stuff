package com.example.wheresmystuff;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoginQuery {
	public LoginResult login(String username, String hash){
		Document doc = null;
		LoginResult log = LoginResult.NETWORK_ERROR;
		
		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/login.php").data("name", username).data("hash", hash).get();
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
		else if (!array[3].equals("okpass"))
			log = LoginResult.INVALID;
		else
			log = LoginResult.ACCEPTED;
		
		return log;
	}
}
