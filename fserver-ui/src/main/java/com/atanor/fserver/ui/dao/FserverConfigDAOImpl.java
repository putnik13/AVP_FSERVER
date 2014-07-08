package com.atanor.fserver.ui.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atanor.fserver.ui.domain.FserverConfig;

@Repository
public class FserverConfigDAOImpl implements FserverConfigDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveConfig(FserverConfig fsrvConf) {
		sessionFactory.getCurrentSession().save(fsrvConf);
	}

	@SuppressWarnings("unchecked")
	public List<FserverConfig> showConfig() {
		return sessionFactory.getCurrentSession().createQuery("from FserverConfig").list();
	}

	@Override
	public void clearConfig() {
//		FserverConfig config = (FserverConfig) sessionFactory.getCurrentSession().load(FserverConfig.class, id);
//		
//		if(config != null){
//			sessionFactory.getCurrentSession().delete(config);
//		}
		sessionFactory.getCurrentSession().createQuery("delete from FserverConfig").executeUpdate();
	}

}
