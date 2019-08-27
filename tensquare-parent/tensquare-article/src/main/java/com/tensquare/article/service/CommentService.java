package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  //加入事务处理
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    //添加评论
    public void add(Comment comment) {
        //设置评论id
        comment.set_id(String.valueOf(idWorker.nextId()));
        commentDao.save(comment);
    }

    //删除评论
    public void delete(String commentId) {
        commentDao.deleteById(commentId);
    }
}
