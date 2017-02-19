package com.huxuemin.xblog.database.mapper;

import java.lang.reflect.Field;

import com.huxuemin.xblog.infrastructure.DomainObject;

public class DomainObjectMap <T extends DomainObject>{
	private String fieldName;
	protected Field field;
	private TableMap<T> dataMap;
	private String foreignKeyColumnName;
	private Class<T> domainClass;
	
	public DomainObjectMap(String foreignKeyColumnName,Class<T> domainClass,TableMap<T> dataMap,String fieldName){
		this.fieldName = fieldName;
		this.foreignKeyColumnName = foreignKeyColumnName;
		this.domainClass = domainClass;
		this.dataMap = dataMap;
		initField();
	}

	public Class<T> getDomainClass(){
		return domainClass;
	}	

	public String getWhereClause(){
		return foreignKeyColumnName + " = ? ";
	}
	
	public String getWhereClause(String foreignKeyColumnValue){
		return foreignKeyColumnName + " =  " + "'" + foreignKeyColumnValue + "'";
	}
	
	public String getFieldName(){
		return this.fieldName;
	}
	
	public Object getValue(Object subject){
		try {
			return field.get(subject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setField(Object result,Object columnValue){
		try {
			field.set(result, columnValue);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void initField(){
		try {
			this.field = dataMap.getKlass().getDeclaredField(getFieldName());
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
