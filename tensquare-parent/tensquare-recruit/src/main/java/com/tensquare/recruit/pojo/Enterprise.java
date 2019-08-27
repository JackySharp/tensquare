package com.tensquare.recruit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_enterprise")  //关联数据库中的表tb_enterprise，name用来指定数据库中的表名
@Data
@AllArgsConstructor
public class Enterprise {
    @Id  //以@Id修饰的属性关联数据库中的表的主键
    private String id;//企业id
    private String name;//企业名称
    private String summary;//企业简介
    private String address;//企业地址
    private String labels;//标签列表
    private String coordinate;//坐标
    private String ishot;//是否热门
    private String logo;//企业LOGO
    private String jobcount;//职位数
    private String url;//企业网址

    public Enterprise() {
    }

    public Enterprise(String name, String summary, String address, String labels, String coordinate, String ishot, String logo, String jobcount, String url) {
        this.name = name;
        this.summary = summary;
        this.address = address;
        this.labels = labels;
        this.coordinate = coordinate;
        this.ishot = ishot;
        this.logo = logo;
        this.jobcount = jobcount;
        this.url = url;
    }

}
