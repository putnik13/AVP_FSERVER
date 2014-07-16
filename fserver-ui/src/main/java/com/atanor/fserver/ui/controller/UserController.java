package com.atanor.fserver.ui.controller;

import java.security.NoSuchAlgorithmException;
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

import ua.atanor.fserver.ui.utils.Md5Passwd;

import com.atanor.fserver.ui.domain.User;
import com.atanor.fserver.ui.service.UserManager;
import com.atanor.fserver.ui.service.UserManagerImpl;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserManager userManager;

	@RequestMapping(method = RequestMethod.GET)
	public String hello(Model model) throws NoSuchAlgorithmException {
		try{
			userManager.getUsers().get(0);
		}catch(IndexOutOfBoundsException e){
			User userAdmin = new User("admin", Md5Passwd.createMd5("admin"), "ROLE_ADMIN", 1);
			User userFserver = new User("fserver", Md5Passwd.createMd5("fserver"), "ROLE_USER", 1);
			
			userManager.insertUser(userAdmin);
			userManager.insertUser(userFserver);
		}
		
		return "index";

	}
	
	@RequestMapping(params="/index", method = RequestMethod.GET)
	public String indexRedir(){
		return "redirect:/";
	}
	
}
