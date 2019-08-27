package com.tensquare.recruit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_recruit")  //关联数据库中的表tb_recruit，name用来指定数据库中的表名
@Data
@AllArgsConstructor
public class Recruit {
    @Id  //以@Id修饰的属性关联数据库中的表的主键
    private String id;//职位id
    private String jobname;//职位名称
    private String salary;//薪资范围
    private String condition;//经验要求
    private String education;//学历要求
    private String type;//任职方式
    private String address;//办公地址
    private String createtime;//职位创建时间
    private String eid;//企业id
    private String state;//职位发布状态
    private String label;//标签
    private String url;//网址
    private String content1;//职位描述
    private String  content2;//任职要求

    public Recruit() {
    }

    public Recruit(String jobname, String salary, String condition, String education, String type, String address, String createtime, String eid, String state, String label, String url, String content1, String content2) {
        this.jobname = jobname;
        this.salary = salary;
        this.condition = condition;
        this.education = education;
        this.type = type;
        this.address = address;
        this.createtime = createtime;
        this.eid = eid;
        this.state = state;
        this.label = label;
        this.url = url;
        this.content1 = content1;
        this.content2 = content2;
    }

}
