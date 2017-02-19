package com.huxuemin.xblog.database.table;

import com.huxuemin.xblog.database.ITable;

public class DiscussTable extends ITable{

	private String dropTabel = "DROP TABLE IF EXISTS `discusses`;";
	private String createTabel = "CREATE TABLE `discusses` (" +
			"`id` bigint(63) NOT NULL," +
			"`content` text DEFAULT NULL," +
			"`articleid` bigint (63) NOT NULL," +
			"`replyid` bigint (63) NOT NULL," +
			"`username` varchar(255) DEFAULT NULL," +
			"`createon` datetime DEFAULT NULL," +
			" PRIMARY KEY (`id`)," +
			" KEY `index_discusses_articleid` (`articleid`)" +
			") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	@Override
	protected String[] getCreateStatement() {
		// TODO Auto-generated method stub
		return new String[] { dropTabel, createTabel};
	}

	@Override
	protected String getTestExistStatement() {
		// TODO Auto-generated method stub
		return "select 1 from discusses where 1=2";
	}

}
