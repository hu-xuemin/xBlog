package com.huxuemin.xblog.infrastructure.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class praseTools {
	/**
	 * String 转 long
	 * @param str 字符串
	 * @return long,转换出错则返回-1
	 */
	public static Long praseLong(String str){
		long l = -1;
		if(str != null && !str.equals("")){
			try {
				l = Long.parseLong(str);
			}catch (NumberFormatException e) {
			    e.printStackTrace();
			}
		}
		return l;
	}
	
	public static Date praseDate(String str){
		if(str != null && !str.equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				 return sdf.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
