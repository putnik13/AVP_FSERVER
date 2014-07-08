package com.atanor.fserver.ui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atanor.fserver.ui.control.beans.GetHttpUrl;

@Controller
@RequestMapping("/system")
public class SysSettingController {
	private static Logger LOGGER = Logger.getLogger(SysSettingController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPage(Model model) throws ClientProtocolException, ParseException, IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getRestAdminUrl");

		model.addAttribute("url", getUrl.getUrl());

		ctx.close();

		model.addAttribute("menuItem", "system");
		
		String[] networkParameters = jsonParse("value", doGetFserverRequest("request/network/show")).split(";");
		model.addAttribute("ip", networkParameters[0]);
		model.addAttribute("netmask", networkParameters[1]);
		model.addAttribute("gateway", networkParameters[2]);
		
		LOGGER.info("Network Param:"+networkParameters[0]+";"+networkParameters[1]+";"+networkParameters[2]);
		
		return "control";
	}
	
	@RequestMapping(params = {"ip","netmask","gateway"}, method = RequestMethod.GET)
	public String setIpAddress(@RequestParam("ip") String ip,
			@RequestParam("netmask") String netmask,
			@RequestParam("gateway") String gateway,
			Model model) throws ClientProtocolException, ParseException, IOException {
		
		/**
		 * Set network params
		 */
		String statusResponse;
		if(gateway.trim().equals("")){
			statusResponse = jsonParse("status", doGetFserverRequest("request/network/"+ip.trim()+"/"+netmask.trim()));
		}else{
			statusResponse = jsonParse("status", doGetFserverRequest("request/network/"+ip.trim()+"/"+netmask.trim()+"/"+gateway.trim()));
		}
		
		/**
		 * Commit network changes
		 */
		doGetFserverRequest("request/network/commit");
		
//		if(statusResponse.equals("true")){
//			model.addAttribute("statusResponse", "true");
//		}else if(statusResponse.equals("false")){
//			model.addAttribute("statusResponse", "false");
//		}
		
		LOGGER.info("POST Request:"+ip+";"+netmask+";"+gateway);
//		model.addAttribute("ip",ip);
		return "redirect:/system";
	}
	
	@RequestMapping(params = "apply", method = RequestMethod.GET)
	public String getApplyRedirect(HttpServletRequest request) {
		try {
			doGetFserverRequest("request/network/commit");
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/system";
	}
	
	private String doGetFserverRequest(String url) throws ClientProtocolException,
			IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getRestAdminUrl");

		String mainUrl = getUrl.getUrl();

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(mainUrl + "/" + url);
		HttpResponse response = httpclient.execute(httpGet);
		
		String resp = "";
		if(!url.equals("request/network/commit"))
			resp = EntityUtils.toString(response.getEntity());
		LOGGER.info("Response: "+resp);
		
		httpclient.getConnectionManager().shutdown();

		ctx.close();
		
		return resp;
	}
	
	private String jsonParse(String paramForSearch, String jsonData) throws ParseException{
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonData);
		JSONObject jsonObj = (JSONObject) obj;
		String ret = (String) jsonObj.get(paramForSearch);
		
		return ret;
	}
}
