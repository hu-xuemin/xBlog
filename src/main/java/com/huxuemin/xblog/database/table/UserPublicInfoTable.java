package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;

public class UserPublicInfoTable extends ITable{
	private String dropTabel = "DROP TABLE IF EXISTS `userpublicinfo`;";
	private String createTabel = "CREATE TABLE `userpublicinfo` (" 
			+ "`username` varchar(255) NOT NULL DEFAULT '',"
			+ "`firstname` varchar(255) DEFAULT NULL,"
			+ "`lastname` varchar(255) DEFAULT NULL,"
			+ "`lang` varchar(255) DEFAULT NULL,"
			+ "`profession` varchar(255) DEFAULT NULL,"
			+ "`country` varchar(255) DEFAULT NULL,"
			+ "`sex` varchar(255) DEFAULT NULL,"
			+ "`birthday` datetime DEFAULT NULL,"
			+ "`profile` varchar(255) DEFAULT NULL,"
			+ " PRIMARY KEY (`username`)" 
			+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	private String initAdmin1 = "INSERT INTO userpublicinfo(username,firstname,lastname,lang,profession,country,sex,birthday,profile)"
	+ " VALUES('admin','','admin','','IT','','man','1987-01-01','–ƒ”–√Õª¢œ∏–·«æﬁ±');";
	
	@Override
	protected String[] getCreateStatement() {
		// TODO Auto-generated method stub
		return new String[] { dropTabel, createTabel,initAdmin1};
	}

	@Override
	protected String getTestExistStatement() {
		// TODO Auto-generated method stub
		return "select 1 from userpublicinfo where 1=2 ";
	}

}
