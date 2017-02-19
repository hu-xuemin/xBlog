package com.huxuemin.xblog.test.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.huxuemin.xblog.config.RootConfig;
import com.huxuemin.xblog.database.DBConnectionFactory;
import com.huxuemin.xblog.infrastructure.SessionConstant;
import com.huxuemin.xblog.web.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({ @ContextConfiguration(name = "parent", classes = RootConfig.class),
		@ContextConfiguration(name = "child", classes = WebConfig.class) })
public class RestApiWebAppContextTest {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		DBConnectionFactory.initDataBaseContext();
	}

	@After
	public void destory() {
		DBConnectionFactory.closeAllConnection();
	}

	@Test
	public void testLogin() throws Exception {
		// get
		mockMvc.perform(MockMvcRequestBuilders.get("/api/login").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(404));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/login").sessionAttr(SessionConstant.USERNAME, "admin")
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().is(200));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/login").sessionAttr(SessionConstant.USERNAME, "abcdefg")
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().is(404));

		// delete
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/login").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(404));
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/login").sessionAttr(SessionConstant.USERNAME, "admin")
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().is(200));

		// post
		mockMvc.perform(MockMvcRequestBuilders.post("/api/login").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).content("loginId=admin&password=admin123"))
				.andExpect(MockMvcResultMatchers.status().is(200));
		mockMvc.perform(MockMvcRequestBuilders.post("/api/login").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).content("loginId=admin&password=admin1234"))
				.andExpect(MockMvcResultMatchers.status().is(403));
		mockMvc.perform(MockMvcRequestBuilders.post("/api/login").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).content("loginId=abcdefg&password=admin1234"))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	public void testUser() throws Exception {
		// userName=admin&firstName=hu&lastName=xuemin&country=cn&lang=zh&profession=it&qq=397997401&phoneNum=13066961905&wechat=hu-xuemin&email=397997401%40qq.com&sex=man&profile=admin
		// LinkedMultiValueMap<String, String> adminInfo = new
		// LinkedMultiValueMap<String, String>();

		// get
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin").sessionAttr(SessionConstant.USERNAME, "admin")
				.sessionAttr(SessionConstant.PASSWORD, "admin123").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(200));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/asfdno").sessionAttr(SessionConstant.USERNAME, "admin")
				.sessionAttr(SessionConstant.PASSWORD, "admin123").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(404));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/asfdno").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(404));

		// get canEdit
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/user/admin/canedit").sessionAttr(SessionConstant.USERNAME, "admin")
						.sessionAttr(SessionConstant.PASSWORD, "admin123").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(200));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/admin/canedit").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(403));
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/user/asfdno/canedit").sessionAttr(SessionConstant.USERNAME, "admin")
						.sessionAttr(SessionConstant.PASSWORD, "admin123").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(403));
		// put
		mockMvc.perform(MockMvcRequestBuilders.put("/api/user/admin").sessionAttr(SessionConstant.USERNAME, "admin")
				.sessionAttr(SessionConstant.PASSWORD, "admin123")
				.content(
						"userName=admin&firstName=hu&lastName=xuemin&country=cn&lang=zh&profession=it&qq=397997401&phoneNum=13066961905&wechat=hu-xuemin&email=397997401%40qq.com&sex=man&profile=admin")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(202));

		mockMvc.perform(MockMvcRequestBuilders.put("/api/user/huxuemin").sessionAttr(SessionConstant.USERNAME, "admin")
				.sessionAttr(SessionConstant.PASSWORD, "admin123")
				.content(
						"userName=admin&firstName=hu&lastName=xuemin&country=cn&lang=zh&profession=it&qq=397997401&phoneNum=13066961905&wechat=hu-xuemin&email=397997401%40qq.com&sex=man&profile=admin")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(404));

		mockMvc.perform(MockMvcRequestBuilders.put("/api/user/admin")
				.content(
						"userName=admin&firstName=hu&lastName=xuemin&country=cn&lang=zh&profession=it&qq=397997401&phoneNum=13066961905&wechat=hu-xuemin&email=397997401%40qq.com&sex=man&profile=admin")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(403));
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/user/huxuemin")
				.sessionAttr(SessionConstant.USERNAME, "huxuemin").sessionAttr(SessionConstant.PASSWORD, "admin123")
				.content(
						"userName=huxuemin&firstName=hu&lastName=xuemin&country=cn&lang=zh&profession=it&qq=397997401&phoneNum=13066961905&wechat=hu-xuemin&email=397997401%40qq.com&sex=man&profile=admin")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(202));
		
		//post
		mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
				.content(
						"user_login=admin&user_password=admin123")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	public void testArticle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/articles/11871121866257")
				.sessionAttr(SessionConstant.USERNAME, "admin").sessionAttr(SessionConstant.PASSWORD, "admin123")
				.content("articleTitle=huxuemin&articleContent=huxuemin")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is(202))
				.andDo(MockMvcResultHandlers.print());
	}
}
