package com.atanor.fserver.ui.controller;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.control.beans.GetHttpUrl;

@Controller
@RequestMapping("/menu")
public class IndexMenuController {
	
	private static Logger LOGGER = Logger.getLogger(UserController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getControl(Model model) {
		model.addAttribute("menuItem", "main");
		
		return "control";
	}
}
