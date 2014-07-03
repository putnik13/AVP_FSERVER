package com.atanor.fserver.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.domain.User;
import com.atanor.fserver.ui.service.UserManager;
import com.atanor.fserver.ui.service.UserManagerImpl;

@Controller
@RequestMapping("/")
public class UserController {

		@Autowired
		private UserManager userManager;
	
		@RequestMapping(method = RequestMethod.GET)
		public String hello(Model model) {

			model.addAttribute("userList", userManager.getUsers());
				
			return "index";

		}
		
	}
