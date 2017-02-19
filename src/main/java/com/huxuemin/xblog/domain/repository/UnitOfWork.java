package com.huxuemin.xblog.domain.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.database.mapper.MapperRegister;
import com.huxuemin.xblog.infrastructure.DomainObject;

public class UnitOfWork {
	
	private static ThreadLocal<UnitOfWork> current = new ThreadLocal<UnitOfWork>();
	
	private List<DomainObject> newObjects = new ArrayList<DomainObject>(); 
	private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>();
	private List<DomainObject> removedObjects = new ArrayList<DomainObject>();
	
	public void registerNew(DomainObject obj){
		if(obj != null && !dirtyObjects.contains(obj) 
				&& !removedObjects.contains(obj) 
				&& !newObjects.contains(obj)){
			newObjects.add(obj);
		}
	}
	
	public void registerDirty(DomainObject obj){
		if(obj != null && !dirtyObjects.contains(obj) 
				&& !removedObjects.contains(obj) 
				&& !newObjects.contains(obj)){
			dirtyObjects.add(obj);
		}
	}
	
	public void registerRemoved(DomainObject obj){
		if(obj != null){
			if(!newObjects.remove(obj)){
				dirtyObjects.remove(obj);
				if(!removedObjects.contains(obj)){
					removedObjects.add(obj);
				}
			}
		}
	}
	
	public static void newCurrent(){
		setCurrent(new UnitOfWork());
	}
	
	public static void setCurrent(UnitOfWork uow){
		current.set(uow);
	}
	
	public static UnitOfWork getCurrent(){
		return current.get();
	}
	
	public void commit(){
		insertNew();
		UpdateDirty();
		deleteRemoved();
	}
	
	private void insertNew(){
		Iterator<DomainObject> objects = newObjects.iterator();
		while(objects.hasNext()){
			DomainObject obj = objects.next();
			MapperRegister.getMapper(obj.getClass()).insert(obj);
		}
	}
	
	private void UpdateDirty(){
		Iterator<DomainObject> objects = dirtyObjects.iterator();
		while(objects.hasNext()){
			DomainObject obj = objects.next();
			MapperRegister.getMapper(obj.getClass()).update(obj);
		}
	}
	
	private void deleteRemoved(){
		Iterator<DomainObject> objects = removedObjects.iterator();
		while(objects.hasNext()){
			DomainObject obj = objects.next();
			MapperRegister.getMapper(obj.getClass()).delete(obj);
		}
	}
}
