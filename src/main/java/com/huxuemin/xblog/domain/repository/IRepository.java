package com.huxuemin.xblog.domain.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.database.mapper.MapperRegister;
import com.huxuemin.xblog.infrastructure.DomainObject;

public abstract class IRepository<T extends DomainObject> {
	protected List<T> domainObjects = new ArrayList<T>();
	protected final Class<T> klass;
	
	protected IRepository(Class<T> klass){
		this.klass = klass;
		init();
	}
	
	public T get(Object primaryKey){
		for(Iterator<T> it = domainObjects.iterator();it.hasNext();){
			T domainObject = it.next();
			if(comparePrimaryKeyPolicy(domainObject,primaryKey)){
				return domainObject;
			}
		}
		T domainObject = (T) MapperRegister.getMapper(klass).findObjectByPrimaryKey(primaryKey);
		if(domainObject != null){
			add(domainObject);
		}
//		System.out.println(domainObject);
		return domainObject;
	}
	
	public List<T> getAll(){
		if(domainObjects.isEmpty()){
			List<T> result = MapperRegister.getMapper(klass).findObjectWhere(" 1 = 1");
			if(!result.isEmpty()){
				domainObjects.addAll(result);
			}
		}
		return domainObjects;
	}
	
	public void delete(T obj){
		synchronized(IRepository.this){
			if(domainObjects.contains(obj)){
				domainObjects.remove(obj);
				obj.delete();
			}
		}
	}
	
//	public boolean isExist(T obj){
//		synchronized(IRepository.this){
//			return domainObjects.contains(obj);
//		}
//	}
	
	public boolean isExistByPrimaryKey(Object primaryKey){
		synchronized(IRepository.this){
			T domainObject = get(primaryKey);
			if(domainObject != null){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public void add(T obj){
		synchronized(IRepository.this){
			if(!domainObjects.contains(obj)){
				domainObjects.add(obj);
			}
		}
	}
	
	protected abstract boolean comparePrimaryKeyPolicy(T domainObject,Object primaryKey);
	protected void init(){}
	
}
