package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;

public class UserPrivateInfoTable extends ITable{
	private String dropTabel = "DROP TABLE IF EXISTS `userprivateinfo`;";
	private String createTabel = "CREATE TABLE `userprivateinfo` (" 
			+ "`username` varchar(255) NOT NULL DEFAULT '',"
			+ "`phoneNum` varchar(255) DEFAULT NULL,"
			+ "`address` varchar(255) DEFAULT NULL,"
			+ "`qq` varchar(255) DEFAULT NULL,"
			+ "`wechat` varchar(255) DEFAULT NULL,"
			+ "`email` varchar(255) DEFAULT NULL,"
			+ " PRIMARY KEY (`username`)" 
			+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	private String initAdmin1 = "INSERT INTO userprivateinfo(username,phoneNum,address,qq,wechat,email)"
	+ " VALUES('admin','13066961905','','397997401','hu-xuemin','dev13@huxuemin.com');";
	
	@Override
	protected String[] getCreateStatement() {
		// TODO Auto-generated method stub
		return new String[] { dropTabel, createTabel,initAdmin1};
	}

	@Override
	protected String getTestExistStatement() {
		// TODO Auto-generated method stub
		return "select 1 from userprivateinfo where 1=2 ";
	}

}
