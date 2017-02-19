package com.huxuemin.xblog.database.mapper;

public class ColumnOneToManyMap {
	private ColumnOneToOneMap columnMap;
	private String foreignKeyColumnName;
	private String foreigntableName;
	
	public ColumnOneToManyMap(ColumnOneToOneMap columnMap, String foreignKeyColumnName, String foreigntableName){
		this.columnMap = columnMap;
		this.foreignKeyColumnName = foreignKeyColumnName;
		this.foreigntableName = foreigntableName;
	}
	
	public String selectSQL(){
		return "SELECT " + columnList() + " FROM " + foreigntableName + " WHERE " + foreignKeyColumnName + " = ? ";
	}
	
	public String insertSQL(){
		return "INSERT INTO " + foreigntableName + "(" + columnList() + ") VALUES(?,?)";
	}
	
	public String deleteSQL(){
		return "DELETE FROM  " + foreigntableName + " WHERE " + foreignKeyColumnName + " = ? ";
	}
	
	private String columnList(){
		return " " + foreignKeyColumnName + "," + columnMap.getColumnName() + " ";
	}
	
	public String getColumnName(){
		return this.columnMap.getColumnName();
	}
	
	public String getFieldName(){
		return this.columnMap.getFieldName();
	}
	
	public Object getValue(Object subject){
		return columnMap.getValue(subject);
	}
	
	public void setField(Object result,Object columnValue){
		columnMap.setField(result, columnValue);
	}
}
