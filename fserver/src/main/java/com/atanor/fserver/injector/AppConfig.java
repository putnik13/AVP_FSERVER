package com.atanor.fserver.injector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atanor.fserver.db.Fserver_ini;
import com.atanor.fserver.db.HibernateUtil;
import com.atanor.fserver.utils.AppUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class AppConfig extends GuiceServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		saveServletContext(event.getServletContext());
		super.contextInitialized(event);
	}
	
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AppServletModule(), new AppCoreModule());
	}

	private void saveServletContext(final ServletContext context) {
		AppUtils.setServletContext(context);
	}
	
}
