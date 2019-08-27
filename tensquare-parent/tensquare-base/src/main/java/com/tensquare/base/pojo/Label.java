package com.tensquare.base.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "tb_label")  //关联数据库中的表tb_label，name用来指定数据库中的表名
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Label {
    @Id  //以@Id修饰的属性关联数据库中的表的主键
    private String id;//标签id
    private String labelname;//标签名称
    private String state;//标签状态
    private Long count;//标签使用数量
    private String recommend;//是否推荐
    private Long fans;//粉丝数
}
