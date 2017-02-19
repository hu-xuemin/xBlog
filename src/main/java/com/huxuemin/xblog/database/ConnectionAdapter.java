package com.huxuemin.xblog.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

class ConnectionAdapter {
	private final long connectionIimeout = 8 * 60 * 60 * 1000 - 10 * 60 * 1000;
	private long lastIimeoutTest = 0L;
	private boolean isIdle = true;
	private long ownerThreadId = -1L;
	private Connection conn;
	private Connection proxyConn;
	
	public ConnectionAdapter(Connection conn,ConnectionPool pool){
		this.conn = conn;
		this.proxyConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), new Class[]{Connection.class}, new _Connection(conn,pool));
		this.lastIimeoutTest = System.currentTimeMillis();
	}

	public Connection getProxyConnection(){
		if(markUsed()){
			return proxyConn;
		}else{
			return null;
		}
	}
	
	public void Close(){
		try {
			if( conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isInvalid(){
		if(conn == null){return true;}
		if(proxyConn == null){return true;}
		if(connectionIsWaitTimeout()){return true;}
		
		try {
			if(!conn.isClosed()){
				return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}
	
	private Boolean connectionIsWaitTimeout(){
		boolean result = true;
		if((System.currentTimeMillis() - lastIimeoutTest) >  connectionIimeout){
			result = testConnectionIsOk();
		}else{
			result = false;
		}
		lastIimeoutTest = System.currentTimeMillis();
		return result;
	}
	
	private boolean testConnectionIsOk(){
		try{
			PreparedStatement stat = conn.prepareStatement("select 1 from users where 1=2");
			stat.execute();
			stat.close();
			return true;
		} catch (CommunicationsException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean markUsed(){
		if(!isIdle){
			return false;
		}
		isIdle = false;
		ownerThreadId = Thread.currentThread().getId();
		return true;
	}
	
	private boolean markIdle() throws Exception{
		if(isIdle){return false;}
		
		if(ownerThreadId != Thread.currentThread().getId()){
			throw new ConnectionException("Current ThreadId is " + Thread.currentThread().getId() + ",but the connection is used by " + ownerThreadId + " Thread!");
		}
		
		isIdle = true;
		ownerThreadId = -1;
		return true;
	}
	
	private void checkStatus() throws Exception{
		if(isIdle){
			throw new ConnectionException("this connection is closed!");
		}
		if(ownerThreadId != Thread.currentThread().getId()){
			throw new ConnectionException("Current ThreadId is " + Thread.currentThread().getId() + ",but the connection is used by " + ownerThreadId + " Thread!");
		}
	}

	private boolean isClosed(){
		if(isIdle){return true;}
		if(ownerThreadId != Thread.currentThread().getId()){return true;}
		return false;
	}
	
	private class _Connection implements InvocationHandler{
		private final Connection conn;
		private final ConnectionPool pool;
		
		_Connection(Connection conn,ConnectionPool pool){
			this.conn = conn;
			this.pool = pool;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// TODO Auto-generated method stub
			Object obj = null;
			if("close".equals(method.getName())){
				if(markIdle()){
					pool.push(ConnectionAdapter.this);
				}
			}else if("isClosed".equals(method.getName())){
				obj = isClosed();
			}else{
				checkStatus();
				obj = method.invoke(conn, args);
			}
			return obj;
		}
	}
}
