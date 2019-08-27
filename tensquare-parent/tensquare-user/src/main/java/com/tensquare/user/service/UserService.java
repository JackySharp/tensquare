package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import com.tensquare.util.IdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //发送Direct模式消息
    public void sendSms(String mobile) {
        //生成短信验证码（6位随机数）
        int code = new Random(999999 - 100000).nextInt() + 100000;
        System.out.println("验证码:" + code);
        //将生成的验证码放入缓存，并设置过期时间为2分钟
        redisTemplate.opsForValue().set("mobile_" + mobile,"code_" + code,2,TimeUnit.MINUTES);
        //创建Map封装消息内容
        Map map = new HashMap();
        map.put("mobile",mobile);
        map.put("code",code);
        //向Service层发送消息
        rabbitTemplate.convertAndSend("sms",map);
    }

    //用户注册
    public void add(User user, String code) {
        //从缓存取出二维码
        String validCode = (String) redisTemplate.opsForValue().get("mobile_" + user.getMobile());
        //如果用户输入的验证码为空，则提示输入验证码
        if (null == code) {
            throw new RuntimeException("验证码不能为空");
        }
        //如果用户输入的验证码和缓存存储的验证码不一致，则提示验证码错误
        if (!code.equals(validCode)) {
            throw new RuntimeException("验证码错误");
        }
        //将注册用户信息添加到数据库
        user.setId(idWorker.nextId() + "");
        //用户密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFollowcount(0);
        user.setFanscount(0);
        user.setOnline(0L);
        user.setRegdate(new Date());
        user.setUpdatedate(new Date());
        user.setLastdate(new Date());
        userDao.save(user);
    }

    //通过手机号在数据库查询对应用户信息
    public User findByMobile(String mobile, String password) {
        //依旧手机号查询对应用户信息
        User user = userDao.findByMobile(mobile);
        if (null != user && passwordEncoder.matches(password,user.getPassword())) {
            //如果如果数据库中的用户密码与输入的用户密码一致，则返货登录用户信息
            return user;
        } else {
            //如果登录输入的用户密码与从数据库查询到的用户密码不一致，则返回null
            return null;
        }
    }

    //删除用户
    public void delete(String userId) {
        userDao.deleteById(userId);
    }

    //添加用户
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        userDao.save(user);
    }

    //更新粉丝数
    public void incFans(String userid, int x) {
        userDao.incFans(userid,x);
    }

    //更新关注数
    public void incFollows(String userid, int x) {
        userDao.incFollows(userid,x);
    }
}
