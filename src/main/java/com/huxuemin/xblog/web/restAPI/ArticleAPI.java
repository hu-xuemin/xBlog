package com.huxuemin.xblog.web.restAPI;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.huxuemin.xblog.application.ArticleService;
import com.huxuemin.xblog.domain.article.ArticleFactory;
import com.huxuemin.xblog.domain.article.ArticleNotFoundException;
import com.huxuemin.xblog.domain.article.DiscussNotFoundException;
import com.huxuemin.xblog.domain.user.UserVerifyFailedException;
import com.huxuemin.xblog.infrastructure.AuthException;
import com.huxuemin.xblog.infrastructure.ErrorMessage;
import com.huxuemin.xblog.infrastructure.SessionConstant;
import com.huxuemin.xblog.infrastructure.dtos.ArticleDTO;
import com.huxuemin.xblog.infrastructure.dtos.ArticleSummaryDTOList;
import com.huxuemin.xblog.infrastructure.dtos.DiscussDTO;
import com.huxuemin.xblog.infrastructure.dtos.DiscussDTOList;
import com.huxuemin.xblog.infrastructure.dtos.PageDTO;

@RestController
@RequestMapping(value = "/api/articles")
public class ArticleAPI {

	@Autowired
	private ArticleService articleService;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public ArticleSummaryDTOList articles() {
		ArticleSummaryDTOList articleList = new ArticleSummaryDTOList();
		articleList.setArticles(articleService.getArticleSummaryDTOs());
		return articleList;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json", "application/xml",
			"application/x-www-form-urlencoded" }, produces = { "application/json", "application/xml" })
	public ResponseEntity<ArticleDTO> publishNewArticle(@RequestParam(value = "articleTitle") String articleTitle,
			@RequestParam(value = "articleContent") String articleContent, UriComponentsBuilder ucb,
			HttpServletRequest request) {
		// ArticleService articleService =
		// RequestContextUtils.findWebApplicationContext(hsRequest).getBean(ArticleService.class);
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);
		ArticleDTO article = ArticleFactory
				.createArticleDTO(articleService.publish(articleTitle, articleContent, userName, password));
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/articles/").path(String.valueOf(article.getArticleId())).build().toUri();
		headers.setLocation(locationUri);
		ResponseEntity<ArticleDTO> responseEntity = new ResponseEntity<ArticleDTO>(article, headers, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/{articleId}", method = RequestMethod.PUT, consumes = { "application/json",
			"application/xml",
			"application/x-www-form-urlencoded" }, produces = { "application/json", "application/xml" })
	public ResponseEntity<ArticleDTO> updateArticle(@RequestParam(value = "articleTitle") String articleTitle,
			@RequestParam(value = "articleContent") String articleContent, @PathVariable long articleId,
			UriComponentsBuilder ucb, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);
		ArticleDTO article = ArticleFactory
				.createArticleDTO(articleService.edit(articleId, articleTitle, articleContent, username, password));
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/api/articles/").path(String.valueOf(article.getArticleId())).build().toUri();
		headers.setLocation(locationUri);
		ResponseEntity<ArticleDTO> responseEntity = new ResponseEntity<ArticleDTO>(article, headers,
				HttpStatus.ACCEPTED);
		return responseEntity;
	}

	@RequestMapping(value = "/{articleId}", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<ArticleDTO> article(@PathVariable long articleId) {
		return new ResponseEntity<ArticleDTO>(articleService.getArticleDTO(articleId), HttpStatus.OK);
	}

	@RequestMapping(value = "/{articleId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteAricle(@PathVariable long articleId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);
		articleService.draft(articleId, userName, password);
		return new ResponseEntity<String>("{\"message\":\"ok\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/{articleId}/discuss", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public DiscussDTOList discuss(@PathVariable long articleId) {
		DiscussDTOList discusses = new DiscussDTOList();
		discusses.setDiscusses(articleService.getAllDiscuss(articleId));
		return discusses;
	}

	@RequestMapping(value = "/{articleId}/discuss/page/{pageno}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public DiscussDTOList discussForRefresh(@PathVariable long articleId, @PathVariable int pageno) {
		DiscussDTOList discusses = new DiscussDTOList();
		discusses.setDiscusses(articleService.getDiscuss(articleId, pageno));
		return discusses;
	}

	@RequestMapping(value = "/{articleId}/discuss/page", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public PageDTO discussForRefresh(@PathVariable long articleId) {
		return new PageDTO(articleService.totalPageOfDiscuss(articleId));
	}

	@RequestMapping(value = "/{articleId}/discuss", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" }, consumes = { "application/json", "application/xml",
					"application/x-www-form-urlencoded" })
	public ResponseEntity<DiscussDTO> replyArticle(@PathVariable long articleId,
			@RequestParam(value = "content") String content, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);
		DiscussDTO discuss = articleService.replyArticle(articleId, -1L, content, username, password);
		return new ResponseEntity<DiscussDTO>(discuss, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{articleId}/discuss/{discussId}", method = RequestMethod.POST, produces = {
			"application/json", "application/xml" }, consumes = { "application/json", "application/xml",
					"application/x-www-form-urlencoded" })
	public ResponseEntity<DiscussDTO> replyDiscuss(@PathVariable long articleId, @PathVariable long discussId,
			@RequestParam(value = "content") String content, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);
		DiscussDTO discuss = articleService.replyArticle(articleId, discussId, content, username, password);
		return new ResponseEntity<DiscussDTO>(discuss, HttpStatus.ACCEPTED);
	}

	@ExceptionHandler(DiscussNotFoundException.class)
	public ResponseEntity<ErrorMessage> discussNotFoundException(DiscussNotFoundException exception) {
		ErrorMessage error = new ErrorMessage(1, exception.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ArticleNotFoundException.class)
	public ResponseEntity<ErrorMessage> articleNotFoundException(ArticleNotFoundException exception) {
		ErrorMessage error = new ErrorMessage(1, exception.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorMessage> authException(AuthException authException) {
		ErrorMessage error = new ErrorMessage(1, authException.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UserVerifyFailedException.class)
	public ResponseEntity<ErrorMessage> userVerifyFailedException(UserVerifyFailedException exception) {
		ErrorMessage error = new ErrorMessage(1, exception.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.FORBIDDEN);
	}

}
