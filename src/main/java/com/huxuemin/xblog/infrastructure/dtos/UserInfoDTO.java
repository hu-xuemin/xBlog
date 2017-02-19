package com.huxuemin.xblog.infrastructure.dtos;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import com.huxuemin.xblog.infrastructure.tools.CustomDateSerializer;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
@XmlType(propOrder = { "userName", "phoneNum", "address", "qq", "wechat", "email", "firstname", "lastname", "lang",
		"profession", "country", "sex", "birthday", "profile" })
public class UserInfoDTO {

	@XmlElement
	@SerializedName("username")
	private String userName;

	@XmlElement
	@SerializedName("phoneNum")
	private String phoneNum;

	@XmlElement
	@SerializedName("address")
	private String address;

	@XmlElement
	@SerializedName("qq")
	private String qq;

	@XmlElement
	@SerializedName("wechat")
	private String wechat;

	@XmlElement
	@SerializedName("email")
	private String email;

	@XmlElement
	@SerializedName("firstname")
	private String firstname;

	@XmlElement
	@SerializedName("lastname")
	private String lastname;

	@XmlElement
	@SerializedName("lang")
	private String lang;

	@XmlElement
	@SerializedName("profession")
	private String profession;

	@XmlElement
	@SerializedName("country")
	private String country;

	@XmlElement
	@SerializedName("sex")
	private String sex;

	@XmlElement
	@SerializedName("birthday")
	private Date birthday;

	@XmlElement
	@SerializedName("profile")
	private String profile;

	public UserInfoDTO() {
	}

	public UserInfoDTO(String username) {
		this.userName = username;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getUserName() {
		return userName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQQ() {
		return qq;
	}

	public void setQQ(String qQ) {
		qq = qQ;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
}
