package com.atanor.fserver.ui.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.control.beans.GetHttpUrl;
import com.atanor.fserver.ui.domain.RecordStatus;
import com.atanor.fserver.ui.service.RecordStatusManager;
import com.atanor.fserver.ui.service.RecordStatusManagerImpl;

@Controller
@RequestMapping("/control")
public class FserverController {
	private static Logger LOGGER = Logger.getLogger(FserverController.class);
	
	@Autowired
	private RecordStatusManager recordStatus;

	@RequestMapping(method = RequestMethod.GET)
	public String getControl(Model model, HttpServletResponse response) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getUrl");

		model.addAttribute("url", getUrl.getUrl());

		ctx.close();

		model.addAttribute("menuItem", "recording");
		
		// Recording status
		try{
			model.addAttribute("recordStatus", recordStatus.showStatus().getStatus());
		}catch(IndexOutOfBoundsException e){
			model.addAttribute("recordStatus", "");
		}
		
//		response.setHeader("Refresh", "5");
		
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
		String recording = "";
		try{
			recording = recordStatus.showStatus().getStatus();
		}catch(IndexOutOfBoundsException e){
		}
		
		try {
			recordStatus.removeStatus();
			if(recording == ""){
				try{
					doFserverRequest("api/stopRedirect");
					doFserverRequest("api/stopRecording");
					
					if(doFserverRequest("api/startRecording").toString().toLowerCase().contains("ok")){
						recordStatus.setStatus(new RecordStatus("RECORDING"));
					}else{
						recordStatus.setStatus(new RecordStatus("Error: remote host is unreachable..."));
					}
				}catch(NullPointerException e){
					recordStatus.setStatus(new RecordStatus("Error: remote host is unreachable..."));
				}
			}
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}
	
	@RequestMapping(params = "stop", method = RequestMethod.GET)
	public String getStop(HttpServletRequest request) {
		try {
			doFserverRequest("api/stopRecording");
			recordStatus.removeStatus();
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}

	@RequestMapping(params = "startRedirect", method = RequestMethod.GET)
	public String getStartRedirect(HttpServletRequest request) {
		try {
			recordStatus.removeStatus();
			try{
				doFserverRequest("api/stopRedirect");
				doFserverRequest("api/stopRecording");
				
				if(doFserverRequest("api/startRecording").toString().toLowerCase().contains("ok")){
					doFserverRequest("api/startRedirect");
					recordStatus.setStatus(new RecordStatus("RECORDING & REDIRECTING"));
				}else{
					recordStatus.setStatus(new RecordStatus("Error: remote host is unreachable..."));
				}
			}catch(NullPointerException e){
				recordStatus.setStatus(new RecordStatus("Error: remote host is unreachable..."));
			}
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}
	
	@RequestMapping(params = "stopRedirect", method = RequestMethod.GET)
	public String getStopRedirect(HttpServletRequest request) {
		try {
			doFserverRequest("api/stopRedirect");
			doFserverRequest("api/stopRecording");
			recordStatus.removeStatus();
		} catch (IOException e) {
			LOGGER.error(e);
		}

		return "redirect:/control";
	}
	
	private StringBuilder doFserverRequest(String url) throws ClientProtocolException,
			IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getUrl");

		String mainUrl = getUrl.getUrl();

		StringBuilder responseText = new StringBuilder();
		
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(mainUrl + "/" + url);

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
		
		ctx.close();
		
		return responseText;
	}
}
