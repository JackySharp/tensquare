package com.tensquare.exception;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定义基本异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(Exception.class) //声明要处理的异常类型

    @ResponseBody
    public Result errorHandler(Exception e) {
        //打印异常栈
        e.printStackTrace();
        //返回错误信息
        return new Result(false,StatusCode.ERROR,e.getMessage());
    }
}
