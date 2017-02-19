package com.huxuemin.xblog.database.mapper;

import java.util.HashMap;
import java.util.Map;

public class MapperRegister {
	public static Map<Class, BaseMapper> map = new HashMap<Class, BaseMapper>();
	
	public static void register(Class cls,BaseMapper mapper){
		map.put(cls, mapper);
	}
	
	public static void unRegister(Class cls){
		map.remove(cls);
	}
	
	public static BaseMapper getMapper(Class cls){
		return map.get(cls);
	}
}
