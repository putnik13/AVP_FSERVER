package com.atanor.fserver.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atanor.fserver.ui.dao.UserDAO;
import com.atanor.fserver.ui.domain.User;

@Service
public class UserManagerImpl implements UserManager{

	@Autowired
	private UserDAO userDAO;
	
	@Transactional
	public void insertUser(User user) {
		userDAO.insertUser(user);
	}

	@Transactional
	public User getUserById(int userId) {
		return null;
	}

	@Transactional
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return userDAO.getUser(username);
	}

	@Transactional
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Transactional
	public void deleteUser(Long id) {
		userDAO.deleteUser(id);
	}

	
}
