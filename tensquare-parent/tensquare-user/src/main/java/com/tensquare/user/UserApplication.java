package com.tensquare.user;

import com.tensquare.util.IdWorker;
import com.tensquare.util.JwtUtil;
import com.tensquare.util.SmsUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@EnableEurekaClient  //启用Eureka客户端发现，只能开启Eureka客户端，不灵活
@EnableDiscoveryClient  //启用客户端发现，发现多个客户端
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

    //将阿里大于短信插件放入SpringBoot容器
    @Bean
    public SmsUtil smsUtil() {
        return new SmsUtil();
    }

    //将IdWorker放入Spring容器
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0,0);
    }

    //将BCrypt密码加密器放入Spring容器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //将JWT鉴权工具类JwtUtil放入Spring容器
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}
