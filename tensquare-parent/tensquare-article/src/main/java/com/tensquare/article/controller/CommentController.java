package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin  //允许跨域访问
public class CommentController {

    @Autowired
    private CommentService commentService;

    //添加评论
    @PostMapping
    public Result addComment(@RequestBody Comment comment) {
        try {
            commentService.add(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"添加评论失败");
        }
        return new Result(true,StatusCode.OK,"添加评论成功");
    }

    //删除评论
    @DeleteMapping("/{commentid}")
    public Result deleteComment(@PathVariable String commentid) {
        try {
            commentService.delete(commentid);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"删除评论失败");
        }
        return new Result(true,StatusCode.OK,"删除评论成功");
    }

}
