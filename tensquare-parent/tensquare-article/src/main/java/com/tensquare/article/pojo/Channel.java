package com.tensquare.article.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_channel")  //关联数据库中的表tb_article，name用来指定数据库中的表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
	@Id  //以@Id修饰的属性关联数据库中的表的主键
	private String id;//ID
	private String name;//频道名称
	private String state;//状态
}
