package com.atanor.fserver.ui.beans;

import java.util.List;

import com.atanor.fserver.ui.model.User;

public interface UserManager {

	void insertUser(User user);
	User getUserById(int userId);
	User getUser(String username);
	List<User> getUsers();
}
