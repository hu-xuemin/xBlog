package com.huxuemin.xblog.domain.article;

import java.util.Date;

import com.huxuemin.xblog.database.mapper.OneToOneColumn;
import com.huxuemin.xblog.database.mapper.PrimaryKeyColumn;
import com.huxuemin.xblog.database.mapper.Table;
import com.huxuemin.xblog.infrastructure.DomainObject;

@Table(name="DISCUSSES")
public class Discuss extends DomainObject {

	@PrimaryKeyColumn(columnName="id")
	private long id;

	@OneToOneColumn(columnName="content")
	private String content;

	@OneToOneColumn(columnName="articleId")
	private long articleId;

	@OneToOneColumn(columnName="replyId")
	private long replyId;

	@OneToOneColumn(columnName="username")
	private String username;

	@OneToOneColumn(columnName="createOn")
	private Date createOn;
	public Discuss(){}
	public Discuss(long id,String content,long articleid,long replyid,String username,Date createon){
		this.id = id;
		this.content = content;
		this.articleId = articleid;
		this.replyId = replyid;
		this.username = username;
		this.createOn = createon;
		markNew();
	}

	public long getDiscussId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getDiscussContent() {
		// TODO Auto-generated method stub
		return this.content;
	}

	public long getReplyId() {
		// TODO Auto-generated method stub
		return this.replyId;
	}
	
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	public Date getCreateOn() {
		// TODO Auto-generated method stub
		return this.createOn;
	}

	public long getArticleId() {
		// TODO Auto-generated method stub
		return this.articleId;
	}
	
}
