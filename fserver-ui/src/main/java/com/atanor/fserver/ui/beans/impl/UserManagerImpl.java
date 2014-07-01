package com.atanor.fserver.ui.beans.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atanor.fserver.ui.beans.UserManager;
import com.atanor.fserver.ui.dao.UserDAO;
import com.atanor.fserver.ui.model.User;

@Service
public class UserManagerImpl implements UserManager{

	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public void insertUser(User user) {
		userDAO.insertUser(user);
	}

	@Override
	@Transactional
	public User getUserById(int userId) {
		return null;
	}

	@Override
	@Transactional
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return userDAO.getUser(username);
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

}
