package com.example.wheresmystuff.controller;


import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.model.Date;
import com.example.wheresmystuff.model.FoundItem;
import com.example.wheresmystuff.model.Location;
import com.example.wheresmystuff.model.LostItem;
import com.example.wheresmystuff.util.Debug;

/**
 * Attempts to get all lost items in the database
 *
 */


public class AllFoundItemsQuery {
	private FoundItem[] items;

	/**
	 * Query to get all lost items in the database
	 * @return result of whether it worked or not
	 */
	public AllItemsQueryResult getAll(){
		Document doc = null;
		AllItemsQueryResult log = AllItemsQueryResult.NETWORK_ERROR;

		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/allfounditems.php").timeout(15*1000).get();
		} catch (IOException e) {
			return log;
		}


		if (doc.text().length() > 2){
			String[] array = doc.text().split("\\^");


			Debug.log("" + Arrays.toString(array));

			String[][] array2 = new String[array.length][7];
			for (int i = 0; i < array.length; i++)
				array2[i] = array[i].split("\\|");

			items = new FoundItem[array.length];

			//			Log.d("ASDF", "" + array.length);
			Debug.log("" + array.length);

			for (int i = 0; i < array.length; i++){
				items[i] = new FoundItem(array2[i][1],
						array2[i][4],
						Utils.convertCategoryBack(array2[i][2]),
						array2[i][3], 
						new Date(Integer.valueOf(array2[i][5].split("/")[0]),
								Integer.valueOf(array2[i][5].split("/")[1]),
								Integer.valueOf(array2[i][5].split("/")[2])),
								new Location(array2[i][6].split(",")[0],array2[i][6].split(",")[1]));

			}
		} else{
			items = new FoundItem[0];
		}


		log = AllItemsQueryResult.OK;

		return log;
	}

	/**
	 * Gets all the items in the database
	 * @return items in the database in an array of type LostItem 
	 */
	public FoundItem[] getList(){
		return items;
	}
}
