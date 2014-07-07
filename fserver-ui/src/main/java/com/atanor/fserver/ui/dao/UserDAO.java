package com.atanor.fserver.ui.dao;

import java.util.List;

import com.atanor.fserver.ui.domain.User;

public interface UserDAO {

	public void insertUser(User user);
	
	public User getUser(String username);
	
	public List<User> getUsers();
}
