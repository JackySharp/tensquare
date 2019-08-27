package com.tensquare.search.service;

import com.tensquare.entity.PageResult;
import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    //添加文章
    public void add(Article article) {
        articleSearchDao.save(article);
    }

    //依据标题或内容字段搜索文章
    public PageResult<Article> searchByTitleOrContent(String keywords, int page, int size) {
        Page<Article> articles = articleSearchDao.findByTitleOrContentLike(keywords, keywords, PageRequest.of(page - 1, size));
        return new PageResult<>(articles.getTotalElements(),articles.getContent());
    }
}
