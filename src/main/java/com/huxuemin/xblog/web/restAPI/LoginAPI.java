package com.huxuemin.xblog.web.restAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huxuemin.xblog.application.UserService;
import com.huxuemin.xblog.domain.user.UserNotFoundException;
import com.huxuemin.xblog.domain.user.UserVerifyFailedException;
import com.huxuemin.xblog.infrastructure.ErrorMessage;
import com.huxuemin.xblog.infrastructure.SessionConstant;
import com.huxuemin.xblog.infrastructure.dtos.DisplayNameDTO;
import com.huxuemin.xblog.infrastructure.dtos.UserInfoDTO;

@RestController
@RequestMapping(value = "/api/login")
public class LoginAPI {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" }, consumes = { "application/json", "application/xml",
	"application/x-www-form-urlencoded" })
	public ResponseEntity<UserInfoDTO> loginIn(@RequestParam(value = "loginId") String loginid,
			@RequestParam(value = "password") String password, HttpServletRequest hsRequest) {

		if (userService.verify(loginid, password)) {
			HttpSession session = hsRequest.getSession();
			session.setAttribute(SessionConstant.USERNAME, loginid);
			session.setAttribute(SessionConstant.PASSWORD, password);
			UserInfoDTO info = userService.getUserInfo(loginid, password, loginid);
			return new ResponseEntity<UserInfoDTO>(info, HttpStatus.OK);
		}
		throw new UserVerifyFailedException();
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml"})
	public ResponseEntity<DisplayNameDTO> loginInfo(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(SessionConstant.USERNAME);
		if (username != null) {
			DisplayNameDTO dis = new DisplayNameDTO(username);
			dis.setDisplayName(userService.getUserDisplayName(username));
			return new ResponseEntity<DisplayNameDTO>(dis, HttpStatus.OK);
		}
		throw new UserNotFoundException();
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = { "application/json", "application/xml"})
	public ResponseEntity<String> LoginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(SessionConstant.USERNAME);
		
		if(username != null){
			session.invalidate();
			return new ResponseEntity<String>("{\"message\":\"logout\"}",HttpStatus.OK);
		}
		throw new UserNotFoundException();
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception){
		ErrorMessage errorMessage = new ErrorMessage(1,exception.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMessage,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserVerifyFailedException.class)
	public ResponseEntity<ErrorMessage> userVerifyFailedException(UserVerifyFailedException exception){
		ErrorMessage errorMessage = new ErrorMessage(1,exception.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMessage,HttpStatus.FORBIDDEN);
	}

}
