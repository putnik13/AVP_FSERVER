import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atanor.fserver.ui.beans.UserManager;
import com.atanor.fserver.ui.model.User;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"mvc-dispatcher-servlet.xml");

		UserManager userManager = (UserManager) ctx.getBean("userManagerImpl");

//		 List<User> users = userManager.getUsers();
//		
//		 System.out.println("\nUser list fetched!"
//		 + "\nUser count: " + users.size()+"\n");

		System.out.println(userManager.getUser("admin"));
		
		ctx.close();
	}

}
