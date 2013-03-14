package com.example.wheresmystuff;
import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Object that queries server, attempting to register a user
 * @author Steven Han
 */
public class CreateAdminQuery {


	/**
	 * Queries server and attempts to register
	 * @return returns the result for whether the user was registered
	 */
	public RegisterResult register(String username, String hash, String email){
		Document doc = null;
		RegisterResult reg = RegisterResult.NETWORK_ERROR;

		if (!username.matches("\\w+"))
			return RegisterResult.INVALID_USERNAME;
		else if (!hash.matches("\\w+"))
			return RegisterResult.INVALID_PASS;
		else if (!email.matches("([\\w-\\.]+)@((?:[\\w]+\\.)+)([a-zA-Z]{2,4})"))
			return RegisterResult.INVALID_EMAIL;

		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/createadmin.php").data("name", username).data("hash", hash).data("email", email).timeout(15*1000).get();
		} catch (IOException e) {
			return reg;
		}

		String[] array = doc.text().split(":");

		if (!array[0].equals("okname"))
			reg = RegisterResult.INVALID_USERNAME;
		else if (!array[1].equals("okpass"))
			reg = RegisterResult.INVALID_PASS;
		else if (!array[2].equals("okemail"))
			reg = RegisterResult.INVALID_EMAIL;
		else if (!array[3].equals("success"))
			reg = RegisterResult.DB_ERROR;
		else if (array[4].equals("alreadyexists"))
			reg = RegisterResult.USER_ALREADY_EXISTS;
		else if (!array[5].equals("success"))
			reg = RegisterResult.DB_ERROR;
		else
			reg = RegisterResult.ACCEPTED;

		return reg;
	}
}
