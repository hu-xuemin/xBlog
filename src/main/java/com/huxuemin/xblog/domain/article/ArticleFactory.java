package com.huxuemin.xblog.domain.article;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.domain.repository.RepositoryRegister;
import com.huxuemin.xblog.domain.user.User;
import com.huxuemin.xblog.infrastructure.dtos.ArticleDTO;
import com.huxuemin.xblog.infrastructure.dtos.ArticleSummaryDTO;
import com.huxuemin.xblog.infrastructure.dtos.DiscussDTO;
import com.huxuemin.xblog.infrastructure.tools.HTMLEscape;
import com.huxuemin.xblog.infrastructure.tools.IDCreateFactory;

public class ArticleFactory {

	public static long getId() {
		return IDCreateFactory.getId();
	}

	public static ArticleDTO createArticleDTO(Article article) {
		if (article != null) {
			ArticleDTO dto = new ArticleDTO();
			dto.setArtileId(article.getArticleId());
			dto.setArticleTitle((article.getTitle()));
			dto.setArticleContent(article.getArticleContent());
			return dto;
		} else {
			return null;
		}
	}

	public static List<ArticleSummaryDTO> createArticleSummaryDTOs(List<Article> articles) {
		Iterator<Article> it = articles.iterator();
		List<ArticleSummaryDTO> articleSummarys = new ArrayList<ArticleSummaryDTO>();
		Article article;
		ArticleSummaryDTO as;
		while (it.hasNext()) {
			article = it.next();
			as = new ArticleSummaryDTO();
			as.setId(article.getArticleId());
			as.setTitle(article.getTitle());
			articleSummarys.add(as);
		}
		return articleSummarys;
	}
	
	public static List<DiscussDTO> createDiscussDTOs(Article articles, int begin, int end)
			throws DiscussNotFoundException {
		List<DiscussDTO> dtoList = new ArrayList<DiscussDTO>();
		if (articles != null) {
			List<Discuss> discussList = null;
			discussList = articles.getDiscusses(begin, end);
			Iterator<Discuss> it = discussList.iterator();
			while (it.hasNext()) {
				dtoList.add(createDiscussDTO(articles, it.next()));
			}
		}
		return dtoList;
	}

	public static DiscussDTO createDiscussDTO(Article articles, Discuss discuss) {
		DiscussDTO dto = new DiscussDTO(discuss.getDiscussId());
		dto.setContent(HTMLEscape.escape(discuss.getDiscussContent()));
		dto.setCreateOn(discuss.getCreateOn());
		dto.setOwerUserName(discuss.getUserName());
		dto.setOwerDisplayName(userNameTodisplayName(discuss.getUserName()));
		Discuss reply = articles.getDiscuss(discuss.getReplyId());
		if (reply != null) {
			dto.setReplyUserName(reply.getUserName());
			dto.setReplyDisName(userNameTodisplayName(reply.getUserName()));
		}
		return dto;
	}

	private static String userNameTodisplayName(String userName) {
		User user = RepositoryRegister.getUserRepository().get(userName);
		if (user != null) {
			return user.getUserDisplayName();
		} else {
			return "";
		}
	}

	public static Article createArticle(String username, String title, String articlecontent) {
		Article article = new Article(getId(), username, title, articlecontent);
		article.canDiscuss();
		article.classify("talk");
		article.top(new Date(System.currentTimeMillis()));
		return article;
	}
}
