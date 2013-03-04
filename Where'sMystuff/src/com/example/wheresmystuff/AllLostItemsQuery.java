

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AllLostItemsQuery {
	private LostItem[] items;


	public SimpleQueryResult getAll(){
		Document doc = null;
		SimpleQueryResult log = SimpleQueryResult.NETWORK_ERROR;

		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/alllostitems.php").timeout(15*1000).get();
		} catch (IOException e) {
			return log;
		}

		String[] array = doc.text().split("\\^");
		System.out.println(Arrays.toString(array));
		
		String[][] array2 = new String[array.length][7];
		for (int i = 0; i < array.length; i++)
			array2[i] = array[i].split("\\|");

		items = new LostItem[array.length];
		for (int i = 0; i < array.length; i++){
			
			System.out.println(Arrays.toString(array2[i]));
			items[i] = new LostItem(array2[i][1],
					array2[i][4],
					Utils.convertCategoryBack(array2[i][2]),
					array2[i][3], 
					new Date(Integer.valueOf(array2[i][5].split(",")[0]),
							Integer.valueOf(array2[i][5].split(",")[1]),
							Integer.valueOf(array2[i][5].split(",")[2])));
		}
		//if (!array[0].equals("ok"))
//			log = SimpleQueryResult.DB_ERROR;
//		else
			log = SimpleQueryResult.OK;

		return log;
	}
	
	public LostItem[] getList(){
		return items;
	}
}
