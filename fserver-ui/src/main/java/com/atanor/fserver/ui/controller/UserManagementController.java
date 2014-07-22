package com.atanor.fserver.ui.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.security.Credential.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atanor.fserver.ui.domain.User;
import com.atanor.fserver.ui.service.UserManager;
import com.atanor.fserver.ui.service.UserManagerImpl;

import org.apache.log4j.Logger;

import ua.atanor.fserver.ui.utils.SysExec;

@Controller
@RequestMapping("/users")
public class UserManagementController {
	private static Logger LOGGER = Logger
			.getLogger(UserManagementController.class);

	@Autowired
	private UserManager userManager;

	@RequestMapping(method = RequestMethod.GET)
	public String manageUser(Model model) {
		model.addAttribute("menuItem", "userManagement");

		model.addAttribute("userList", userManager.getUsers());
		return "control";
	}

	@RequestMapping(params = { "username", "password" }, method = RequestMethod.GET)
	public String addUser(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password)
			throws NoSuchAlgorithmException {

		User user = new User();
		user.setUsername(username.trim());
		user.setPassword(md5(password.trim()));
		user.setEnabled(1);
		user.setRole("ROLE_USER");

		if (!username.trim().isEmpty() && !password.trim().isEmpty()){
			userManager.insertUser(user);
			if(username.trim().equals("fserver")){
				SysExec.execute("/root/scripts/additional/smbpass.sh "+password.trim());
			}
		}
		LOGGER.debug("USER Created: " + user);

		return "redirect:/users";
	}

	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId, Model model) {

		userManager.deleteUser(userId);

		return "redirect:/users";
	}
	
	private String md5(String password) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(password.trim().getBytes(), 0, password.length());
		byte[] bytes = digest.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		String generatedPassword = sb.toString();
		return generatedPassword;
	}
}
