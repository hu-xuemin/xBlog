package com.huxuemin.xblog.database.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.database.DBConnectionFactory;
import com.huxuemin.xblog.domain.repository.UnitOfWork;
import com.huxuemin.xblog.infrastructure.DomainObject;

public class BaseMapper<T extends DomainObject> {
	protected TableMap<T> tableMap;
	
	public BaseMapper(TableMap<T> tableMap){
		this.tableMap = tableMap;
	}
	
	public TableMap<T> getTableMap(){
		return tableMap;
	}

	public List<T> findObjectWhere(String whereClause){
		String sql = "SELECT " + tableMap.columnList() + " FROM " + tableMap.getTableName() + " WHERE " + whereClause;
//		System.out.println(sql);
		List<T> result = initDomainObjectBySQL(sql);
		for(Iterator<T> it = result.iterator();it.hasNext();){
			T t = it.next();
			if(t != null){
				fillOneToManyFields(t);
				fillOneToOneDomainObjectFields(t);
				fillOneToManyDomainObjectFields(t);
			}
		}
		return result;
	}
	
	public T findObjectByPrimaryKey(Object keyValue){
		String sql = "SELECT " + tableMap.columnList() + " FROM " + tableMap.getTableName() + " WHERE " + tableMap.primaryKeyWhereClause();
//		System.out.println(sql);
		List<T> result = initDomainObjectBySQL(sql,keyValue);
		if(result.size() > 0){
			T t = result.get(0);
			if(t != null){
				fillOneToManyFields(t);
				fillOneToOneDomainObjectFields(t);
				fillOneToManyDomainObjectFields(t);
			}
			return t;
		}
		return null;
	}
	
	private List<T> initDomainObjectBySQL(String sql,Object... param){
		Connection conn = DBConnectionFactory.getConnection();
		PreparedStatement stat;
		T result = null;
		List<T> objects= new ArrayList<T>();
		try {
			stat = conn.prepareStatement(sql);
			for(int i = 0;i < param.length;i++){
				stat.setObject(i + 1, param[i]);
			}
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				result = (T) tableMap.getKlass().newInstance();
				for(Iterator<ColumnOneToOneMap> it = tableMap.getOneToOneColumns();it.hasNext();){
					ColumnOneToOneMap columnMap = it.next();
					Object columnValue = rs.getObject(columnMap.getColumnName());
					columnMap.setField(result, columnValue);
				}
				objects.add(result);
			}
			stat.close();
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return objects;
	}
	
	protected void fillOneToManyFields(T targetobject){
		for(Iterator<ColumnOneToManyMap> it = tableMap.getOneToManyColumns();it.hasNext();){
			ColumnOneToManyMap oneToManyField = it.next();
			oneToManyField.setField(targetobject, getDataFromDatabase(targetobject,oneToManyField));
		}
	}
	
	protected List<Object> getDataFromDatabase(T targetobject,ColumnOneToManyMap oneToManyField){
		String sql = oneToManyField.selectSQL();
		Connection conn = DBConnectionFactory.getConnection();
		PreparedStatement stat;
		List<Object> columnValueLsit = new ArrayList<Object>();
		try {
			stat = conn.prepareStatement(sql);
			stat.setObject(1, tableMap.primaryKeyValue(targetobject));
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Object columnValue = rs.getObject(oneToManyField.getColumnName());
				columnValueLsit.add(columnValue);
			}
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return columnValueLsit;
	}
	
	protected void fillOneToOneDomainObjectFields(T targetobject){
		for(Iterator<DomainObjectMap> it = tableMap.getOnetoOneDomainObjects();it.hasNext();){
			DomainObjectMap domainObjectField = it.next();
			List<?> objectList = getDataFromMapper(targetobject,domainObjectField);
			if(!objectList.isEmpty()){
				domainObjectField.setField(targetobject, objectList.get(0));
			}
		}
	}
	
	protected void fillOneToManyDomainObjectFields(T targetobject){
		for(Iterator<DomainObjectMap> it = tableMap.getOneToManyDomainObjects();it.hasNext();){
			DomainObjectMap domainObjectField = it.next();
			domainObjectField.setField(targetobject, getDataFromMapper(targetobject,domainObjectField));
		}
	}
	
	protected List<?> getDataFromMapper(T targetobject,DomainObjectMap domainObjectMap){
		String keyValue = tableMap.primaryKeyValue(targetobject).toString();
		String whereClause = domainObjectMap.getWhereClause(keyValue);
		return MapperRegister.getMapper(domainObjectMap.getDomainClass()).findObjectWhere(whereClause);
	}
	
	public void insert(T object){
		String sql = "INSERT INTO " + tableMap.getTableName() + "(" + tableMap.columnList() + ") " + "VALUES (" + tableMap.insertList() + ")";
		Connection conn = UnitOfWork.getCurrent().getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			int argCount = 1;
			for(Iterator<ColumnOneToOneMap> it = tableMap.getOneToOneColumns();it.hasNext();){
				stat.setObject(argCount++ , it.next().getValue(object));
			}
			int update = stat.executeUpdate();
			if(update > 0){
				insertOneToManyColumns(object);
			}
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void insertOneToManyColumns(T object) {
		// TODO Auto-generated method stub
		Iterator<ColumnOneToManyMap> it = tableMap.getOneToManyColumns();
		while(it.hasNext()){
			ColumnOneToManyMap oneToManyColumnMap = it.next();
			insertIntoDatabase(object,oneToManyColumnMap);
		}
	}
	
	protected void insertIntoDatabase(T object,ColumnOneToManyMap oneToManyColumnMap){
		String sql = oneToManyColumnMap.insertSQL();
		Iterator<?> it = ((List<?>)oneToManyColumnMap.getValue(object)).iterator();
		while(it.hasNext()){
			insertOneRow(tableMap.primaryKeyValue(object),it.next().toString(),sql);
		}
	}
	
	protected void insertOneRow(Object primaryKeyValue,Object foreignKeyColumnValue,String sql){
		Connection conn = UnitOfWork.getCurrent().getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setObject(1, primaryKeyValue);
			stat.setObject(2, foreignKeyColumnValue);
			int update = stat.executeUpdate();
			if(update > 0){
				//System.out.println("discuss insert!");
			}
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(T object){
		String sql = "UPDATE " + tableMap.getTableName() + tableMap.updateList() + " WHERE " + tableMap.primaryKeyWhereClause();
		Connection conn =UnitOfWork.getCurrent().getConnection();
		try {
			int argCount = 1;
			PreparedStatement stat = conn.prepareStatement(sql);
			for(Iterator<ColumnOneToOneMap> it = tableMap.getOneToOneColumns();it.hasNext();){
				stat.setObject(argCount++ , it.next().getValue(object));
			}
			stat.setObject(argCount++,tableMap.primaryKeyValue(object));
			int update = stat.executeUpdate();
			if(update > 0){
				updateOneToManyColumns(object);
			}
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void updateOneToManyColumns(T object){
		deleteOneToManyColumns(object);
		insertOneToManyColumns(object);
	}

	public void delete(T object){
		String sql = "DELETE FROM " + tableMap.getTableName() + " WHERE " + tableMap.primaryKeyWhereClause();
		if(deleteBySql(object,sql)){
			deleteOneToManyColumns(object);
		}
	}
	
	protected void deleteOneToManyColumns(T object){
		for(Iterator<ColumnOneToManyMap> it = tableMap.getOneToManyColumns();it.hasNext();){
			ColumnOneToManyMap oneToManyColumnMap = it.next();
			String sql = oneToManyColumnMap.deleteSQL();
			deleteBySql(object,sql);
		}
	}
	
	private boolean deleteBySql(T object,String sql){
		boolean result = false;
		Connection conn = UnitOfWork.getCurrent().getConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(sql);
			stat.setObject(1,tableMap.primaryKeyValue(object));
			int update = stat.executeUpdate();
			if(update > 0){
				//System.out.println("discuss delete!");
				result = true;
			}
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
