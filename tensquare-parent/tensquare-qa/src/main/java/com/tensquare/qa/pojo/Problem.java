package com.tensquare.qa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_problem")  //关联数据库中的表tb_problem，name用来指定数据库中的表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
  @Id  //以@Id修饰的属性关联数据库中的表的主键
  private String id;//问题id
  private String title;//问题标题
  private String content;//问题内容
  private java.sql.Timestamp createtime;//问题创建时间
  private java.sql.Timestamp updatetime;//问题更新时间
  private String userid;//用户id
  private String nickname;//用户昵称
  private long visits;//浏览量
  private long thumbup;//点赞数
  private long reply;//回复数
  private String solve;//是否解决
  private String replyname;//回复人昵称
  private java.sql.Timestamp replytime;//回复时间
}
