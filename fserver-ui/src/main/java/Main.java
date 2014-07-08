import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atanor.fserver.ui.domain.User;
import com.atanor.fserver.ui.service.UserManager;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"mvc-dispatcher-servlet.xml");

		UserManager userManager = (UserManager) ctx.getBean("userManagerImpl");

		// List<User> users = userManager.getUsers();
		//
		// System.out.println("\nUser list fetched!"
		// + "\nUser count: " + users.size()+"\n\r");
		//
		// for(User s : userManager.getUsers()){
		// System.out.println(s.getUsername()+"\n\r");
		// }
		//
		// System.out.println("\n\r");

		User user = new User();
		user.setUsername("test");
		user.setPassword("123456");
		user.setEnabled(1);
		user.setRole("ROLE_USER");
		
		userManager.insertUser(user);
		ctx.close();

	}

}
