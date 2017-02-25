package com.huxuemin.xblog.domain.article;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.database.mapper.OneToManyDomainObject;
import com.huxuemin.xblog.database.mapper.OneToOneColumn;
import com.huxuemin.xblog.database.mapper.PrimaryKeyColumn;
import com.huxuemin.xblog.database.mapper.Table;
import com.huxuemin.xblog.infrastructure.AuthConstant;
import com.huxuemin.xblog.infrastructure.AuthException;
import com.huxuemin.xblog.infrastructure.DomainObject;

@Table(name = "ARTICLES")
public class Article extends DomainObject {

	@PrimaryKeyColumn(columnName = "id")
	private long id;

	@OneToOneColumn(columnName = "owerusername")
	private String owerusername;

	@OneToOneColumn(columnName = "title")
	private String title;

	@OneToOneColumn(columnName = "content")
	private String articlecontent;

	@OneToOneColumn(columnName = "topTime")
	private Date topTime;

	@OneToOneColumn(columnName = "updateTime")
	private Date updateTime;

	@OneToOneColumn(columnName = "category")
	private String category;

	@OneToOneColumn(columnName = "canDiscussStatus")
	private boolean canDiscussStatus = true;

	@OneToOneColumn(columnName = "articleStatus")
	private int articleStatus = 0;

	@OneToManyDomainObject(foreignKeyColumnName = "articleId", foreignKeyDomainClass = Discuss.class)
	private List<Discuss> disscusses = new ArrayList<Discuss>();

	@OneToOneColumn(columnName = "lastreplytime")
	private Date lastReplyTime;

	private boolean isSort = false;

	public Article() {
	}

	public Article(long id, String owerusername, String title, String articlecontent) {
		this.id = id;
		this.owerusername = owerusername;
		this.title = title;
		this.articlecontent = articlecontent;
		refreshUpdateTime();
		refreshLastReplyTime(new Date(System.currentTimeMillis()));
		this.markNew();
	}

	public void setArticleInfo(long id, String owerusername, String title, String articlecontent) {
		this.id = id;
		this.owerusername = owerusername;
		this.title = title;
		this.articlecontent = articlecontent;
		markDirty();
	}

	private void refreshUpdateTime() {
		this.updateTime = new Date(System.currentTimeMillis());
	}

	private void refreshLastReplyTime(Date date) {
		this.lastReplyTime = date;
	}

	public long getArticleId() {
		return this.id;
	}

	public String getOwerUserName() {
		return this.owerusername;
	}

	public String getArticleContent() {
		// TODO Auto-generated method stub
		return this.articlecontent;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	public void edit(String title, String articlecontent) {
		// TODO Auto-generated method stub
		this.title = title;
		this.articlecontent = articlecontent;
		refreshUpdateTime();
		this.markDirty();
	}

	public void top(Date time) {
		// TODO Auto-generated method stub
		this.topTime = time;
		this.markDirty();
	}

	public Date getTop() {
		if (this.topTime != null && this.updateTime != null) {
			if (this.topTime.after(this.updateTime)) {
				return this.topTime;
			} else {
				return this.updateTime;
			}
		} else {
			return this.updateTime;
		}
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void classify(String category) {
		// TODO Auto-generated method stub
		this.category = category;
		this.markDirty();
	}

	public String getCategory() {
		return this.category;
	}

	public boolean canReply() {
		this.markDirty();
		return canDiscussStatus && articleStatus == 0;
	}

	public void closeDiscuss() {
		// TODO Auto-generated method stub
		this.canDiscussStatus = false;
		this.markDirty();
	}

	public void openDiscuss() {
		// TODO Auto-generated method stub
		this.canDiscussStatus = true;
		this.markDirty();
	}

	public void draft() {
		// TODO Auto-generated method stub
		this.articleStatus = 1;
		this.markDirty();
	}

	public void recyle() {
		// TODO Auto-generated method stub
		this.articleStatus = 2;
		this.markDirty();
	}

	public void republish() {
		// TODO Auto-generated method stub
		this.articleStatus = 0;
		refreshUpdateTime();
		this.markDirty();
	}

	private boolean isSort() {
		if (isSort) {
			return isSort;
		}
		disscusses.sort(new DiscussComparator());
		isSort = true;
		return isSort;
	}

	public List<Discuss> getDiscusses(int begin, int end) throws DiscussNotFoundException {
		if (isSort()) {
			if (begin < 0) {
				begin = 0;
			}

			if (begin >= disscusses.size() || end < begin) {
				throw new DiscussNotFoundException();
			}

			if (end > disscusses.size()) {
				end = disscusses.size();
			}

			return disscusses.subList(begin, end);
		}
		throw new DiscussNotFoundException();
	}

	public List<Discuss> getDiscusses(int begin) throws DiscussNotFoundException {
		if (isSort()) {
			return getDiscusses(begin, disscusses.size());
		}
		throw new DiscussNotFoundException();
	}

	public List<Discuss> getAllDiscusses() {
		if (isSort()) {
			return disscusses;
		}
		throw new DiscussNotFoundException();
	}
	
	public int numberOfDiscuss(){
		return disscusses.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Discuss replyArticle(String username, String content) {
		// TODO Auto-generated method stub
		Discuss discuss = null;
		if (canDiscussStatus && content != null && content.length() > 2) {
			Date date = new Date(System.currentTimeMillis());
			discuss = new Discuss(ArticleFactory.getId(), content, id, id, username, date);
			disscusses.add(discuss);
			refreshLastReplyTime(date);
	        return discuss;
		}

        throw new AuthException(AuthConstant.PUBLIC_DISCUSS);
	}

	public Discuss replyDiscuss(String username, long discussId, String content) {
		// TODO Auto-generated method stub
		Discuss discuss = null;
		if (canDiscussStatus && content != null && content.length() > 2) {
			Date date = new Date(System.currentTimeMillis());
			if (disscussIsExist(discussId)) {
				discuss = new Discuss(ArticleFactory.getId(), content, id, discussId, username, date);
			} else {
				discuss = new Discuss(ArticleFactory.getId(), content, id, id, username, date);
			}
			disscusses.add(discuss);
			refreshLastReplyTime(date);
	        return discuss;
		}
		
		throw new AuthException(AuthConstant.PUBLIC_DISCUSS);
	}

	public Date lastReplyTime() {
		return this.lastReplyTime;
	}

	public Discuss getDiscuss(long discussId) {
		// TODO Auto-generated method stub
		Iterator<Discuss> it = disscusses.iterator();
		while (id > 0 && it.hasNext()) {
			Discuss discuss = it.next();
			if (discuss.getDiscussId() == id) {
				return discuss;
			}
		}
		return null;
	}

	private boolean disscussIsExist(long id) {
		Iterator<Discuss> it = disscusses.iterator();
		while (id > 0 && it.hasNext()) {
			if (it.next().getDiscussId() == id) {
				return true;
			}
		}
		return false;
	}
}
