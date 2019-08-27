package com.tensquare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 使用Lombok插件，省去为实体类编写构造函数、stter、getter方法、toString方法的步骤
 * @param <T>
 */
@Data  //相当于声明了这些注解：@Getter、@Setter、@RequiredArgsConstructor、@ToString、@EqualsAndHashCode
@AllArgsConstructor  //省去编写带有全部参数的构造方法的步骤
@NoArgsConstructor  //省去VBIan写午餐的构造方法的步骤
public class PageResult<T> implements Serializable {
    private long totals;//查询的总记录数
    private List<T> rows;//查询结果分页后得到的每页显示的结果集
}
