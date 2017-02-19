package com.huxuemin.xblog.web;

import javax.servlet.ServletContextEvent;

import com.huxuemin.xblog.database.DBConnectionFactory;


public class ContextManager implements javax.servlet.ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		DBConnectionFactory.closeAllConnection();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		DBConnectionFactory.initDataBaseContext();
	}

}
