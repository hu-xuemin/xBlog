package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;
import com.huxuemin.xblog.infrastructure.AuthConstant;

public class RoleAuthTable extends ITable {

	private String dropTabel = "DROP TABLE IF EXISTS `rolesauth`;";
	
	private String createTabel = "CREATE TABLE `rolesauth` (" 
			+ "`name` varchar(255) NOT NULL DEFAULT '',"
			+ "`authority` varchar(255) NOT NULL DEFAULT ''," 
			+ "PRIMARY KEY (`name`,`authority`)"
			+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	
	private String initAdmin1 = "INSERT INTO rolesauth(name,authority) VALUES('admin','" + AuthConstant.ROLE_MANAGER + "')";
	private String initAdmin2 = "INSERT INTO rolesauth(name,authority) VALUES('admin','" + AuthConstant.ARTICLE_MANAGER + "')";
	private String initAdmin3 = "INSERT INTO rolesauth(name,authority) VALUES('admin','" + AuthConstant.DISCUSS_MANAGER + "')";
	private String initAdmin4 = "INSERT INTO rolesauth(name,authority) VALUES('admin','" + AuthConstant.USER_MANAGER + "')";
	private String initAdmin5 = "INSERT INTO rolesauth(name,authority) VALUES('admin','" + AuthConstant.PUBLIC_DISCUSS + "')";

	private String initManager1 = "INSERT INTO rolesauth(name,authority) VALUES('manager','" + AuthConstant.USER_MANAGER + "')";
	private String initManager2 = "INSERT INTO rolesauth(name,authority) VALUES('manager','" + AuthConstant.DISCUSS_MANAGER + "')";
	private String initManager3 = "INSERT INTO rolesauth(name,authority) VALUES('manager','" + AuthConstant.PUBLIC_DISCUSS + "')";

	private String initUser1 = "INSERT INTO rolesauth(name,authority) VALUES('user','" + AuthConstant.PUBLIC_DISCUSS + "')";
	
	@Override
	protected String[] getCreateStatement() {
		// TODO Auto-generated method stub
		return new String[] { dropTabel, createTabel, 
				initAdmin1, initAdmin2, initAdmin3, initAdmin4,initAdmin5,
				initManager1, initManager2,initManager3,
				initUser1
				};
	}

	@Override
	protected String getTestExistStatement() {
		// TODO Auto-generated method stub
		return "select 1 from rolesauth where 1=2";
	}

}
