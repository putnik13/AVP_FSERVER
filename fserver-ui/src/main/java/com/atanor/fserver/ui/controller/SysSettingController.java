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

	@RequestMapping(params = {"ip","netmask","gateway"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String setIpAddress(@RequestParam("ip") String ip,
			@RequestParam("netmask") String netmask,
			@RequestParam("gateway") String gateway,
			Model model) {
		
		LOGGER.info("POST Request:"+ip+";"+netmask+";"+gateway);
		model.addAttribute("ip",ip);
		return "control";
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
		
		String resp = EntityUtils.toString(response.getEntity());
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
