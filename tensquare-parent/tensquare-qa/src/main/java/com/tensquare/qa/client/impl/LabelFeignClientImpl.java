package com.tensquare.qa.client.impl;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.qa.client.LabelFeignClient;
import org.springframework.stereotype.Component;

@Component
public class LabelFeignClientImpl implements LabelFeignClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false,StatusCode.ERROR,"tensquare-base服务异常");
    }
}
