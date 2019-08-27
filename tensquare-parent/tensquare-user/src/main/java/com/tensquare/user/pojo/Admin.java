package com.tensquare.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_admin")  //关联数据库中的表tb_admin，name用来指定数据库中的表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id  //以@Id修饰的属性关联数据库中的表的主键
    private String id;
    private String loginname;
    private String password;
    private String state;
}
