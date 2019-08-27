package com.tensquare.article.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_column")  //关联数据库中的表tb_column，name用来指定数据库中的表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column {
	@Id  //以@Id修饰的属性关联数据库中的表的主键
	private String id;//ID
	private String name;//专栏名称
	private String summary;//专栏简介
	private String userid;//用户ID
	private java.util.Date createtime;//申请日期
	private java.util.Date checktime;//审核日期
	private String state;//状态
}
