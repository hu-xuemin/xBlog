package com.huxuemin.xblog.infrastructure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.gson.annotations.SerializedName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
@XmlType(name = "error", propOrder = { "code", "message" })
public class ErrorMessage {

	@XmlElement
	@SerializedName("code")
	private int code;

	@XmlElement
	@SerializedName("message")
	private String message;

	public ErrorMessage(){
		this.code = -1;
		this.message = "unKnow Error!";
	}
	
	public ErrorMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return message;
	}
}
