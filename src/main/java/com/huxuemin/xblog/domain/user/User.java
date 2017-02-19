package com.huxuemin.xblog.domain.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.database.mapper.OneToManyColumn;
import com.huxuemin.xblog.database.mapper.OneToOneColumn;
import com.huxuemin.xblog.database.mapper.OneToOneDomainObject;
import com.huxuemin.xblog.database.mapper.PrimaryKeyColumn;
import com.huxuemin.xblog.database.mapper.Table;
import com.huxuemin.xblog.domain.repository.RepositoryRegister;
import com.huxuemin.xblog.infrastructure.AuthConstant;
import com.huxuemin.xblog.infrastructure.DomainObject;
import com.huxuemin.xblog.infrastructure.tools.IDCreateFactory;
import com.huxuemin.xblog.infrastructure.tools.MD5Tools;

@Table(name="users")
public class User extends DomainObject {
	
	@OneToOneColumn(columnName="id")
	private long id;

	@PrimaryKeyColumn(columnName="username")
	private String userName;

	@OneToOneColumn(columnName="hashedpassword")
	private String hashedPassword;

	@OneToOneColumn(columnName="salt")
	private String salt;

	@OneToManyColumn(columnName = "rolename", foreignKeyColumnName = "username", foreigntableName = "userrole")
	private List<String> roles = new ArrayList<String>();
	
	@OneToOneColumn(columnName="accountLock")
	private boolean accountLock = false;

	@OneToOneColumn(columnName="lastloginon")
	private Date lastLoginOn = new Date(System.currentTimeMillis());

	@OneToOneColumn(columnName="updateon")
	private Date updateOn = new Date(System.currentTimeMillis());

	@OneToOneColumn(columnName="mustchangepassword")
	private boolean mustChangePassword = false;

	@OneToOneColumn(columnName="passwordchangeon")
	private Date passwordChangeOn = new Date(System.currentTimeMillis());


	@OneToOneColumn(columnName="usercreateon")
	private Date userCreateOn = new Date(System.currentTimeMillis());

	@OneToOneDomainObject(foreignKeyColumnName="username", foreignKeyDomainClass = UserPrivateInfo.class)
	private UserPrivateInfo privateInfo;

	@OneToOneDomainObject(foreignKeyColumnName="username", foreignKeyDomainClass = UserPublicInfo.class)
	private UserPublicInfo publicInfo;
	
	public User() {}
	
	public User(String name) {
		this.id = IDCreateFactory.getId();
		this.userName = name;
		this.roles = new ArrayList<String>();
		this.privateInfo = new UserPrivateInfo(name);
		this.publicInfo = new UserPublicInfo(name);
		markNew();
	}

	void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	void setSalt(String salt) {
		this.salt = salt;
	}

	UserPublicInfo getPublicInfo(){
		return this.publicInfo;
	}
	
	UserPrivateInfo getPrivateInfo(){
		return this.privateInfo;
	}

	private void updateMustChangePassword() {
		if (mustChangePassword) {
			mustChangePassword = false;
		}
	}

	private void updatePasswordChangeOn() {
		this.passwordChangeOn = new Date(System.currentTimeMillis());
	}

	private void updateLastLoginOn() {
		this.lastLoginOn = new Date(System.currentTimeMillis());
	}
/*
	private void updateUpdateOn() {
		this.updateOn = new Date(System.currentTimeMillis());
	}
*/
	public String getUserName() {
		return this.userName;
	}

	public long getUserId() {
		return this.id;
	}

	public boolean checkPassword(String password) {
		boolean result = false;
		if (!accountLock && !mustChangePassword) {
			try {
				if (password != null && hashedPassword.equals(MD5Tools.md5Encode(this.salt + password))) {
					result = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateLastLoginOn();
		}
		return result;
	}

	public void changePassword(String oldPassword, String newPassword) {
		if (checkPassword(oldPassword)) {
			try {
				String s = MD5Tools.md5Encode(userName + String.valueOf(System.currentTimeMillis()));
				String hp = MD5Tools.md5Encode(s + newPassword);
				salt = s;
				hashedPassword = hp;
				updateMustChangePassword();
				updatePasswordChangeOn();
				markDirty();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void newPassword(String newPassword) {
		try {
			String s = MD5Tools.md5Encode(userName + String.valueOf(System.currentTimeMillis()));
			String hp = MD5Tools.md5Encode(s + newPassword);
			salt = s;
			hashedPassword = hp;
			updateMustChangePassword();
			updatePasswordChangeOn();
			markDirty();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addRole(Role role) {
		if (role != null && !hasRole(role)) {
			roles.add(role.getName());
		}
	}

	public void removeRole(Role role) {
		roles.remove(role.getName());
	}

	public boolean hasRole(Role role) {
		return roles.contains(role.getName());
	}

	public boolean hasAuth(AuthConstant auth) {
		boolean result = false;
		Iterator<String> it = roles.iterator();
		while (!result && it.hasNext()) {
			result = getRole(it.next()).hasAuth(auth);
		}
		return result;
	}
	
	private Role getRole(String rolename){
		return RepositoryRegister.getRoleRepository().get(rolename);
	}
	
	public String getUserDisplayName() {
		String displyName = "";
		String firstName = this.publicInfo.getFirstname();
		String lastName = this.publicInfo.getLastname();
		if (firstName != null && !firstName.equals("")) {
			displyName = firstName;
		}
		if (lastName != null && !lastName.equals("")) {
			displyName = displyName + " " + lastName;
		}
		if (displyName.equals("")) {
			displyName = this.userName;
		}
		return displyName;
	}

	public void mustChangePassword() {
		// TODO Auto-generated method stub
		this.mustChangePassword = true;
	}

	public void lock() {
		// TODO Auto-generated method stub
		this.accountLock = true;
	}

	public void unLock() {
		// TODO Auto-generated method stub
		this.accountLock = false;
	}

	public boolean passwordIsInvalid() {
		// TODO Auto-generated method stub
		return mustChangePassword;
	}

	public boolean isLock() {
		// TODO Auto-generated method stub
		return accountLock;
	}

	public Date getLastLoginOn() {
		// TODO Auto-generated method stub
		return this.lastLoginOn;
	}

	public Date getUpdateOn() {
		// TODO Auto-generated method stub
		return this.updateOn;
	}

	public Date getPasswrodChangeOn() {
		// TODO Auto-generated method stub
		return this.passwordChangeOn;
	}

	public Date getUserCreateOn() {
		return this.userCreateOn;
	}
	
	public void setPhoneNum(String phoneNum) {
		this.privateInfo.setPhoneNum(phoneNum);
	}
	
	public void setAddress(String address) {
		this.privateInfo.setAddress(address);
	}
	
	public void setQQ(String qQ) {
		this.privateInfo.setQQ(qQ);
	}
	
	public void setWechat(String wechat) {
		this.privateInfo.setWechat(wechat);
	}
	
	public void setEmail(String email) {
		this.privateInfo.setEmail(email);
	}

	public void setFirstname(String firstname) {
		this.publicInfo.setFirstname(firstname);
	}

	public void setLastname(String lastname) {
		this.publicInfo.setLastname(lastname);
	}
	
	public void setLang(String lang) {
		this.publicInfo.setLang(lang);
	}
	
	public void setProfession(String profession) {
		this.publicInfo.setProfession(profession);
	}
	
	public void setSex(String sex) {
		this.publicInfo.setSex(sex);
	}
	
	public void setBirthday(Date birthday) {
		this.publicInfo.setBirthday(birthday);
	}
	
	public void setProfile(String profile) {
		this.publicInfo.setProfile(profile);
	}
	
	public void setCountry(String country) {
		this.publicInfo.setCountry(country);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
