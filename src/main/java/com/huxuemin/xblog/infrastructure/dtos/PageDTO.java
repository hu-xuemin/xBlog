package com.huxuemin.xblog.infrastructure.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "page")
@XmlType(propOrder = {  "total" })
public class PageDTO {
	@XmlElement
	private int total = 0;

	public PageDTO() {
	}

	public PageDTO(int total) {
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
