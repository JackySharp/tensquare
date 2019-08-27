package com.zelin.canal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable{
    @Field("id")
    private int pid;
    @Field("product_name")
    private String name;
    @Field("product_catalog_name")
    private String catalog_name;
    @Field("product_price")
    private float price;

    private int number;
    @Field("product_description")
    private String description;
    @Field("product_picture")
    private String picture;
    private String release_time;

    public Product(int pid, String name, String catalog_name, float price) {
        this.pid = pid;
        this.name = name;
        this.catalog_name = catalog_name;
        this.price = price;
    }

}
