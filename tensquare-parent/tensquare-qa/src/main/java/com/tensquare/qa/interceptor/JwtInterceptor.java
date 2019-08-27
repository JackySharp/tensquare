package com.tensquare.qa.interceptor;

import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义拦截器用于签权
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //得到请求头对象
        String header = request.getHeader("Authorization");
        //判断是否以“Bearer”开头
        if (!StringUtils.isEmpty(header) && header.startsWith("Bearer")) {
            //取出请求头第7位以后的字符串，即token
            String token = header.substring(7);
            //解析token得到Claims对象
            Claims claims = jwtUtil.parseToken(token);
            System.out.println("拦截器解析claims=" + claims);
            //判断Claims对象是否为空
            if (null != claims) {
                //如果Claims对象不为空，将Claims对象放到请求作用域
                request.setAttribute("claims",claims);
            }
        }
        //无论是否获取token和Claims对象，拦截器都放行，程序才能继续往下执行
        return true;
    }
}
