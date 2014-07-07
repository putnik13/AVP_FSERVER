package com.atanor.fserver.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.control.beans.GetHttpUrl;

@Controller
@RequestMapping("/control")
public class FserverController {
	private static Logger LOGGER = Logger.getLogger(FserverController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getControl(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName();
//		model.addAttribute("userName", name.toUpperCase());
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getUrl");

		model.addAttribute("url", getUrl.getUrl());

		ctx.close();

		model.addAttribute("menuItem", "recording");
		
		return "control";
	}

	@RequestMapping(params = "addChapter", method = RequestMethod.GET)
	public String getAddChapter(HttpServletRequest request) {
		try {
			doFserverRequest("api/addChapter");
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}
	
	@RequestMapping(params = "start", method = RequestMethod.GET)
	public String getStart(HttpServletRequest request) {
		try {
			doFserverRequest("api/startRecording");
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}
	
	@RequestMapping(params = "stop", method = RequestMethod.GET)
	public String getStop(HttpServletRequest request) {
		try {
			doFserverRequest("api/stopRecording");
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}

	@RequestMapping(params = "startRedirect", method = RequestMethod.GET)
	public String getStartRedirect(HttpServletRequest request) {
		try {
			doFserverRequest("api/startRedirect");
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}
	
	@RequestMapping(params = "stopRedirect", method = RequestMethod.GET)
	public String getStopRedirect(HttpServletRequest request) {
		try {
			doFserverRequest("api/stopRedirect");
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}
	
	private void doFserverRequest(String url) throws ClientProtocolException,
			IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getUrl");

		String mainUrl = getUrl.getUrl();

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(mainUrl + "/" + url);

		HttpResponse response = httpclient.execute(httpPost);
		LOGGER.info(response);
		httpclient.getConnectionManager().shutdown();
		
		ctx.close();
	}
}
