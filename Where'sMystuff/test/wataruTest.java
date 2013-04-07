import junit.framework.TestCase;
import org.junit.Test;

import com.example.wheresmystuff.controller.DeleteUserQuery;
import com.example.wheresmystuff.controller.RegisterQuery;

import static org.junit.Assert.*;

public class wataruTest {
	RegisterQuery reger = new RegisterQuery();
	DeleteUserQuery deleter = new DeleteUserQuery();
	
	
	@Test
	public void testLoginValidUser() {
		reger.register("name","password","hey@email.com");
		assertEquals(2,edgeList.size());
		
		
	}
	
	@Test
	public void testLoginInvalidUser() {
		assertEquals(2, edgeList.size());
	}
	@Test
	public void testLoginInvalidPass() {
		assertEquals(2, edgeList.size());
	}
	@Test
	public void testLoginValidPass() {
		assertEquals(2, edgeList.size());
	}
	
	@Test
	public void testLogInValidUser() {
		assertEquals(2, edgeList.size());
	}
}
