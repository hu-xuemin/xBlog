package com.huxuemin.xblog.database.mapper;

import java.lang.reflect.Field;

public class ColumnAnnotationProcesser {

	public static void columnToFieldMaps(TableMap dataMap) {
		Field[] fields = dataMap.getKlass().getDeclaredFields();
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				PrimaryKeyColumn(dataMap,field);
				OneToOneColumn(dataMap,field);
				OneToManyColumn(dataMap,field);
				OneToOneDomainObject(dataMap,field);
				OneToManyDomainObject(dataMap,field);
			}
		}
	}

	private static void PrimaryKeyColumn(TableMap dataMap, Field field) {
		if (field.isAnnotationPresent(PrimaryKeyColumn.class)) {
			PrimaryKeyColumn columnAnnotation = field.getAnnotation(PrimaryKeyColumn.class);
			String columnName = columnAnnotation.columnName();
			dataMap.setPrimaryKeyColumn(columnName, field.getName());
		}
	}
	
	private static void OneToOneColumn(TableMap dataMap, Field field) {
		if (field.isAnnotationPresent(OneToOneColumn.class)) {
			OneToOneColumn columnAnnotation = field.getAnnotation(OneToOneColumn.class);
			String columnName = columnAnnotation.columnName();
			dataMap.addOneToOneColumn(columnName, field.getName());
		}
	}
	
	private static void OneToManyColumn(TableMap dataMap, Field field) {
		if (field.isAnnotationPresent(OneToManyColumn.class)) {
			OneToManyColumn columnAnnotation = field.getAnnotation(OneToManyColumn.class);
			String columnName = columnAnnotation.columnName();
			String foreignKeyColumnName = columnAnnotation.foreignKeyColumnName();
			String foreigntableName = columnAnnotation.foreigntableName();
			dataMap.addOneToManyColumn(columnName, foreignKeyColumnName, foreigntableName, field.getName());
		}
	}
	
	private static void OneToOneDomainObject(TableMap dataMap, Field field) {
		if (field.isAnnotationPresent(OneToOneDomainObject.class)) {
			OneToOneDomainObject columnAnnotation = field.getAnnotation(OneToOneDomainObject.class);
			String foreignKeyColumnName = columnAnnotation.foreignKeyColumnName();
			Class cls = columnAnnotation.foreignKeyDomainClass();
			dataMap.addOneToOneDomainObject(foreignKeyColumnName, cls, field.getName());
		}
	}
	
	private static void OneToManyDomainObject(TableMap dataMap, Field field) {
		if (field.isAnnotationPresent(OneToManyDomainObject.class)) {
			OneToManyDomainObject columnAnnotation = field.getAnnotation(OneToManyDomainObject.class);
			String foreignKeyColumnName = columnAnnotation.foreignKeyColumnName();
			Class cls = columnAnnotation.foreignKeyDomainClass();
			dataMap.addOneToManyDomainObject(foreignKeyColumnName, cls, field.getName());
		}
	}
}
