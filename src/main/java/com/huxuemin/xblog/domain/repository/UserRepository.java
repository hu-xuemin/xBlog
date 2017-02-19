package com.huxuemin.xblog.domain.repository;

import com.huxuemin.xblog.domain.user.User;

/**
 * @author huxuemin 2016��8��23��
 *
 */
public class UserRepository extends IRepository<User>{
	
	public UserRepository(){
		super(User.class);
	}

	@Override
	protected boolean comparePrimaryKeyPolicy(User domainObject, Object primaryKey) {
		// TODO Auto-generated method stub
		if(domainObject.getUserName().equals(String.valueOf(primaryKey))){
			return true;
		}
		return false;
	}
	
}
