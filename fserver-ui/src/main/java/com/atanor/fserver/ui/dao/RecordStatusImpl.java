package com.atanor.fserver.ui.dao;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atanor.fserver.ui.domain.RecordStatus;

@Repository
public class RecordStatusImpl implements RecordStatusDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setStatus(RecordStatus status) {
		sessionFactory.getCurrentSession().save(status);
	}

	public RecordStatus showStatus() {
		Query q = sessionFactory.getCurrentSession().createQuery("from RecordStatus");
		return (RecordStatus) q.list().get(0);
	}

	public void removeStatus() {
		Query q = sessionFactory.getCurrentSession().createQuery("delete from RecordStatus");
		q.executeUpdate();
	}

}
