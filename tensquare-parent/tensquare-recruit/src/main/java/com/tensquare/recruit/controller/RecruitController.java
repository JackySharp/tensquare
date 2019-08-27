package com.tensquare.recruit.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruit")
@CrossOrigin  //允许跨域访问
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    //查询推荐职位：状态为“2”的职位,并按创建日期降序排序，查询前4条记录
    @GetMapping("/search/recommend")
    public Result findRecruitsByStateOrderByDesc() {
        List<Recruit> recruits = recruitService.findRecruitsByRecommend();
        return new Result(true,StatusCode.OK,"推荐职位查询成功",recruits);
    }

    //查询最新职位：状态不为0，并按创建日期降序排序，查询出前12条记录
    @GetMapping("/search/newlist")
    public Result findLastestRecruitsOrderByDesc() {
        List<Recruit> recruits = recruitService.findLastestRecruits();
        return new Result(true,StatusCode.OK,"最新职位查询成功",recruits);
    }
}
