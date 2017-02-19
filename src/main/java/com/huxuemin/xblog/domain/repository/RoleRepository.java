package com.huxuemin.xblog.domain.repository;

import com.huxuemin.xblog.domain.user.Role;

/**
 * @author huxuemin 2016��8��23��
 *
 */
public class RoleRepository extends IRepository<Role> {

	public RoleRepository() {
		super(Role.class);
	}

	@Override
	protected boolean comparePrimaryKeyPolicy(Role domainObject, Object primaryKey) {
		// TODO Auto-generated method stub
		if (domainObject.getName().equals(String.valueOf(primaryKey))) {
			return true;
		}
		return false;
	}
}
