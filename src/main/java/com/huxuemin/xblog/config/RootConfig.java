package com.huxuemin.xblog.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.huxuemin.xblog.auth.PublishAuthCheck;
import com.huxuemin.xblog.config.RootConfig.WebPackage;
import com.huxuemin.xblog.domain.repository.ArticleRepository;
import com.huxuemin.xblog.domain.repository.RepositoryRegister;
import com.huxuemin.xblog.domain.repository.RoleRepository;
import com.huxuemin.xblog.domain.repository.UserRepository;

@Configuration
@ComponentScan(basePackages = { "com.huxuemin.xblog" }, excludeFilters = {
		@Filter(type = FilterType.CUSTOM, value = WebPackage.class) })
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class RootConfig {

	public static class WebPackage extends RegexPatternTypeFilter {
		public WebPackage() {
			super(Pattern.compile("com\\.huxuemin\\.xblog\\.web.*"));
		}
	}

	@Bean
	public ArticleRepository articleRepository() {
		return RepositoryRegister.getArticleRepository();
	}

	@Bean
	public UserRepository userRepository() {
		return RepositoryRegister.getUserRepository();
	}

	@Bean
	public RoleRepository roleRepository() {
		return RepositoryRegister.getRoleRepository();
	}

	@Bean
	public PublishAuthCheck publishAuthCheck() {
		return new PublishAuthCheck();
	}
}
