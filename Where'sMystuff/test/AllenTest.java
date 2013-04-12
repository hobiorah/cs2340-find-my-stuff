import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.controller.*;
import com.example.wheresmystuff.model.*;


public class AllenTest {

	@Test
	/**
	 * @author Allen Tsai
	 */
	public void promoteToAdminTest() {
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
	RegisterQuery reger = new RegisterQuery();
	DeleteUserQuery deleter = new DeleteUserQuery();
	LoginQuery login = new LoginQuery();
	
	
	@Test
	/**
	 * @author Wataru
	 */
	public void testLoginValidUser() {
		User user = new User("name","password","hey@email.com");
		reger.register(user);
		
		assertEquals(LoginResult.ACCEPTED, login.login("name", "password"));
		
		deleter.delete(user);
	}
	
	@Test
	public void testLoginInvalidUser() {
		User user = new User("name","password","hey@email.com");
		reger.register(user);
		
		assertEquals(LoginResult.INVALID, login.login("namer", "password"));

		
		deleter.delete(user);	
	}
	@Test
	public void testLoginInvalidPass() {
		User user = new User("name","password","hey@email.com");
		reger.register(user);
		
		assertEquals(LoginResult.INVALID, login.login("name", "passwords"));

		
		deleter.delete(user);	}
	@Test
	public void testLoginLocked() {
		User user = new User("name","password","hey@email.com");
		reger.register(user);
		
		login.login("name", "passwords");
		login.login("name", "passwords");
		login.login("name", "passwords");

		assertEquals(LoginResult.LOCKED, login.login("name", "password"));
		
		deleter.delete(user);	}
	
	@Test
	public void testLogInValidBoth() {
		User user = new User("name","password","hey@email.com");
		reger.register(user);
		
		assertEquals(LoginResult.INVALID, login.login("names", "passwords"));
		
		deleter.delete(user);	}
	
	
		@Test
		/**
		 * @author Harry
		 */
		public void testCreateLostItem() {
			CreateLostItemQuery create = new CreateLostItemQuery();
			
			String name ="JunitDemo";
			String test = "testTA";
			String description = "Good Grade :)";
			Date date = new  Date(4, 7, 2013);
			//register.register(newuser);
			//this better work
			LostItem lost = new LostItem(name,description, Category.HEIRLOOMS, test ,date, new Location("Atlanta","Georgia") );
			assertEquals(SimpleQueryResult.OK,create.create(lost));
			
		}

}
