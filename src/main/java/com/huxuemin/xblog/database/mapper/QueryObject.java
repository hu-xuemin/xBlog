package com.huxuemin.xblog.database.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryObject {
	private Class klass;
	private List<Criteria> criterias = new ArrayList<Criteria>();
	
	public QueryObject(Class klass){
		this.klass = klass;
	}
	
	public void addCriteria(Criteria criteria){
		if(!criterias.contains(criteria)){
			criterias.add(criteria);
		}
	}
	
	public String generateWhereClause(){
		StringBuffer result = new StringBuffer();
		for(Iterator<Criteria> it = criterias.iterator();it.hasNext();){
			Criteria c = it.next();
			if(result.length() != 0){
				result.append(" AND ");
			}
			result .append(c.generateSql(MapperRegister.getMapper(klass).getTableMap()));
		}
		return result.toString();
	}
}
