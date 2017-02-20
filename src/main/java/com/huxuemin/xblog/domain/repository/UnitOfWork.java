package com.huxuemin.xblog.domain.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.database.DBConnectionFactory;
import com.huxuemin.xblog.database.mapper.MapperRegister;
import com.huxuemin.xblog.database.mapper.SqlTransactionException;
import com.huxuemin.xblog.infrastructure.DomainObject;

public class UnitOfWork {

	private static ThreadLocal<UnitOfWork> current = new ThreadLocal<UnitOfWork>();

	private List<DomainObject> newObjects = new ArrayList<DomainObject>();
	private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>();
	private List<DomainObject> removedObjects = new ArrayList<DomainObject>();
	private Connection conn;

	private UnitOfWork() {
		conn = DBConnectionFactory.getConnection();
		try {
			if(conn!=null && conn.getAutoCommit()){
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void registerNew(DomainObject obj) {
		if (obj != null && !dirtyObjects.contains(obj) && !removedObjects.contains(obj) && !newObjects.contains(obj)) {
			newObjects.add(obj);
		}
	}

	public void registerDirty(DomainObject obj) {
		if (obj != null && !dirtyObjects.contains(obj) && !removedObjects.contains(obj) && !newObjects.contains(obj)) {
			dirtyObjects.add(obj);
		}
	}

	public void registerRemoved(DomainObject obj) {
		if (obj != null) {
			if (!newObjects.remove(obj)) {
				dirtyObjects.remove(obj);
				if (!removedObjects.contains(obj)) {
					removedObjects.add(obj);
				}
			}
		}
	}

	public static void newCurrent() {
		setCurrent(new UnitOfWork());
	}

	public static void setCurrent(UnitOfWork uow) {
		current.set(uow);
	}

	public static UnitOfWork getCurrent() {
		return current.get();
	}

	public Connection getConnection() {
		return conn;
	}

	public void commit() {
		insertNew();
		UpdateDirty();
		deleteRemoved();
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new SqlTransactionException(e.toString());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		current.remove();
	}

	private void insertNew() {
		Iterator<DomainObject> objects = newObjects.iterator();
		while (objects.hasNext()) {
			DomainObject obj = objects.next();
			MapperRegister.getMapper(obj.getClass()).insert(obj);
		}
	}

	private void UpdateDirty() {
		Iterator<DomainObject> objects = dirtyObjects.iterator();
		while (objects.hasNext()) {
			DomainObject obj = objects.next();
			MapperRegister.getMapper(obj.getClass()).update(obj);
		}
	}

	private void deleteRemoved() {
		Iterator<DomainObject> objects = removedObjects.iterator();
		while (objects.hasNext()) {
			DomainObject obj = objects.next();
			MapperRegister.getMapper(obj.getClass()).delete(obj);
		}
	}
}
