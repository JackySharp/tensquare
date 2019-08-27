package com.tensquare.qa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_reply")  //关联数据库中的表tb_problem，name用来指定数据库中的表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
  @Id  //以@Id修饰的属性关联数据库中的表的主键
  private String id;//回复编号
  private String problemid;//问题id
  private String content;//回答内容
  private java.sql.Timestamp createtime;//创建时间
  private java.sql.Timestamp updatetime;//更新时间
  private String userid;//回答人id
  private String nickname;//回答人昵称
}
