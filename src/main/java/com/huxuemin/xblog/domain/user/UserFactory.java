package com.huxuemin.xblog.domain.user;

import com.huxuemin.xblog.domain.repository.RepositoryRegister;
import com.huxuemin.xblog.infrastructure.dtos.UserInfoDTO;
import com.huxuemin.xblog.infrastructure.tools.IDCreateFactory;
import com.huxuemin.xblog.infrastructure.tools.MD5Tools;

public class UserFactory {
	//static long userid = 100;
	//hashedpassword: 8fe73152a9ffdde3cde40d1120c3c02b
	//salt: 2d07a6f758d2fcac6a88bade6e915d4c
	
	public static long getId(){
		return IDCreateFactory.getId();
	}
	
	/**
	 * ����һ���û�
	 * @param username �û���
	 * @param password ����
	 * @return �û�
	 */
	public static User createUser(String username,String password){
		User user = new User(username);
		String salt = "";
		String hashedPassword = "";
		try {
			salt = MD5Tools.md5Encode(username + String.valueOf(System.currentTimeMillis()));
			hashedPassword = MD5Tools.md5Encode(salt + password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setSalt(salt);
		user.setHashedPassword(hashedPassword);
		user.addRole(RepositoryRegister.getRoleRepository().get("user"));
		return user;
	}
	
	public static Role createRole(String rolename){
		return new Role(rolename);
	}
	
	public static void modifyUserInfo(UserInfoDTO userInfoDTO){
		User infoUser = RepositoryRegister.getUserRepository().get(userInfoDTO.getUserName());
		infoUser.setFirstname(userInfoDTO.getFirstName());
		infoUser.setLastname(userInfoDTO.getLastName());
		infoUser.setLang(userInfoDTO.getLang());
		infoUser.setCountry(userInfoDTO.getCountry());
		infoUser.setProfession(userInfoDTO.getProfession());
		infoUser.setSex(userInfoDTO.getSex());
		infoUser.setBirthday(userInfoDTO.getBirthday());
		infoUser.setProfile(userInfoDTO.getProfile());
		infoUser.setQQ(userInfoDTO.getQQ());
		infoUser.setPhoneNum(userInfoDTO.getPhoneNum());
		infoUser.setEmail(userInfoDTO.getEmail());
		infoUser.setAddress(userInfoDTO.getAddress());
		infoUser.setWechat(userInfoDTO.getWechat());
	}
	
	public static UserInfoDTO createUserInfoDTO(User usersinfo){
		UserPublicInfo publicInfo = usersinfo.getPublicInfo();
		UserInfoDTO dto = new UserInfoDTO(publicInfo.getUsername());
		dto.setFirstName(publicInfo.getFirstname());
		dto.setLastName(publicInfo.getLastname());
		dto.setLang(publicInfo.getLang());
		dto.setCountry(publicInfo.getCountry());
		dto.setProfession(publicInfo.getProfession());
		dto.setSex(publicInfo.getSex());
		dto.setBirthday(publicInfo.getBirthday());
		dto.setProfile(publicInfo.getProfile());
		return dto;
	}
	
	public static UserInfoDTO createFullUserInfoDTO(User usersinfo){
		UserInfoDTO dto = createUserInfoDTO(usersinfo);
		UserPrivateInfo privateInfo = usersinfo.getPrivateInfo();
		dto.setQQ(privateInfo.getQQ());
		dto.setPhoneNum(privateInfo.getPhoneNum());
		dto.setEmail(privateInfo.getEmail());
		dto.setAddress(privateInfo.getAddress());
		dto.setWechat(privateInfo.getWechat());
		return dto;
	}
}
