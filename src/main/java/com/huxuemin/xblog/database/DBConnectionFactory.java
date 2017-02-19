package com.huxuemin.xblog.database;

import java.sql.Connection;
import java.util.Map;

import com.huxuemin.xblog.database.mapper.BaseMapper;
import com.huxuemin.xblog.database.mapper.MapperRegister;
import com.huxuemin.xblog.database.mapper.TableAnnotationProcesser;
import com.huxuemin.xblog.database.mapper.TableMap;
import com.huxuemin.xblog.database.table.ArticleTable;
import com.huxuemin.xblog.database.table.DiscussTable;
import com.huxuemin.xblog.database.table.RoleAuthTable;
import com.huxuemin.xblog.database.table.RolesTable;
import com.huxuemin.xblog.database.table.UserPrivateInfoTable;
import com.huxuemin.xblog.database.table.UserPublicInfoTable;
import com.huxuemin.xblog.database.table.UserRolesTable;
import com.huxuemin.xblog.database.table.UserTable;

public class DBConnectionFactory {

	private static MySqlDBManager connectionManager = new MySqlDBManager();

	public static Connection getConnection() {
		return connectionManager.getConnection();
	}

	public static void initDataBaseContext() {
		initTables();
		initDomainMapper();
	}

	private static void initDomainMapper() {
		Map<Class<?>, String> classToTablename = TableAnnotationProcesser
				.scanClassToTablenameMaps("com.huxuemin.xblog.domain");
		for (Map.Entry<Class<?>, String> entry : classToTablename.entrySet()) {
			Class<?> klass = entry.getKey();
			String tableName = entry.getValue();
			TableMap dm = new TableMap(tableName, klass).buildColumnAnnotation();
			MapperRegister.register(klass, new BaseMapper(dm));
		}
	}

	private static void initTables() {
		initTable(new UserTable());
		initTable(new RoleAuthTable());
		initTable(new RolesTable());
		initTable(new ArticleTable());
		initTable(new DiscussTable());
		initTable(new UserRolesTable());
		initTable(new UserPublicInfoTable());
		initTable(new UserPrivateInfoTable());
	}

	static ConnectionAdapter createNewConnectionAdapter(ConnectionPool pool) {
		return new ConnectionAdapter(connectionManager.createNewConnection(), pool);
	}

	private static void initTable(ITable tab) {
		connectionManager.initTable(tab);
	}

	public static void closeAllConnection() {
		connectionManager.closeAllConnection();
	}

}
