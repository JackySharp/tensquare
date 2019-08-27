package com.tensquare.qa.client;

import com.tensquare.entity.Result;
import com.tensquare.qa.client.impl.LabelFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * tensquare-qa服务调用tensquare-base服务的接口
 */
//启用Feign客户端接口，“value”（别名为“name”）代表访问的服务的名称
//微服务的名称是在对应的微服务的 application.yml.bak 配置文件中定义的
//微服务名称不能出现“-”，否则会报错
@FeignClient(value = "tensquare-base",fallback = LabelFeignClientImpl.class)
public interface LabelFeignClient {
    /**
     * 调用微服务tensquare-base Controller层的方法findById()，
     * 需要注意的是，即使{labelId}这个参数与方法中的形参名一致，
     * 也需要在@PathVariable注解中带有此参数的名称
     * @param labelId
     * @return
     */
    @GetMapping("/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);
}
