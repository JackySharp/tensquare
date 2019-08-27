package com.tensquare.gathering.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gathering")
@CrossOrigin  //允许跨域访问
public class gatheringController {

    @Autowired
    private GatheringService gatheringService;

    //依据id查询活动
    @GetMapping("/{ghatheringId}")
    public Result findGatheringById(@PathVariable String gatheringId) {
        Gathering gathering = gatheringService.findById(gatheringId);
        return new Result(true,StatusCode.OK,"查询活动成功");
    }
}
