import org.junit.Test;

import junit.framework.TestCase;

import android.test.*;


import com.example.wheresmystuff.controller.DeleteUserQuery;
import com.example.wheresmystuff.controller.LoginQuery;
import com.example.wheresmystuff.controller.LoginResult;
import com.example.wheresmystuff.controller.RegisterQuery;
import com.example.wheresmystuff.model.User;

import static org.junit.Assert.*;

public class wataruTest {
	RegisterQuery reger = new RegisterQuery();
	DeleteUserQuery deleter = new DeleteUserQuery();
	LoginQuery login = new LoginQuery();
	
	
	@Test
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
}
