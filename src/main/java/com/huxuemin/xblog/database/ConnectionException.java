package com.huxuemin.xblog.database;

import java.sql.SQLException;

class ConnectionException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9146774388389821597L;

	public ConnectionException(String desc){
		super(desc);
	}

}
