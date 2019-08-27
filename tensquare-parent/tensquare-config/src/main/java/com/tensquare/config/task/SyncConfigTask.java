package com.tensquare.config.task;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component  //将定时任务加入SpringBoot组件，实现SpringBoot启动的时候定时任务类也随之启动直到SpringBoot关闭
@EnableScheduling  //开启定时任务
public class SyncConfigTask {
    /**
     * 定时（每分钟的30秒）向Gitee（或Github）远程仓库请求同步各个微服务的配置文件application.yml
     */
    @Scheduled(cron = "30 * * * * *")
    public void sync() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(new HashMap(),headers);
        restTemplate.postForEntity("http://localhost:12000/actuator/bus-refresh",request,null);
    }
}
