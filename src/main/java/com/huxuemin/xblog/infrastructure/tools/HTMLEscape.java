package com.huxuemin.xblog.infrastructure.tools;

import java.util.regex.Pattern;

import com.huxuemin.xblog.infrastructure.RegexConstant;

public class HTMLEscape {
	public static String escape(String str){
		if(str != null){
			str = Pattern.compile(RegexConstant.HTMLLT).matcher(str).replaceAll("&lt;");
			return Pattern.compile(RegexConstant.HTMLGT).matcher(str).replaceAll("&gt;");
		}else{
			return str;
		}
	}
}
