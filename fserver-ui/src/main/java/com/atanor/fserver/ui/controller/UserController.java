package com.atanor.fserver.ui.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.domain.User;
import com.atanor.fserver.ui.domain.UserRoles;
import com.atanor.fserver.ui.service.UserManager;
import com.atanor.fserver.ui.service.UserRoleManager;
import com.atanor.fserver.ui.service.UserRoleManagerImpl;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserManager userManager;

	@RequestMapping(method = RequestMethod.GET)
	public String hello(Model model) {
		UserRoleManagerImpl userRole = new UserRoleManagerImpl();
		model.addAttribute("role", userRole.showUserRole("admin"));
		return "index";

	}
	
	@RequestMapping(params="/index", method = RequestMethod.GET)
	public String indexRedir(){
		return "redirect:/";
	}

}
