package com.atanor.fserver.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atanor.fserver.ui.beans.impl.UserManagerImpl;
import com.atanor.fserver.ui.model.User;

@Controller
@RequestMapping("/")
public class UserController {

		@RequestMapping(method = RequestMethod.GET)
		public String hello(Model model) {
//			User user = new User();
			UserManagerImpl users = new UserManagerImpl();
			
			model.addAttribute("users", users.getUsers());
			
			return "index";

		}
		
	}
