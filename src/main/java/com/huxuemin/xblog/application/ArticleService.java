package com.huxuemin.xblog.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huxuemin.xblog.domain.article.Article;
import com.huxuemin.xblog.domain.article.ArticleFactory;
import com.huxuemin.xblog.domain.article.ArticleNotFoundException;
import com.huxuemin.xblog.domain.article.DiscussNotFoundException;
import com.huxuemin.xblog.domain.repository.RepositoryRegister;
import com.huxuemin.xblog.domain.repository.UnitOfWork;
import com.huxuemin.xblog.infrastructure.AuthConstant;
import com.huxuemin.xblog.infrastructure.AuthException;
import com.huxuemin.xblog.infrastructure.dtos.ArticleDTO;
import com.huxuemin.xblog.infrastructure.dtos.ArticleSummaryDTO;
import com.huxuemin.xblog.infrastructure.dtos.DiscussDTO;

@Service
public class ArticleService {

	@Autowired
	UserService userService;

	public Article publish(String title, String content, String username, String password) throws AuthException {
		if (userService.verify(username, password) && userService.hasAuth(username, AuthConstant.ARTICLE_MANAGER)) {
			UnitOfWork.newCurrent();
			Article article = ArticleFactory.createArticle(username, title, content);
			RepositoryRegister.getArticleRepository().add(article);
			UnitOfWork.getCurrent().commit();
			return article;
		} else {
			throw new AuthException(AuthConstant.ARTICLE_MANAGER);
		}
	}

	public Article edit(long id, String title, String content, String username, String password)
			throws AuthException, ArticleNotFoundException {
		if (userService.verify(username, password) && userService.hasAuth(username, AuthConstant.ARTICLE_MANAGER)) {
			Article article = RepositoryRegister.getArticleRepository().get(id);
			if (article != null) {
				UnitOfWork.newCurrent();
				article.edit(title, content);
				UnitOfWork.getCurrent().commit();
				return article;
			} else {
				throw new ArticleNotFoundException();
			}
		} else {
			throw new AuthException(AuthConstant.ARTICLE_MANAGER);
		}
	}

	public ArticleDTO getArticleDTO(long id) throws ArticleNotFoundException {
		Article article = RepositoryRegister.getArticleRepository().get(id);
		if (article != null) {
			return ArticleFactory.createArticleDTO(article);
		} else {
			throw new ArticleNotFoundException();
		}
	}

	public List<ArticleSummaryDTO> getArticleSummaryDTOs() {
		return ArticleFactory.createArticleSummaryDTOs(RepositoryRegister.getArticleRepository().getAll());
	}

	public DiscussDTO replyArticle(long articleid, long discussid, String content, String username, String password)
			throws AuthException {
		if (userService.verify(username, password) && userService.hasAuth(username, AuthConstant.PUBLIC_DISCUSS)) {
			Article article = RepositoryRegister.getArticleRepository().get(articleid);
			if (article != null) {
				UnitOfWork.newCurrent();
				DiscussDTO discuss = ArticleFactory.createDiscussDTO(article,
						article.replyDiscuss(username, discussid, content));
				UnitOfWork.getCurrent().commit();
				return discuss;
			}
		}
		throw new AuthException(AuthConstant.PUBLIC_DISCUSS);
	}

	public List<DiscussDTO> getAllDiscuss(long articleId) {
		Article article = RepositoryRegister.getArticleRepository().get(articleId);
		return ArticleFactory.createDiscussDTOs(article, 0, article.numberOfDiscuss());
	}

	public int numberOfDiscuss(long articleId) {
		return RepositoryRegister.getArticleRepository().get(articleId).numberOfDiscuss();
	}

	public List<DiscussDTO> getDiscuss(long articleId, int begin, int end) {
		return ArticleFactory.createDiscussDTOs(RepositoryRegister.getArticleRepository().get(articleId), begin, end);
	}

	public List<DiscussDTO> getDiscuss(long articleId, int pagenumber) {
		if (pagenumber > 0) {
			pagenumber = pagenumber - 1;
			return ArticleFactory.createDiscussDTOs(RepositoryRegister.getArticleRepository().get(articleId),
					pagenumber * 10, (pagenumber + 1) * 10);
		}
		throw new DiscussNotFoundException();
	}

	public int totalPageOfDiscuss(long articleId) {
		return (RepositoryRegister.getArticleRepository().get(articleId).numberOfDiscuss() + 9) / 10;
	}

	public void top(int id, long time, String username, String password) {

	}

	public void classify(int id, String category, String username, String password) {

	}

	public void closeDiscuss(int id, String username, String password) {

	}

	public void openDiscuss(int id, String username, String password) {

	}

	public void draft(long articleId, String username, String password) {
		if(userService.verify(username, password) && userService.hasAuth(username, AuthConstant.ARTICLE_MANAGER)){
			UnitOfWork.newCurrent();
			Article article = RepositoryRegister.getArticleRepository().get(articleId);
			RepositoryRegister.getArticleRepository().delete(article);
			UnitOfWork.getCurrent().commit();
		}else{
			throw new AuthException(AuthConstant.ARTICLE_MANAGER);
		}
	}

	public void recyle(int id, String username, String password) {

	}

	public void republish(int id, String username, String password) {

	}
}
