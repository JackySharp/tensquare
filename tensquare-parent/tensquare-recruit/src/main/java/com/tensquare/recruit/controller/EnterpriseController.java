package com.tensquare.recruit.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enterprise")
@CrossOrigin  //允许跨域访问
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    //查询热门企业列表
    @GetMapping("/search/hotlist")
    public Result searchHotEnterpriseList() {
        List<Enterprise> hotEnterprises = enterpriseService.getHotEnterprises();
        return new Result(true,StatusCode.OK,"热门企业查询成功",hotEnterprises);
    }

}
