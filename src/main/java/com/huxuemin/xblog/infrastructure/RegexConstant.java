package com.huxuemin.xblog.infrastructure;

public class RegexConstant {
	//^[a-zA-Z][a-zA-Z0-9_]*$
	public static final String USERNAME = "^[a-zA-Z][a-zA-Z0-9_]*$";
	//^[a-zA-Z](?:[\S]*[0-9_])+[\S]*$
	public static final String PASSWORD = "^[a-zA-Z](?:[\\S]*[0-9_])+[\\S]*$";
	//\d{3}-\d{8}|\d{4}-\{7,8}
	public static final String PHONE = "\\d{3}-\\d{8}|\\d{4}-\\{7,8}";
	//[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?
	public static final String EMAIL = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
	//[1-9][0-9]{4,}
	public static final String QQ = "[1-9][0-9]{4,}";
	// < : &#60; &lt;
	public static final String HTMLLT = "<(?!br)";
	// > : &#62; &gt;
	public static final String HTMLGT = "(?<!br)>";
}
