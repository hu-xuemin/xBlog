package com.huxuemin.xblog.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class MySqlDBManager{
	private final static String TAG = "MysqlDBConnectionManager";
	private final static String driverClassName = "com.mysql.jdbc.Driver";
	private final String database = "blog";
	private final String URL = "jdbc:mysql://localhost:3306/" + database + "?useSSL=false";
	private final String USERNAME = "blog";
	private final String PASSWORD = "blog";
	private final String createDatabase = "create DATABASE "+ database +" CHARACTER SET utf8;";
	private final String createUser = "create USER '" + USERNAME + "'@'localhost' IDENTIFIED BY '" + PASSWORD + "';";
	private final String grantUser = "GRANT ALL PRIVILEGES ON " + database + ".* TO '" + USERNAME + "'@'localhost';";
	private final String flush = "FLUSH PRIVILEGES;";
	
	private final String ROOT_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
	private final String ROOT_USERNAME = "root";
	//private final String ROOT_PASSWORD = "7955909";
	private final String ROOT_PASSWORD = "h7955909";
	//private final String ROOT_PASSWORD = "Hh7955909+";
	
	//private final static int intMaxConnectionNum = 50;
	//private int intConnectionNum = 0;

	private static ConnectionPool connPool = new ConnectionPool();

	MySqlDBManager(){
		try{
			Class.forName(driverClassName);			
		} catch(ClassNotFoundException ce){
			ce.printStackTrace();
		}
		initDatabase();
	}
	
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return connPool.pop();
	}

	public void closeAllConnection() {
		connPool.releaseAllConnection();
	}
	
	private void initDatabase(){
		if(!DatabaseIsExist()){
			switchRootInitDatabase();
		}
	}

	private boolean DatabaseIsExist(){
		boolean result = false;
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private void switchRootInitDatabase(){
		try {
			Connection conn = DriverManager.getConnection(ROOT_URL, ROOT_USERNAME, ROOT_PASSWORD);
			Statement smt = conn.createStatement();
			smt.addBatch(createDatabase);
			smt.addBatch(createUser);
			smt.addBatch(grantUser);
			smt.addBatch(flush);
			smt.executeBatch();
			smt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initTable(ITable tab){
		if(!tab.isExist(getConnection())){
			tab.create(getConnection());
		}
	}
	
	public boolean tableIsExist(ITable tab){
		return tab.isExist(getConnection());
	}
		
	Connection createNewConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(TAG + ",createNewConnection()!");
		return conn;
	}
}
