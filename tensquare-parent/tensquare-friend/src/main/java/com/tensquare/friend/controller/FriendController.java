package com.tensquare.friend.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.friend.client.UserFeignClient;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import com.tensquare.friend.service.FriendService;
import com.tensquare.friend.service.NoFriendService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private NoFriendService noFriendService;

    @Autowired
    private HttpServletRequest request;

    //添加好友
    @PutMapping("/like/{friendid}/{type}")
    public Result like(@PathVariable String friendid, @PathVariable String type) {
        //获得Claims对象
        Claims claims = (Claims) request.getAttribute("claims");
        if (null != claims) {
            String roles = (String) claims.get("roles");
            if (StringUtils.isNotBlank(roles)) {
                //获得当前登录用户id
                String userid = claims.getId();
                //type位“1”：喜欢，type为“2”：不喜欢
                if ("1".equals(type)) {
                    try {
                        int i = friendService.addFriend(userid, friendid);
                        if (i > 0) {
                            return new Result(true, StatusCode.OK, "添加好友成功");
                        } else {
                            return new Result(false, StatusCode.ACCESSERROR, "对方已经是您的好友,不能重复添加");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new Result(false, StatusCode.ERROR, "添加好友失败");
                    }
                } else if ("2".equals(type)) {
                    if (null == noFriendService.findByUseridAndFriendid(userid,friendid)) {
                        noFriendService.add(new NoFriend(userid, friendid));
                    }
                    return new Result(true,StatusCode.OK,"已将对方列为不喜欢对象");
                }
            }
        }
        //如果没有Claims对象，说明没有生成token，提示用户登录
        return new Result(false, StatusCode.ACCESSERROR, "无权添加好友,请先登录");
    }

    //删除好友
    @DeleteMapping("/{friendid}")
    public Result delete(@PathVariable String friendid) {
        //获得Claims对象
        Claims claims = (Claims) request.getAttribute("claims");
        if (null != claims) {
            //获得登录的用户拥有的角色
            String roles = (String) claims.get("roles");
            if (StringUtils.isNotBlank(roles)) {
                //获得登录的用户id
                String userid = claims.getId();
                System.out.println(userid);
                try {
                    int i = friendService.deleteFriend(userid, friendid);
                    if (i > 0) {
                        return new Result(true,StatusCode.OK,"删除好友成功");
                    } else {
                        return new Result(false,StatusCode.ACCESSERROR,"对方已不是您的好友,不能重复删除");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(false,StatusCode.ERROR,"删除好友失败");
                }
            }
        }
        return new Result(false,StatusCode.ACCESSERROR,"无权删除好友,请重新登录");
    }
}
