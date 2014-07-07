package com.atanor.fserver.ui.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atanor.fserver.ui.domain.UserRoles;

@Repository
public class UserRolesDAOImpl implements UserRolesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insertUserRole(UserRoles userRole) {
		sessionFactory.getCurrentSession().save(userRole);
	}

	@Override
	public UserRoles showUserRole(String username) {
		Query q = sessionFactory.getCurrentSession().createQuery(
				"from UserRoles where username = :username");
		q.setParameter("username", username);
		
		return (UserRoles) q.list().get(0);
	}

	@Override
	public void delUserRole(UserRoles userRole) {
		sessionFactory.getCurrentSession().delete(userRole);
	}

}
