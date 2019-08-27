package com.tensquare.user.service;

import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.pojo.Admin;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //新增管理员
    public void add(Admin admin) {
        //生成一个随机id并设置为管理员的id
        admin.setId(idWorker.nextId() + "");
        //对输入的管理员密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        System.out.println(admin.getPassword());
        //添加管理员
        adminDao.save(admin);
    }

    //依据登录名和密码查找指定管理员
    public Admin findAdminByLoginNameAndPassword(String loginname, String password) {
        Admin admin = adminDao.findAdminByLoginname(loginname);
        //比对从数据库查询到的管理员密码是否与输入的管理员密码匹配
        if (null != admin && passwordEncoder.matches(password,admin.getPassword())) {
            //如果匹配，返回从数据库查询到的管理员
            return admin;
        } else {
            //如果没有查询到管理员信息或者输入的密码与管理员密码不一致，则返回null
            return null;
        }
    }

    //删除管理员
    public void delete(String adminId) {
        adminDao.deleteById(adminId);
    }
}
