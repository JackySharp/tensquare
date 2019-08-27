package com.tensquare.article.controller;

import com.tensquare.article.service.ArticleService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin  //允许跨域访问
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //审核文章
    @PutMapping("/examine/{articleId}")
    public Result examineArticle(@PathVariable String articleId) {
        try {
            articleService.examineArticle(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"文章审核失败");
        }
        return new Result(true,StatusCode.OK,"文章审核成功");
    }

    //给文章点赞
    @PutMapping("/thumbup/{articleId}")
    public Result thumbupArticle(@PathVariable String articleId) {
        try {
            articleService.thumbup(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"点赞失败");
        }
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
