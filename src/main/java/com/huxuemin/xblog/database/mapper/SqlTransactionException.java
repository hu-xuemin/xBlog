package com.huxuemin.xblog.database.mapper;

public class SqlTransactionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278972270682020275L;
	private String message;
	
	public SqlTransactionException(String message){
		this.message = message;
	}
	
	@Override
	public String toString(){
		return message;
	}
}
