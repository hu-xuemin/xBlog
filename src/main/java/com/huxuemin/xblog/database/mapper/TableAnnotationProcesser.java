package com.huxuemin.xblog.database.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.huxuemin.xblog.infrastructure.tools.ClasspathPackageScanner;

public class TableAnnotationProcesser {
	
	private final static Class<Table> tableClass = Table.class;
	
	private static String getTableName(Class<?> bean){
			Table table = bean.getAnnotation(tableClass);
			return table.name();
	}
	
	public static Map<Class<?>,String> scanClassToTablenameMaps(String packageName){
		ClasspathPackageScanner cps = new ClasspathPackageScanner(packageName);
		Map<Class<?>,String> classAndTablename = new HashMap<Class<?>,String>();
		try {
			List<String> allPackageClassName = cps.getFullyQualifiedClassNameList();
			for(Iterator<String> it = allPackageClassName.iterator();it.hasNext();){
				String className = it.next();
				Class<?> klass = Class.forName(className);
				if(klass.isAnnotationPresent(tableClass)){
					classAndTablename.put(klass, getTableName(klass));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classAndTablename;
	}
	
}
