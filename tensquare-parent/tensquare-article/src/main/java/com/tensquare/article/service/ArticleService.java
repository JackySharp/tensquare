package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional  //加入事务处理
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    //审核文章
    public void examineArticle(String articleId) {
        //依据id审核文章
        articleDao.setExamineState(articleId);
    }

    //点赞文章
    public void thumbup(String articleId) {
        // 更新文章的点赞数
        articleDao.addThumbup(articleId);
    }

    private Article findById(String articleId) {
        //从Redis缓存中取出点赞数
        Article article = (Article) redisTemplate.opsForValue().get(articleId);
        //如果Redis缓存中的点赞数为空，则向数据库查询
        if (null == article) {
            System.out.println("从数据库查询数据");
            article = articleDao.findById(articleId).get();
            //设置缓存的过期时间，例如下面就是设置缓存5分钟后过期
            redisTemplate.opsForValue().set(articleId,article.getThumbup(),5,TimeUnit.MINUTES);
            System.out.println("更新Redis数据: " + article);
        }
        return article;
    }

    //修改文章
    public void update(Article article) {
        //更新文章到缓存
        redisTemplate.opsForValue().set(article.getId(),article);
        //更新文章到数据库
        articleDao.save(article);
    }

    //删除文章
    public void delete(String articleId) {
        //从缓存删除文章
        redisTemplate.delete(articleId);
        //从数据库删除文章
        articleDao.deleteById(articleId);
    }
}
