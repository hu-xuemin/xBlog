package com.huxuemin.xblog.domain.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.huxuemin.xblog.database.mapper.OneToOneColumn;
import com.huxuemin.xblog.database.mapper.PrimaryKeyColumn;
import com.huxuemin.xblog.database.mapper.Table;
import com.huxuemin.xblog.infrastructure.DomainObject;

@Table(name="userpublicinfo")
public class UserPublicInfo extends DomainObject{

	@PrimaryKeyColumn(columnName="username")
	private String username;

	@OneToOneColumn(columnName="firstname")
	private String firstname;

	@OneToOneColumn(columnName="lastname")
	private String lastname;

	@OneToOneColumn(columnName="lang")
	private String lang;

	@OneToOneColumn(columnName="profession")
	private String profession;

	@OneToOneColumn(columnName="country")
	private String country;

	@OneToOneColumn(columnName="sex")
	private String sex;

	@OneToOneColumn(columnName="birthday")
	private Date birthday;

	@OneToOneColumn(columnName="profile")
	private String profile;
	
	public UserPublicInfo(){}
	
	public UserPublicInfo(String username){
		this.username = username;
		try {
			birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1987-01-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		markNew();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
		markDirty();
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
		markDirty();
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
		markDirty();
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
		markDirty();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
		markDirty();
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		markDirty();
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
		markDirty();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		markDirty();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
		markDirty();
	}

}
