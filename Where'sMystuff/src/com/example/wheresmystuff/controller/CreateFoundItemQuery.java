package com.example.wheresmystuff.controller;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.model.FoundItem;

public class CreateFoundItemQuery {
	/**
	 * Queries server and attempts to sumbit a found item
	 * @return returns the result for whether the item was submitted succesfully
	 */
	public SimpleQueryResult create(FoundItem item){
		Document doc = null;
		SimpleQueryResult reg = SimpleQueryResult.NETWORK_ERROR;

		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/createfounditem.php")
					.data("name", item.getName())
					.data("category", Utils.convertCategory(item.getCategory()).toLowerCase())
					.data("reward", item.getReward())
					.data("description", item.getDescription())
					.data("date", item.getDateEntered().serialize())
					.data("location", item.getLocation().serialize())
					//.data("location", item.getLocation().serialize())
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