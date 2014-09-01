package com.atanor.fserver.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.control.beans.GetHttpUrl;
import com.atanor.fserver.ui.domain.FserverConfig;
import com.atanor.fserver.ui.domain.RecordStatus;
import com.atanor.fserver.ui.service.FserverConfigManager;
import com.atanor.fserver.ui.service.RecordStatusManager;

@Controller
@RequestMapping("/stream")
public class VideoStreamReader {
private static Logger LOGGER = Logger.getLogger(FserverController.class);
	
	@Autowired
	private FserverConfigManager fConfig;
	
	@Autowired
	private RecordStatusManager recordStatus;

	@RequestMapping(method = RequestMethod.GET)
	public String startReading(Model model, HttpServletResponse response) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getUrl");

		model.addAttribute("url", getUrl.getUrl());

		ctx.close();

		model.addAttribute("menuItem", "stream");
		
		
		String recording = "";
		try{
			recording = recordStatus.showStatus().getStatus();
			if(recording != ""){
				FserverConfig config = new FserverConfig();
				try{
				model.addAttribute("streamUrl", config.getMedia_source().trim());
				}catch(Exception e){
				}
			}
		}catch(IndexOutOfBoundsException e){
		}
		
		
		return "control";
	}
}
