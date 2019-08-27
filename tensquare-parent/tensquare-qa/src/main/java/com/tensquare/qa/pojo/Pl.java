package com.tensquare.qa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * tb_pl是一个中间表，绑定双主键来唯一确定一行记录
 */
@Entity
@Table(name = "tb_pl") //关联数据库中的表tb_pl，name用来指定数据库中的表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pl implements Serializable {
  @Id
  private String problemid;//问题id
  @Id
  private String labelid;//标签id
}
