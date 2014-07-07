package com.atanor.fserver.ui.dao;

import com.atanor.fserver.ui.domain.UserRoles;

public interface UserRolesDAO {
	public void insertUserRole(UserRoles userRole);
	public UserRoles showUserRole(String username);
	public void delUserRole(UserRoles userRole);
}
