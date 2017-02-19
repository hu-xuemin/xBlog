package com.huxuemin.xblog.database.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OneToManyDomainObject {
	public String foreignKeyColumnName();
	public Class foreignKeyDomainClass();
}
