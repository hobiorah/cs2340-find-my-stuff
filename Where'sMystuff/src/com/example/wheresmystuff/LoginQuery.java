package com.example.wheresmystuff;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoginQuery {
	public LoginResult register(String username, String hash){
		Document doc = null;
		LoginResult reg = LoginResult.NETWORK_ERROR;
		
		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/createuser.php").data("name", username).data("hash", hash).get();
		} catch (IOException e) {
			return reg;
		}
		
		String[] array = doc.text().split(":");

		if (!array[0].equals("valid"))
			reg = LoginResult.INVALID;
		else if (!array[1].equals("success"))
			reg = LoginResult.DB_ERROR;
		else if (!array[2].equals("okuser"))
			reg = LoginResult.INVALID;
		else if (!array[3].equals("okpass"))
			reg = LoginResult.INVALID;
		else
			reg = LoginResult.ACCEPTED;
		
		return reg;
	}
}
