package com.huxuemin.xblog.application;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huxuemin.xblog.domain.repository.RepositoryRegister;
import com.huxuemin.xblog.domain.repository.UnitOfWork;
import com.huxuemin.xblog.domain.user.User;
import com.huxuemin.xblog.domain.user.UserFactory;
import com.huxuemin.xblog.domain.user.UserNotFoundException;
import com.huxuemin.xblog.domain.user.UserVerifyFailedException;
import com.huxuemin.xblog.infrastructure.AuthConstant;
import com.huxuemin.xblog.infrastructure.AuthException;
import com.huxuemin.xblog.infrastructure.RegexConstant;
import com.huxuemin.xblog.infrastructure.dtos.UserInfoDTO;

@Service
public class UserService {

	@Autowired
	ArticleService articleService;

	public boolean verify(String username, String password) throws UserVerifyFailedException {
		if (regexCheckUsername(username) && regexCheckPassword(password)) {
			User user = RepositoryRegister.getUserRepository().get(username);
			if (user != null && user.checkPassword(password)) {
				return true;
			}
		}
		throw new UserVerifyFailedException();
	}

	public void testPublish() {
		System.out.println("testPublish()");
		articleService.publish("afsdf", "sfsadf", "admin", "admin123");
	}

	public UserInfoDTO getUserInfo(String username, String password, String usernameforinfo)
			throws UserNotFoundException {
		if (regexCheckUsername(usernameforinfo)) {
			User usersinfo = RepositoryRegister.getUserRepository().get(usernameforinfo);
			if (usersinfo != null) {
				if (regexCheckUsername(username) && regexCheckPassword(password)) {
					User user = RepositoryRegister.getUserRepository().get(username);
					if (user.checkPassword(password)) {
						if (user.equals(usersinfo) || user.hasAuth(AuthConstant.USER_MANAGER)) {
							return UserFactory.createFullUserInfoDTO(usersinfo);
						}
					}
				}
				return UserFactory.createUserInfoDTO(usersinfo);
			}
		}
		throw new UserNotFoundException();
	}

	public void modifyUserInfo(String username, String password, UserInfoDTO userInfoDTO) throws AuthException {
		if (regexCheckUsername(userInfoDTO.getUserName())) {
			User infoUser = RepositoryRegister.getUserRepository().get(userInfoDTO.getUserName());
			if (regexCheckUsername(username) && regexCheckPassword(password)) {
				User user = RepositoryRegister.getUserRepository().get(username);
				if (user.checkPassword(password)) {
					if (user.equals(infoUser) || user.hasAuth(AuthConstant.USER_MANAGER)) {
						UnitOfWork.newCurrent();
						UserFactory.modifyUserInfo(userInfoDTO);
						UnitOfWork.getCurrent().commit();
						return;
					}
				}
			}
		}
		throw new AuthException(AuthConstant.USER_MANAGER);
	}

	public UserInfoDTO register(String username, String password) {
		if (regexCheckUsername(username) && regexCheckPassword(password)) {
			if (!RepositoryRegister.getUserRepository().isExistByPrimaryKey(username)) {
				UnitOfWork.newCurrent();
				User user = UserFactory.createUser(username, password);
				RepositoryRegister.getUserRepository().add(user);
				UnitOfWork.getCurrent().commit();
				return UserFactory.createUserInfoDTO(user);
			}
		}
		throw new UserNotFoundException();
	}

	public String getUserDisplayName(String username) throws UserNotFoundException {
		User user = RepositoryRegister.getUserRepository().get(username);
		if (user != null) {
			return user.getUserDisplayName();
		}
		throw new UserNotFoundException();
	}

	public boolean hasAuth(String username, AuthConstant auth) {
		boolean result = false;
		User user = RepositoryRegister.getUserRepository().get(username);
		if (user != null && user.hasAuth(auth)) {
			result = true;
		}
		return result;
	}

	public boolean hasEditUserInfoAuth(String username, String password, String targetUser) {
		if (regexCheckUsername(targetUser)) {
			User usersinfo = RepositoryRegister.getUserRepository().get(targetUser);
			if (usersinfo != null && regexCheckUsername(username) && regexCheckPassword(password)) {
				User user = RepositoryRegister.getUserRepository().get(username);
				if (user!= null && user.checkPassword(password)) {
					if (user.equals(usersinfo) || user.hasAuth(AuthConstant.USER_MANAGER)) {
						return true;
					}
				}
			}
		}
		throw new AuthException(AuthConstant.USER_MANAGER);
	}

	private boolean regexCheckUsername(String username) {
		if (username != null) {
			return Pattern.compile(RegexConstant.USERNAME).matcher(username).matches();
		} else {
			return false;
		}
	}

	private boolean regexCheckPassword(String password) {
		if (password != null) {
			return Pattern.compile(RegexConstant.PASSWORD).matcher(password).matches();
		} else {
			return false;
		}
	}

}
