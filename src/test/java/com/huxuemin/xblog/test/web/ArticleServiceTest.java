package com.huxuemin.xblog.test.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huxuemin.xblog.application.ArticleService;
import com.huxuemin.xblog.config.RootConfig;
import com.huxuemin.xblog.database.DBConnectionFactory;
import com.huxuemin.xblog.domain.article.ArticleNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
public class ArticleServiceTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @Autowired
    ArticleService articleService;
    
    @Before
    public void setUp() {
        DBConnectionFactory.initDataBaseContext();
    }

    @After
    public void destory() {
        DBConnectionFactory.closeAllConnection();
    }
    
    @Test
    public void number(){
        expectedEx.expect(ArticleNotFoundException.class);
        expectedEx.expectMessage("Article not found!");
        articleService.numberOfDiscuss(123);
    }
}
