package com.example.wheresmystuff.controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.model.User;
/**
 * Attempts to run qeury to make a user an Admin
 * @author HarryO
 *
 */
public class PromoteToAdminQuery {
	
	/**
	 * makes a regular user an admin
	 * @param user
	 * @return if the query was successful
	 */
		public SimpleQueryResult create(User user){
			Document doc = null;
			SimpleQueryResult reg = SimpleQueryResult.NETWORK_ERROR;

			try {
				doc = Jsoup.connect("http://steve.node13.info/findmystuff/makeadmin.php")
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
