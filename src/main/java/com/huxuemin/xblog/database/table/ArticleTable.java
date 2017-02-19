package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;

public class ArticleTable extends ITable {
	private String dropTabel = "DROP TABLE IF EXISTS `articles`;";
	private String createTabel = "CREATE TABLE `articles` (" 
			+ "`id` bigint(63) NOT NULL,"
			+ "`title` varchar(255) DEFAULT NULL," 
			+ "`content` text DEFAULT NULL,"
			+ "`toptime` datetime DEFAULT NULL," 
			+ "`updatetime` datetime DEFAULT NULL,"
			+ "`category` varchar(255) DEFAULT NULL," 
			+ "`owerusername` varchar(255) DEFAULT NULL,"
			+ "`candiscussstatus` tinyint(1) NOT NULL DEFAULT '0'," 
			+ "`articlestatus` int(4) DEFAULT NULL,"
			+ "`lastreplytime` datetime DEFAULT NULL,"
			+ " PRIMARY KEY (`id`)," 
			+ " KEY `index_articles_title` (`title`)"
			+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	@Override
	protected String[] getCreateStatement() {
		// TODO Auto-generated method stub
		return new String[] { dropTabel, createTabel};
	}
	
	@Override
	protected String getTestExistStatement() {
		// TODO Auto-generated method stub
		return "select 1 from articles where 1=2";
	}

}
