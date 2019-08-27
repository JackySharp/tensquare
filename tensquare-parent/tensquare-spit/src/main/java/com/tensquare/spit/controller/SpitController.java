package com.tensquare.spit.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
@CrossOrigin  //允许跨域访问
public class SpitController {

    @Autowired
    private SpitService spitService;

    //新增吐槽
    @PostMapping
    public Result addSpit(@RequestBody Spit spit) {
        try {
            spitService.add(spit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"添加吐槽失败");
        }
        return new Result(true,StatusCode.OK,"添加吐槽成功");
    }

    //查询所有吐槽
    @GetMapping
    public Result getAllSpitList() {
        List<Spit> spitList = spitService.findAll();
        return new Result(true,StatusCode.OK,"查询成功",spitList);
    }

    //依据id查询吐槽记录
    @GetMapping("/{spitId}")
    public Result findSpit(@PathVariable String spitId) {
        Spit spit = spitService.findById(spitId);
        return new Result(true,StatusCode.OK,"查询成功",spit);
    }

    //依据id修改吐槽记录
    @PutMapping("/{spitId}")
    public Result updateSpit(@RequestBody Spit spit, @PathVariable String spitId) {
        try {
            spit.set_id(spitId);
            spitService.update(spit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"修改吐槽失败");
        }
        return new Result(true, StatusCode.OK,"修改吐槽失败");
    }

    //依据id删除吐槽记录
    @DeleteMapping("/{spitId}")
    public Result deleteSpit(@PathVariable String spitId) {
        try {
            spitService.delete(spitId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"删除吐槽失败");
        }
        return new Result(true, StatusCode.OK,"删除吐槽失败");
    }

    //给吐槽点赞
    @PutMapping("/thumbup/{spitId}")
    public Result thumbupSpit(@PathVariable String spitId) {
        Boolean flg = spitService.thumbup(spitId);
        if (flg) {
            return new Result(true, StatusCode.OK,"点赞成功");
        } else {
            return new Result(false, StatusCode.REPERROR,"点赞失败");
        }
    }

    //依据上级id查询吐槽列表
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findSpitsByParentId(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        PageResult<Spit> spits = spitService.findByParentid(parentid, page, size);
        return new Result(true,StatusCode.OK,"依据上一级id查询吐槽列表成功",spits);
    }
}
