package com.example.wheresmystuff;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DeleteUserQuery {
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
