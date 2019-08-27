package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleSearchDao extends ElasticsearchRepository<Article,String> {

    //按标题或内容搜索文章
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
