package com.tensquare.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

//配置Elastic Search索引库，“indexName”属性指明索引库的名称，“type”指明索引库的表的名称
@Document(indexName="tensquare",type = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    @Id
    private String id;
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")  //映射索引字段
    private String title;
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")  //映射索引字段
    private String content;
    private String state;

}
