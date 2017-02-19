package com.huxuemin.xblog.infrastructure.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="articles")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "articles" })
public class ArticleSummaryDTOList {

	@XmlElement(name="article")
	private List<ArticleSummaryDTO> articles;

	public List<ArticleSummaryDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleSummaryDTO> articles) {
		this.articles = articles;
	}
	
}
