import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.wheresmystuff.controller.DeleteUserQuery;
import com.example.wheresmystuff.controller.LoginQuery;
import com.example.wheresmystuff.controller.RegisterQuery;
import com.example.wheresmystuff.controller.SimpleQueryResult;
import com.example.wheresmystuff.controller.UnlockUserQuery;
import com.example.wheresmystuff.model.User;

/**
 * 
 * @author Zhiyuan "Jerry" Lin
 *
 */
public class ZhiyuanTest extends TestCase {
	User user = new User("zhiyuan", "zhiyuan", "zhiyuan@zhiyuan.edu");
	
	@Before
	public void setUp(){
		//register user
		RegisterQuery register = new RegisterQuery();
		register.register(user);
		
		//lock the user
		LoginQuery login = new LoginQuery();
		login.login("zhiyuan", "WrongPassword");
		login.login("zhiyuan", "WrongPassword");
		login.login("zhiyuan", "WrongPassword");
	}
	
	@Test
	public void testUnlockUser(){
		UnlockUserQuery unlock = new UnlockUserQuery();
		assertEquals(unlock.unlock(user),SimpleQueryResult.OK);
	}

	@After
	public void cleanUp(){
		DeleteUserQuery deleteUser = new DeleteUserQuery();
		deleteUser.delete(user);
	}
}
