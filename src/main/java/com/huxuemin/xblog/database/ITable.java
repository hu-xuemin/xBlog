package com.huxuemin.xblog.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public abstract class ITable {
	public boolean isExist(Connection conn){
		boolean result = true;
		try{
			PreparedStatement stat = conn.prepareStatement(getTestExistStatement());
			stat.execute();
			stat.close();
		} catch (CommunicationsException e){
			result = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	};
	
	public void create(Connection conn){
		Statement smt;
		try {
			smt = conn.createStatement();
			String[] str = getCreateStatement();
			for(int k = 0; k < str.length; k++){
				smt.addBatch(str[k]);
			}
			smt.executeBatch();
			smt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	protected abstract String getTestExistStatement();
	protected abstract String[] getCreateStatement();
}
