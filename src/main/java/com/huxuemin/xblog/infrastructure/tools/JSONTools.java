package com.huxuemin.xblog.infrastructure.tools;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huxuemin.xblog.infrastructure.ErrorMessage;

public class JSONTools {
	
	public static String JSONMsg(int statusValue,String msg){
		ErrorMessage obj = new ErrorMessage(statusValue, msg);  
		Gson gson = new GsonBuilder().create();  
		return gson.toJson(obj);
	}
	
	public static String ObjectToJSON(Object obj){
		Gson gson = new GsonBuilder().create();  
		return gson.toJson(obj);
	}
	
	public static String ListToJSON(List<?> objs){
		Gson gson = new GsonBuilder().create();  
		return gson.toJson(objs);
	}
}
