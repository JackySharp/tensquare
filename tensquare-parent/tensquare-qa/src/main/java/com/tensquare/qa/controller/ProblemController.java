package com.tensquare.qa.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.qa.client.LabelFeignClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/problem")
//@CrossOrigin //允许跨域访问
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LabelFeignClient labelFeignClient;

    //查询最新问题列表，按降序排序
    @GetMapping("/newlist/{label}/{page}/{size}")
    public Result getNewList(@PathVariable String label, @PathVariable int page, @PathVariable int size ) {
        PageResult newProblems = problemService.getNewProblems(label, page, size);
        return new Result(true,StatusCode.OK,"查询最新问题列表成功",newProblems);
    }

    //查询热门问题列表，按回复数降序排序
    @GetMapping("/hotlist/{label}/{page}/{size}")
    public Result getHotList(@PathVariable String label, @PathVariable int page, @PathVariable int size) {
        PageResult<Problem> hotProblems = problemService.getHotProblems(label, page, size);
        return new Result(true,StatusCode.OK,"查询热门问题列表成功",hotProblems);
    }

    //发布问题
    @PostMapping
    public Result add(@RequestBody Problem problem) {
        //从请求域获取Claims
        Claims claims = (Claims) request.getAttribute("claims");
        if (null == claims) {
            return new Result(false,StatusCode.LOGINERROR,"登录状态异常,请重新登录");
        }
        String roles = (String) claims.get("roles");
        if (null != roles && "user".equals(roles)) {
            try {
                problem.setUserid(claims.getId());
                problemService.add(problem);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,StatusCode.ERROR,"发布问题失败");
            }
            return new Result(true,StatusCode.OK,"问题发布成功");
        } else {
            return new Result(false,StatusCode.ACCESSERROR,"抱歉,只有会员才能发布问题");
        }
    }

    //调用 tensquare-base模块的findById方法
    @GetMapping("/label/{labelId}")
    public Result findLabelById(@PathVariable String labelId) {
        return labelFeignClient.findById(labelId);
    }
}
