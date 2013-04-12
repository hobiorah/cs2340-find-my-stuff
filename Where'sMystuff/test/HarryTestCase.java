import static org.junit.Assert.*;

import org.junit.Test;

import com.example.wheresmystuff.controller.CreateLostItemQuery;
import com.example.wheresmystuff.controller.LoginQuery;
import com.example.wheresmystuff.controller.LoginResult;
import com.example.wheresmystuff.controller.RegisterQuery;
import com.example.wheresmystuff.controller.RegisterResult;
import com.example.wheresmystuff.controller.SimpleQueryResult;
import com.example.wheresmystuff.model.Category;
import com.example.wheresmystuff.model.Date;
import com.example.wheresmystuff.model.Location;
import com.example.wheresmystuff.model.LostItem;
import com.example.wheresmystuff.model.User;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.jsoup.nodes.Document;

import com.example.wheresmystuff.controller.*;
import com.example.wheresmystuff.model.*;

public class HarryTestCase {
	
	RegisterQuery register = new RegisterQuery();
	LoginQuery login = new LoginQuery();
	//CreateLostItemQuery create = new CreateLostItemQuery();
	
	User newuser = new User("Junit","test","case@gmail.com");
	@Test
	
	public void testValidSignUp() {
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
