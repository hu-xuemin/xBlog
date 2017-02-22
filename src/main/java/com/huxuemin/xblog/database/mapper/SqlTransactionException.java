package com.huxuemin.xblog.database.mapper;

public class SqlTransactionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278972270682020275L;
	
	public SqlTransactionException(String message){
	    super(message);
	}
}
