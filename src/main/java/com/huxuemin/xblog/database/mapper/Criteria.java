package com.huxuemin.xblog.database.mapper;

import com.huxuemin.xblog.infrastructure.DomainObject;

public class Criteria {
	private String sqlOperator;
	private String fieldName;
	private Object value;
	
	public static Criteria equalTo(String fieldName,String value){
		return new Criteria(" = ",fieldName,value);
	}
	
	private Criteria(String sql,String fieldName,Object value){
		this.sqlOperator = sql;
		this.fieldName = fieldName;
		this.value = value;
	}
	
	public String generateSql(TableMap<? extends DomainObject> dataMap){
		return dataMap.getColumnForField(fieldName) + sqlOperator + value;
	}
}
