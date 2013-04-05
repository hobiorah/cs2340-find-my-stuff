package com.example.wheresmystuff.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.model.Date;
import com.example.wheresmystuff.model.Location;
import com.example.wheresmystuff.model.LostItem;
import com.example.wheresmystuff.util.Debug;

import android.util.Log;

/**
 * Attempts to get all lost items in the database
 *
 */


public class AllLostItemsQuery {
	private LostItem[] items;

	/**
	 * Query to get all lost items in the database
	 * @return result of whether it worked or not
	 */
	public AllItemsQueryResult getAll(){
		Document doc = null;
		AllItemsQueryResult log = AllItemsQueryResult.NETWORK_ERROR;

		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/alllostitems.php").timeout(15*1000).get();
		} catch (IOException e) {
			return log;
		}


		String[] array = doc.text().split("\\^");{

			Debug.log("" + Arrays.toString(array));
			
			String[][] array2 = new String[array.length][7];
			for (int i = 0; i < array.length; i++)
				array2[i] = array[i].split("\\|");

			items = new LostItem[array.length];

//			Log.d("ASDF", "" + array.length);

			for (int i = 0; i < array.length; i++){
				items[i] = new LostItem(array2[i][1],
						array2[i][4],
						Utils.convertCategoryBack(array2[i][2]),
						array2[i][3], 
						new Date(Integer.valueOf(array2[i][5].split("/")[0]),
								Integer.valueOf(array2[i][5].split("/")[1]),
								Integer.valueOf(array2[i][5].split("/")[2])),
								new Location(array2[i][6].split(",")[0],array2[i][6].split(",")[1]));
			}

		}
		log = AllItemsQueryResult.OK;

		return log;
	}

	/**
	 * Gets all the items in the database
	 * @return items in the database in an array of type LostItem 
	 */
	public LostItem[] getList(){
		return items;
	}
}