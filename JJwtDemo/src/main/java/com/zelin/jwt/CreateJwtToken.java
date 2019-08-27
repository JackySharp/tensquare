package com.zelin.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class CreateJwtToken {

    //测试创建基本的Jwt
    @Test
    public void testCreateJwt() {
        /**
         * Jwts.builder()：Jwt生成器
         * setId()：设置生成器id
         * setIssuedAt()：设置加密时间
         * setSubject()：设置主题信息
         * signWith()：设置Jwt签名
         * compact()：得到Jwt生成的字符串
         */
        //创建Jwt生成器
        JwtBuilder jwtBuilder = Jwts.builder().setId("123456")  //设置JWT id
                .setIssuedAt(new Date())  //设置签发时间
                .setSubject("admin")  //设置认证/授权的主体信息
                .signWith(SignatureAlgorithm.HS256,"alibaba");  //设置签名和加密算法
        //得到生成的结果
        String compact = jwtBuilder.compact();
        System.out.println("compact=" + compact);
    }

    //测试创建带有过期时间的Jwt
    @Test
    public void testCreateJwtWithExpire() {
        //获取当前时间
        long millisSeconds = System.currentTimeMillis();
        //设置到期时间为当前时间加上10mins
        Date date = new Date(millisSeconds + 10 * 60 * 1000);
        //创建Jwt对象
        JwtBuilder jwtBuilder = Jwts.builder().setId("666666")  //设置JWT id
                .setSubject("user")  //设置认证/授权的主体信息
                .setIssuedAt(new Date())  //设置签发时间
                .setExpiration(date)  //设置过期时间
                .signWith(SignatureAlgorithm.HS256,"alibaba");  //设置签名和加密算法
        //获取生成结果
        String compact = jwtBuilder.compact();
        System.out.println("compact=" + compact);
    }

    //测试创建带有过期时间并且携带数据的Jwt
    @Test
    public void testCreateJwtWithExpireAndData() {
        //获取当前时间
        long currentTime = System.currentTimeMillis();
        //设置过期时间为当前时间加上1min
        Date date = new Date(currentTime + 60 * 1000);
        //生成JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder().setId("888")  //设置JWT id
                .setSubject("jacky")  //设置认证/授权的主体信息
                .setIssuedAt(new Date())  //设置签发时间
                .setExpiration(date)  //设置过期时间
                .claim("roles","admin");
        //获得Token对象
        String compact = jwtBuilder.compact();
        System.out.println("compact=" + compact);
    }




}
