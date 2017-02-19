package com.huxuemin.xblog.web.restAPI;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.huxuemin.xblog.application.UserService;
import com.huxuemin.xblog.domain.user.UserNotFoundException;
import com.huxuemin.xblog.domain.user.UserVerifyFailedException;
import com.huxuemin.xblog.infrastructure.AuthConstant;
import com.huxuemin.xblog.infrastructure.AuthException;
import com.huxuemin.xblog.infrastructure.ErrorMessage;
import com.huxuemin.xblog.infrastructure.SessionConstant;
import com.huxuemin.xblog.infrastructure.dtos.UserInfoDTO;

@RestController
@RequestMapping(value = "/api/user")
public class UserAPI {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<UserInfoDTO> userInfo(@PathVariable(value = "username") String usernameforinfo,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);
		UserInfoDTO dto = userService.getUserInfo(username, password, usernameforinfo);
		return new ResponseEntity<UserInfoDTO>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json", "application/xml",
			"application/x-www-form-urlencoded" }, produces = { "application/json", "application/xml" })
	public ResponseEntity<UserInfoDTO> register(@RequestParam(value = "user_login") String username,
			@RequestParam(value = "user_password") String password, UriComponentsBuilder ucb,
			HttpServletRequest request) {
		UserInfoDTO userInfo = userService.register(username, password);
		HttpHeaders header = new HttpHeaders();
		URI uri = ucb.path("/api/user/").path(username).build().toUri();
		header.setLocation(uri);
		return new ResponseEntity<UserInfoDTO>(userInfo, header, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.PUT, consumes = { "application/json",
			"application/xml",
			"application/x-www-form-urlencoded" }, produces = { "application/json", "application/xml" })
	public ResponseEntity<UserInfoDTO> edit(@PathVariable(value = "username") String username,
			@ModelAttribute UserInfoDTO userInfo, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);

		// System.out.println("logindId:" + loginId);
		// System.out.println("userInfo.getUserName():" +
		// userInfo.getUserName());

		if (!userInfo.getUserName().equals(username)) {
			throw new UserNotFoundException();
		}

		userService.modifyUserInfo(loginId, password, userInfo);
		return new ResponseEntity<UserInfoDTO>(userInfo, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{username}/canedit", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<String> canEdit(@PathVariable(value = "username") String username,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(SessionConstant.USERNAME);
		String password = (String) session.getAttribute(SessionConstant.PASSWORD);

		if (userService.hasEditUserInfoAuth(loginId, password, username)) {
			return new ResponseEntity<String>("{\"canedit\":\"true\"}",HttpStatus.OK);
		}
		throw new AuthException(AuthConstant.USER_MANAGER);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(1, exception.toString());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorMessage> authException(AuthException authException) {
		ErrorMessage error = new ErrorMessage(1, authException.toString());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UserVerifyFailedException.class)
	public ResponseEntity<ErrorMessage> userVerifyFailedException(UserVerifyFailedException exception) {
		ErrorMessage error = new ErrorMessage(1, exception.toString());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.FORBIDDEN);
	}
}
