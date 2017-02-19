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
@XmlRootElement(name = "discuss")
@XmlType(propOrder = { "id", "content", "replyUserName", "replyDisName", "owerUserName",
		"owerDisplayName", "createOn" })
public class DiscussDTO {

	@XmlElement
	@SerializedName("id")
	private long id;

	@XmlElement
	@SerializedName("content")
	private String content;

	@XmlElement
	@SerializedName("replyUserName")
	private String replyUserName;

	@XmlElement
	@SerializedName("replyDisName")
	private String replyDisName;

	@XmlElement
	@SerializedName("owerUserName")
	private String owerUserName;

	@XmlElement
	@SerializedName("owerDisplayName")
	private String owerDisplayName;

	@XmlElement
	@SerializedName("createOn")
	private Date createOn;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public DiscussDTO(){
	}
	
	public DiscussDTO(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}

	public String getOwerUserName() {
		return owerUserName;
	}

	public void setOwerUserName(String owerUserName) {
		this.owerUserName = owerUserName;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getReplyDisName() {
		return replyDisName;
	}

	public void setReplyDisName(String replyDisName) {
		this.replyDisName = replyDisName;
	}

	public String getOwerDisplayName() {
		return owerDisplayName;
	}

	public void setOwerDisplayName(String owerDisplayName) {
		this.owerDisplayName = owerDisplayName;
	}

}
