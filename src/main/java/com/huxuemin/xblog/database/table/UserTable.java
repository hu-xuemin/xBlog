package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;

public class UserTable extends ITable{

	private String deleteUser = "DROP TABLE IF EXISTS `users`;";
	private String createUser = "CREATE TABLE `users` ("
		+	"`id` bigint(63) NOT NULL ,"
		+	"`username` varchar(255) NOT NULL DEFAULT '',"
		+	"`hashedpassword` varchar(255) NOT NULL DEFAULT '',"
		+	"`salt` varchar(255) DEFAULT NULL,"
		+	"`accountlock` tinyint(1) NOT NULL DEFAULT '0',"
		+	"`lastloginon` datetime DEFAULT NULL,"
		+	"`updateon` datetime DEFAULT NULL,"
		+	"`mustchangepassword` tinyint(1) NOT NULL DEFAULT '0',"
		+	"`passwordchangeon` datetime DEFAULT NULL,"
		+	"`usercreateon` datetime DEFAULT NULL,"
		+	"PRIMARY KEY (`username`),"
		+	"UNIQUE (`id`),"
		+	"KEY `index_users_on_username` (`id`)"
		+	") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	//ƒ¨»œ√‹¬Î£∫admin123
	private String initAdmin1 = "INSERT INTO USERS(id,username,hashedpassword,salt,accountLock,lastLoginOn,updateOn,mustChangePassword,passwordChangeOn,userCreateOn)"
	+ " VALUES(100,'admin','ff37fb7012028a5353f26aa53e1d3b0f','8b563c68d80309af81354269c5a072a0',0,'2016-05-31','2016-05-31',0,'2016-05-31','2016-05-31');";
	
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
		return "select 1 from USERS where 1=2";
	}

}
