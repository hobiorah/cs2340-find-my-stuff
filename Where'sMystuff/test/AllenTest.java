import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.controller.*;
import com.example.wheresmystuff.model.*;


public class AllenTest {

	@Test
	public void adminTest() {
		String username = "Allen";
		String hash = "hash";
		Document doc = null;
		
		User user = new User(username, hash, "atsai@gatech.edu");
		new RegisterQuery().register(user);
		assertTrue(new PromoteToAdminQuery().create(user) == SimpleQueryResult.OK);
		try {
			doc = Jsoup.connect("http://steve.node13.info/findmystuff/login.php").data("name", username).data("hash", hash).timeout(15*1000).get();
		} catch (IOException e) {
		}		
		String[] array = doc.text().split(":");
		assertTrue(array[5].equalsIgnoreCase("admin"));
		new DeleteUserQuery().delete(user);
	}

}
