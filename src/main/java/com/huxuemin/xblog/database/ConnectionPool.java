package com.huxuemin.xblog.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

class ConnectionPool {
//	private final static String TAG = "ConnectionPool";
	private static ArrayList<ConnectionAdapter> idleConnection = new ArrayList<>();
	private static ArrayList<ConnectionAdapter> usedConnection = new ArrayList<>();
	
	public ConnectionPool(){}
	
	void push(ConnectionAdapter connAdapter){
		// TODO Auto-generated method stub
		synchronized(ConnectionPool.class){
			if(connAdapter != null){
				usedConnection.remove(connAdapter);
			}
			
			if(connAdapter != null && !idleConnection.contains(connAdapter)){
				idleConnection.add(connAdapter);
			}
		}
//		System.out.println(TAG + ",idle connection number: " + idleConnection.size());
//		System.out.println(TAG + ",used connection number: " + usedConnection.size());
	}
	
	public Connection pop(){
		synchronized(ConnectionPool.class){
			ConnectionAdapter connAdapter = null;
			if(idleConnection.isEmpty()){
				connAdapter = createNewConnectionAdapter();
			}else{
				connAdapter = idleConnection.get(0);
				idleConnection.remove(connAdapter);
				if(connAdapter == null || connAdapter.isInvalid()){
					connAdapter = createNewConnectionAdapter();
				}
			}
			//System.out.println(TAG + ",pop()");
			if(connAdapter != null && !usedConnection.contains(connAdapter)){
				usedConnection.add(connAdapter);
			}
			return connAdapter.getProxyConnection();
		}
	}

	private ConnectionAdapter createNewConnectionAdapter(){
		return DBConnectionFactory.createNewConnectionAdapter(ConnectionPool.this);
	}
	
	public void releaseAllConnection() {
		// TODO Auto-generated method stub
		Iterator<ConnectionAdapter> it = idleConnection.iterator();
		while(it.hasNext()){
			it.next().Close();
		}
		
		it = usedConnection.iterator();
		while(it.hasNext()){
			it.next().Close();
		}
		idleConnection.clear();
		usedConnection.clear();
	}
}
