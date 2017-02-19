package com.huxuemin.xblog.infrastructure.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "discusses")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "discusses" })
public class DiscussDTOList {

	@XmlElement(name="discuss")
	private List<DiscussDTO> discusses;

	public List<DiscussDTO> getDiscusses() {
		return discusses;
	}

	public void setDiscusses(List<DiscussDTO> discusses) {
		this.discusses = discusses;
	}

}
