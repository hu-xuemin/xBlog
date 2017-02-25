package com.huxuemin.xblog.infrastructure;

/**
 * @author huxuemin
 *
 */
public enum AuthConstant {

	USER_MANAGER(101,"user management rights"),

	ROLE_MANAGER(102,"role managerment rights"),

	ARTICLE_MANAGER(103,"article managerment rights"),

	DISCUSS_MANAGER(104,"discuss managerment rights"),

	PUBLIC_DISCUSS(201,"publish discuss rights"),

    MANAGE_PAGE(301,"access manage page rights");
    
	int num;

	String desc;

	private AuthConstant(int num,String desc){
		this.num = num;
		this.desc = desc;
	}

	public int numOfAuth(){
		return num;
	}
	
	public String descOfAuth(){
		return desc;
	}
	
}
