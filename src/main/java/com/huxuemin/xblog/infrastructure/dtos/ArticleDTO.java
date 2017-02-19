package com.huxuemin.xblog.infrastructure.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.gson.annotations.SerializedName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "article")
@XmlType(propOrder = { "id", "title", "content" })
public class ArticleDTO {

	@XmlElement
	@SerializedName("artileId")
	private long id;

	@XmlElement
	@SerializedName("articleTitle")
	private String title;

	@XmlElement
	@SerializedName("articleContent")
	private String content;

	public long getArticleId() {
		return id;
	}

	public void setArtileId(long artileId) {
		this.id = artileId;
	}

	public String getArticleTitle() {
		return title;
	}

	public void setArticleTitle(String title) {
		this.title = title;
	}

	public String getArticleContent() {
		return content;
	}

	public void setArticleContent(String articlecontent) {
		this.content = articlecontent;
	}

}
