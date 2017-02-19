package com.huxuemin.xblog.domain.user;

import com.huxuemin.xblog.database.mapper.OneToOneColumn;
import com.huxuemin.xblog.database.mapper.PrimaryKeyColumn;
import com.huxuemin.xblog.database.mapper.Table;
import com.huxuemin.xblog.infrastructure.DomainObject;

@Table(name="userprivateinfo")
public class UserPrivateInfo extends DomainObject{

	@PrimaryKeyColumn(columnName="username")
	private String username;

	@OneToOneColumn(columnName="phoneNum")
	private String phoneNum;

	@OneToOneColumn(columnName="address")
	private String address;

	@OneToOneColumn(columnName="qq")
	private String QQ;

	@OneToOneColumn(columnName="wechat")
	private String wechat;

	@OneToOneColumn(columnName="email")
	private String Email;

	public UserPrivateInfo(){
	}
	public UserPrivateInfo(String username){
		this.username = username;
		markNew();
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
		markDirty();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		markDirty();
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
		markDirty();
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
		markDirty();
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
		markDirty();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		markDirty();
	}

}