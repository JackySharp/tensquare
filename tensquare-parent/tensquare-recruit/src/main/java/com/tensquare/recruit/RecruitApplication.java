package com.tensquare.recruit;

import com.tensquare.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * SpringBoot启动类
 */
@SpringBootApplication
//@EnableEurekaClient  //启用Eureka客户端发现，只能开启Eureka客户端，不灵活
@EnableDiscoveryClient  //启用客户端发现，发现多个客户端
public class RecruitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class);
    }

    //将IdWorker放入Spring容器
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0,0);
    }
}
