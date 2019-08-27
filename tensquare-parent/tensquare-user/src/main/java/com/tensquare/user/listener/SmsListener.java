package com.tensquare.user.listener;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.util.SmsUtil;
import org.springframework.core.env.Environment;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

@RabbitListener(queues = "sms")
@ContextConfiguration("classpath*:properties/sms.properties")
@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private Environment env;

    //向阿里大于短信平台发送短信验证码
    @RabbitHandler
    public void sendMessageToAlidayu(Map map) throws ClientException {
        //封装查询参数
        Map paramMap = new HashMap();
        paramMap.put("code",map.get("code"));
        String param = JSON.toJSONString(paramMap);
        smsUtil.sendSms(map.get("mobile").toString(),"sms.templateCode","sms.signname",param);
    }
}
