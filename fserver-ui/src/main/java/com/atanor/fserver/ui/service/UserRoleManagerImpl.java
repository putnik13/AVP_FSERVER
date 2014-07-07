package com.atanor.fserver.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atanor.fserver.ui.dao.UserRolesDAO;
import com.atanor.fserver.ui.domain.UserRoles;

@Service
public class UserRoleManagerImpl implements UserRoleManager{

	@Autowired
	private UserRolesDAO userRolesDAO; 
	
	@Transactional
	public void insertUserRole(UserRoles userRole) {
		userRolesDAO.insertUserRole(userRole);
	}

	@Transactional
	public UserRoles showUserRole(String username) {
		return userRolesDAO.showUserRole(username);
	}

	@Transactional
	public void deleteUSerRole(UserRoles userRole) {
		userRolesDAO.delUserRole(userRole);
	}

}
