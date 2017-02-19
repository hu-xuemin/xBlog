package com.huxuemin.xblog.domain.repository;

public class RepositoryRegister {
	public static UserRepository userR = new UserRepository();
	public static RoleRepository roleR = new RoleRepository();
	public static ArticleRepository articleR = new ArticleRepository();

	public static UserRepository getUserRepository(){
		return userR;
	}
	
	public static RoleRepository getRoleRepository(){
		return roleR;
	}
	
	public static ArticleRepository getArticleRepository(){
		return articleR;
	}

}
