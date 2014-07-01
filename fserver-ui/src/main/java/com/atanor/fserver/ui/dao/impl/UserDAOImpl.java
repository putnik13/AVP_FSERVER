package com.atanor.fserver.ui.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atanor.fserver.ui.dao.UserDAO;
import com.atanor.fserver.ui.model.User;

@Service
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insertUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User getUser(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from user where username = :username");
		query.setParameter("username", username);
		return (User) query.list().get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				User.class);
		// Query query = sessionFactory.getCurrentSession().createQuery(
		// "select u from User u");
		// query.executeUpdate();
		// System.out.println(query.list().get(0));
		return criteria.list();
		// return (List<User>)query.list();
	}
}
