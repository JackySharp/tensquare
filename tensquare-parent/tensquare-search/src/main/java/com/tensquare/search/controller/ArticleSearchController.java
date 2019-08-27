package com.tensquare.search.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin  //允许跨域访问
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;

    //添加文章
    @PostMapping
    public Result addArticle(@RequestBody Article article) {
        try {
            articleSearchService.add(article);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"文章添加失败");
        }
        return new Result(true,StatusCode.OK,"文章添加成功");
    }

    //依据标题或内容字段搜索文章
    @GetMapping("/search/{keywords}/{page}/{size}")
    public Result searchByTitleOrContent(@PathVariable String keywords, @PathVariable int page, @PathVariable int size) {
        PageResult<Article> articlePageResult = articleSearchService.searchByTitleOrContent(keywords, page, size);
        return new Result(true,StatusCode.OK,"按标题或内容搜索成功",articlePageResult);
    }

}
