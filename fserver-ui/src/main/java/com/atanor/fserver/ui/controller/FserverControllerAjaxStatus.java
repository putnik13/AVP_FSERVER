package com.atanor.fserver.ui.controller;

import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.control.beans.GetHttpUrl;
import com.atanor.fserver.ui.service.RecordStatusManager;

@Controller
@RequestMapping("/control-status")
public class FserverControllerAjaxStatus {
	private static Logger LOGGER = Logger.getLogger(FserverController.class);
	
	@Autowired
	private RecordStatusManager recordStatus;

	@RequestMapping(method = RequestMethod.GET)
	public String getStatus(Model model, HttpServletResponse response) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "fserver-url.xml" });

		GetHttpUrl getUrl = (GetHttpUrl) ctx.getBean("getUrl");

		model.addAttribute("url", getUrl.getUrl());

		ctx.close();

		// Recording status
		try{
			model.addAttribute("recordStatus", recordStatus.showStatus().getStatus());
		}catch(IndexOutOfBoundsException e){
			model.addAttribute("recordStatus", "");
		}
		
		return "control-status";
	}
}
