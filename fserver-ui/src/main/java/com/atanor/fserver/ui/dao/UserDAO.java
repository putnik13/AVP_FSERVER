package com.atanor.fserver.ui.dao;

import java.util.List;

import com.atanor.fserver.ui.model.User;

public interface UserDAO {

	void insertUser(User user);
	
	User getUser(String username);
	
	List<User> getUsers();
}
