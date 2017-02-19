package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;

public class RolesTable extends ITable {

	private String dropTabel = "DROP TABLE IF EXISTS `roles`;";
	
	private String createTabel = "CREATE TABLE `roles` (" 
			+ "`name` varchar(255) NOT NULL DEFAULT '',"
			+ "PRIMARY KEY (`name`)"
			+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	
	private String initAdmin1 = "INSERT INTO ROLES(name) VALUES('admin')";

	private String initManager1 = "INSERT INTO ROLES(name) VALUES('manager')";

	private String initUser1 = "INSERT INTO ROLES(name) VALUES('user')";
	
	@Override
	protected String[] getCreateStatement() {
		// TODO Auto-generated method stub
		return new String[] { dropTabel, createTabel, 
				initAdmin1, 
				initManager1,
				initUser1
				};
	}

	@Override
	protected String getTestExistStatement() {
		// TODO Auto-generated method stub
		return "select 1 from roles where 1=2";
	}

}
