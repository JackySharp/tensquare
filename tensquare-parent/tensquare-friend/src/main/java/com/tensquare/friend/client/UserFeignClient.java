package com.tensquare.friend.client;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * tensquare-friend服务调用tensquare-user服务的接口
 */
//启用Feign客户端接口，“value”（别名为“name”）代表访问的服务的名称
//微服务的名称是在对应的微服务的 application.yml.bak 配置文件中定义的
//微服务名称不能出现“-”，否则会报错
@FeignClient("tensquare-user")
public interface UserFeignClient {
    /**
     * 调用微服务tensquare-user Controller层的方法incFans()，
     * 需要注意的是，即使{userid}这个参数与方法中的形参名一致，
     * 也需要在@PathVariable注解中带有此参数的名称
     * @param userid
     * @param x
     * @return
     */
    @PutMapping("/user/incfans/{userid}/{x}")
    public Result incFans(@PathVariable("userid") String userid, @PathVariable("x") int x);

    /**
     * 调用微服务tensquare-user Controller层的方法incFollows()，
     * 需要注意的是，即使{userid}这个参数与方法中的形参名一致，
     * 也需要在@PathVariable注解中带有此参数的名称
     * @param userid
     * @param x
     * @return
     */
    @PutMapping("/user/incfollows/{userid}/{x}")
    public Result incFollows(@PathVariable("userid") String userid, @PathVariable("x") int x);
}
