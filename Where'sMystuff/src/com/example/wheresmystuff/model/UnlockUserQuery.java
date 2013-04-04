package com.example.wheresmystuff.model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Query to unlock a user who has been locked. Can only be done by an admind
 * @author HarryO
 *
 */
public class UnlockUserQuery {
	
	/**
	 * Runs query to unlock a user
	 * @param user to unlock
	 * @return whether the query was successful or not
	 */
		public SimpleQueryResult unlock(User user){
			Document doc = null;
			SimpleQueryResult reg = SimpleQueryResult.NETWORK_ERROR;

			try {
				doc = Jsoup.connect("http://steve.node13.info/findmystuff/unlockuser.php")
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
