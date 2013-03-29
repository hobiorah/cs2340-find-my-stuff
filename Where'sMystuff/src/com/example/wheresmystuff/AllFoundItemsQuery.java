package com.example.wheresmystuff;


import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.util.Log;

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
	public AllFoundItemsQueryResult getAll(){
		Document doc = null;
		AllFoundItemsQueryResult log = AllFoundItemsQueryResult.NETWORK_ERROR;

		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/viewfounditems.php").timeout(15*1000).get();
		} catch (IOException e) {
			Log.d("MyApp","I am here");
			return log;
		}

		if (doc.text().contains("^")){
			String[] array = doc.text().split("\\^");{

				String[][] array2 = new String[array.length][7];
				for (int i = 0; i < array.length; i++)
					array2[i] = array[i].split("\\|");

				items = new FoundItem[array.length];

				Log.d("ASDF", "" + array.length);

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

			}
			log = AllFoundItemsQueryResult.OK;
		} else {
			log = AllFoundItemsQueryResult.EMPTY;
		}
		

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
