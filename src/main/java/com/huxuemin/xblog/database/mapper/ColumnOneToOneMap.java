package com.huxuemin.xblog.database.mapper;

import java.lang.reflect.Field;

public class ColumnOneToOneMap {
	private String columnName;
	private String fieldName;
	protected Field field;
	private TableMap dataMap;
	
	public ColumnOneToOneMap(String columnName,String fieldName,TableMap dataMap){
		this.columnName = columnName;
		this.fieldName = fieldName;
		this.dataMap = dataMap;
		initField();
	}
	
	public String getColumnName(){
		return this.columnName;
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
