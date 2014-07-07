package com.atanor.fserver.ui.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atanor.fserver.ui.domain.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void insertUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	public User getUser(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from User where username = :username");
		query.setParameter("username", username);
		return (User) query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}
}
