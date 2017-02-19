package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;

public class UserRolesTable extends ITable{

	private String deleteUser = "DROP TABLE IF EXISTS `userrole`;";
	private String createUser = "CREATE TABLE `userrole` ("
		+	"`username` varchar(255) NOT NULL DEFAULT '',"
		+   "`rolename` varchar(255) NOT NULL DEFAULT '',"
		+   "PRIMARY KEY (`username`,`rolename`)"
		+	") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	
	private String initAdmin1 = "INSERT INTO userrole(username,rolename)"
	+ " VALUES('admin','admin');";
	
	@Override
	protected String[] getCreateStatement() {
		// TODO Auto-generated method stub
		return new String[]{
				deleteUser,
				createUser,
				initAdmin1
		};
	}

	@Override
	protected String getTestExistStatement() {
		// TODO Auto-generated method stub
		return "select 1 from userrole where 1=2";
	}

}
