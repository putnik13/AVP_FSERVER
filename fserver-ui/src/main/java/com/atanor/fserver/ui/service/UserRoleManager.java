package com.atanor.fserver.ui.service;

import com.atanor.fserver.ui.domain.UserRoles;

public interface UserRoleManager {
	public void insertUserRole(UserRoles userRole);
	public UserRoles showUserRole(String username);
	public void deleteUSerRole(UserRoles userRole);
}
