package com.tensquare.user.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    //新增管理员
    @PostMapping
    public Result add(@RequestBody Admin admin) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            if (null == claims) {
                return new Result(false,StatusCode.LOGINERROR,"登录状态异常,请重新登录");
            }
            String roles = (String) claims.get("roles");
            if (null == roles || !"admin".equals(roles)) {
                return new Result(false,StatusCode.ACCESSERROR,"无权删除管理员");
            }
            adminService.add(admin);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"管理员创建失败");
        }
        return new Result(true,StatusCode.OK,"管理员创建成功");
    }

    //管理员登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> loginMap) {
        //从数据库查询与输入的管理员信息对应的记录
        Admin admin = adminService.findAdminByLoginNameAndPassword(loginMap.get("loginname"),loginMap.get("password"));
        if (null == admin) {
            //如果查询到的管理员信息为空，则管理员登录失败
            return new Result(false,StatusCode.ACCESSERROR,"管理员账户或密码错误");
        } else {
            //为管理员添加签权（生成token）
            String token = jwtUtil.createJwt(admin.getId(), admin.getLoginname(), "admin");
            //将管理员token封装到Map
            Map<String,String> map = new HashMap<String,String>();
            map.put("token",token);
            map.put("name",admin.getLoginname());
            return new Result(true, StatusCode.OK, "管理员登录成功",map);
        }
    }

    //删除管理员
    @DeleteMapping("/{adminId}")
    public Result delete(@PathVariable String adminId) {
        //从请求域取出Claims对象
        Claims claims = (Claims) request.getAttribute("claims");
        if (null == claims) {
            return new Result(false,StatusCode.LOGINERROR,"登录状态异常,请重新登录");
        }
        //解析Claims对象权限信息
        String roles = (String) claims.get("roles");
        if (null != roles && "admin".equals(roles)) {
            //如果当前登录身份是管理员，则有删除权限
            try {
                adminService.delete(adminId);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,StatusCode.ERROR,"删除管理员失败");
            }
            return new Result(true,StatusCode.OK,"删除管理员成功");
        } else {
            return new Result(false,StatusCode.ACCESSERROR,"无权删除管理员");
        }
    }
}
