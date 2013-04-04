package com.example.wheresmystuff.controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.model.User;

/**
 * Deletes a user from the database which can only be done by a admin
 * @author HarryO
 *
 */
public class DeleteUserQuery {
	
	/**
	 * 
	 * deletes a user in the system
	 * @param user to be deleted
	 * @return if query was successful
	 */
		public SimpleQueryResult delete(User user){
			Document doc = null;
			SimpleQueryResult reg = SimpleQueryResult.NETWORK_ERROR;

			try {
				doc = Jsoup.connect("http://steve.node13.info/findmystuff/deleteuser.php")
						.data("user",user.getName())
						.timeout(15*1000)
						.get();
			} catch (IOException e) {
				return reg;
			}

			String[] array = doc.text().split(":");

			if (!array[0].equals("ok"))
				reg = SimpleQueryResult.DB_ERROR;
			else
				reg = SimpleQueryResult.OK;

			return reg;
		}
}
