package com.atanor.fserver.ui.service;

import java.util.List;

import com.atanor.fserver.ui.domain.User;

public interface UserManager {

	public void insertUser(User user);
	public User getUserById(int userId);
	public User getUser(String username);
	public List<User> getUsers();
}
