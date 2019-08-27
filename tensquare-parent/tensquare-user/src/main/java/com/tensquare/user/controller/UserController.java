package com.tensquare.user.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin  //允许跨域访问
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    //生成验证码，向阿里大于短信平台提交发送验证码短信请求
    @GetMapping("/sendSms/{mobile}")
    public Result createValidCode(@PathVariable String mobile) {
        try {
            userService.sendSms(mobile);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"验证码发送失败");
        }
        return new Result(true,StatusCode.OK,"验证码已发送");
    }

    //用户注册
    @PostMapping("/register/{code}")
    public Result registry(@RequestBody User user, @PathVariable String code) {
        try {
            userService.add(user,code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"用户注册失败");
        }
        return new Result(true,StatusCode.OK,"用户注册成功");
    }

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> loginMap) {
        //从数据库查找与输入的用户信息匹配的用户
        User user = userService.findByMobile(loginMap.get("mobile"), loginMap.get("password"));
        if (user == null) {
            //如果没有查询到指定用户，则登录失败
            return new Result(false,StatusCode.ACCESSERROR,"用户登陆失败");
        } else {
            //为普通用户添加签权（生成token）
            String token = jwtUtil.createJwt(user.getId(), user.getNickname(), "user");
            //将token封装到Map
            Map<String,String> map = new HashMap<String,String>();
            map.put("token",token);
            map.put("name",user.getNickname());
            return new Result(true,StatusCode.OK,"用户登录成功",map);
        }
    }

    //删除普通用户
    @DeleteMapping("/{userId}")
    public Result delete(@PathVariable String userId) {
        //从请求域得到Claims对象
        Claims claims = (Claims) request.getAttribute("claims");
        if (null == claims) {
            return new Result(false,StatusCode.LOGINERROR,"登录状态异常,请重新登录");
        }
        //从Claims对象解析权限信息
        String roles = (String) claims.get("roles");
        //判断当前登录权限
        if (null != roles && "admin".equals(roles)) {
            //如果当前登录权限为管理员，可以对用户进行删除操作
            try {
                userService.delete(userId);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, StatusCode.ERROR, "删除用户失败");
            }
            return new Result(true,StatusCode.OK,"删除用户成功");
        } else {
            return new Result(false,StatusCode.ACCESSERROR,"无权删除用户");
        }
    }

    //添加用户
    @PostMapping
    public Result add(@RequestBody User user) {
        try {
            userService.add(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"添加用户失败");
        }
        return new Result(true,StatusCode.OK,"添加用户成功");
    }

    //更新粉丝数
    @PutMapping("/incfans/{userid}/{x}")
    public Result incFans(@PathVariable String userid, @PathVariable int x) {
        try {
            userService.incFans(userid,x);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"更新粉丝数失败");
        }
        return new Result(true,StatusCode.OK,"更新粉丝数成功");
    }

    //更新关注数
    @PutMapping("/incfollows/{userid}/{x}")
    public Result incFollows(@PathVariable String userid, @PathVariable int x) {
        try {
            userService.incFollows(userid,x);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"更新关注失败");
        }
        return new Result(true,StatusCode.OK,"更新关注数成功");
    }
}
