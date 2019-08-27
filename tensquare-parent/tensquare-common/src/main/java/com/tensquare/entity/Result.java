package com.tensquare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用Lombok插件，省去为实体类编写构造函数、stter、getter方法、toString方法的步骤
 */
@Data  //相当于声明了这些注解：@Getter、@Setter、@RequiredArgsConstructor、@ToString、@EqualsAndHashCode
@AllArgsConstructor  //省去编写带有全部参数的构造方法的步骤
public class Result {
    private boolean status;//返回成功或者失败
    private Integer code;//返回码
    private String message;//返回提示信息
    private Object data;//返回数据，该数据可能是集合、数组、Map、json字符串等任意类型对象

    public Result() {
    }

    public Result(boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.status;
    }
}
