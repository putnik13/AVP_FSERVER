import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atanor.fserver.ui.domain.RecordStatus;

public class Main {

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"mvc-dispatcher-servlet.xml");

		// UserManager userManager = (UserManager)
		// ctx.getBean("userManagerImpl");

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

		/*
		 * User test
		 */
		// User user = new User();
		// user.setUsername("test");
		// user.setPassword("123456");
		// user.setEnabled(1);
		// user.setRole("ROLE_USER");
		//
		// userManager.insertUser(user);
		// ctx.close();

		/*
		 * RecordStatus test;
		 */

		// RecordStatus recStatus = new RecordStatus();
		// recStatus.setStatus("RECORDING");
		//
		// RecordStatusManager rsm = (RecordStatusManager)
		// ctx.getBean("recordStatusManagerImpl");
		// // rsm.setStatus(recStatus);
		// System.out.println(rsm.showStatus().getStatus());
		// rsm.removeStatus();
		// ctx.close();

//		System.out.println(doFserverRequest("api/startRecording"));
		
		try{
			if(doFserverRequest("api/startRecording").toString().toLowerCase().contains("ok")){
				System.out.println("ok");
			}else{
				System.out.println("not ok");
			}
		}catch(NullPointerException e){
		}

	}

	private static StringBuilder doFserverRequest(String url)
			throws ClientProtocolException, IOException {
		// ClassPathXmlApplicationContext ctx = new
		// ClassPathXmlApplicationContext(
		// new String[] { "fserver-url.xml" });
		//
		// GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getUrl");
		//
		// String mainUrl = getUrl.getUrl();

		StringBuilder responseText = new StringBuilder();

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(
				"http://10.10.10.15:8080/fserver-0.0.1-SNAPSHOT/api/startRecording");

		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent()));
			String line = null;

			while ((line = reader.readLine()) != null) {
				responseText.append(line);
			}
		} catch (IOException e) {
		} catch (Exception e) {
		}

		httpclient.getConnectionManager().shutdown();

		// ctx.close();

		return responseText;
	}

}
