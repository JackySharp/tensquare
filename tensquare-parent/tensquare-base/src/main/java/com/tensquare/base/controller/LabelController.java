package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin  //允许跨域访问
@RefreshScope  //此注解允许更改自定义配置属性不需要重启微服务就能生效
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Value("${ip}")
    private String ip;

    @GetMapping("/showip")
    public Result showIp() {
        return new Result(true,StatusCode.OK,"查询IP成功",ip);
    }

    //查询所有标签
    @RequestMapping(method = RequestMethod.GET)
    public Result getLabelList() {
        List<Label> labelList = labelService.findAll();
        return new Result(true,StatusCode.OK,"查询标签列表完毕",labelList);
    }

    //添加label
    @RequestMapping(method = RequestMethod.POST)
    public Result addLabel(@RequestBody Label label) {
        try {
            labelService.addOne(label);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"新增标签失败");
        }
        return new Result(true,StatusCode.OK,"新增标签成功");
    }

    //依据id查询标签
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable String labelId) {
        Label label = labelService.findById(labelId);
        return new Result(true,StatusCode.OK,"查询标签完毕",label);
    }

    //依据id修改标签
    @PutMapping("/{labelId}")
    public Result updateById(@PathVariable String labelId, @RequestBody Label label) {
        try {
            label.setId(labelId);
            labelService.updateById(label);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"更新标签失败");
        }
        return new Result(true,StatusCode.OK,"更新标签成功");
    }

    //依据id删除标签
    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable String labelId) {
        try {
            labelService.deleteById(labelId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"删除标签失败");
        }
        return new Result(true,StatusCode.OK,"删除标签成功");
    }

    //依据条件查询标签
    @PostMapping("/search")
    public Result search(@RequestBody Label label) {
        List<Label> labels = null;
        try {
            labels = labelService.search(label);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"条件查询失败");
        }
        return new Result(true,StatusCode.OK,"条件查询成功",labels);
    }

    //条件查询带分页
    @PostMapping("/search/{page}/{size}")
    public Result searchByPage(@RequestBody Label label, @PathVariable int page, @PathVariable int size) {
        PageResult pageResult = null;
        pageResult = labelService.searchByPage(label,page,size);
        return new Result(true,StatusCode.OK,"分页查询成功",pageResult);
    }

}
