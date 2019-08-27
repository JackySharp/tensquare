package com.tensquare;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaMongoDBDemo {

    //查询数据库表所有文档
    @Test
    public void testQuery() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        //查询数据库表的所有文档（一行记录）
        FindIterable<Document> documents = spit.find();
        printDocuments(documents);
    }

    //条件查询：精确查询1
    @Test
    public void testAccurateQuery1() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        //构建查询条件
        Document bson = new Document("userid","1001");
        bson.put("nickname","骚浪剑");
        //查询指定文档
        FindIterable<Document> documents = spit.find(bson);
        //输出结果
        printDocuments(documents);
    }

    //条件查询：精确查询2
    @Test
    public void testAccurateQuery2() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        //构建查询条件
        BasicDBObject bson = new BasicDBObject("nickname","大剑");
        bson.put("userid","1003");
        //查询指定文档
        FindIterable<Document> documents = spit.find(bson);
        //输出结果
        printDocuments(documents);
    }

    //条件查询：模糊查询1
    @Test
    public void testFuzzyQuery1() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        //构建查询条件
        BasicDBObject bson = new BasicDBObject("nickname",new BasicDBObject("$regex","^小"));
        bson.put("visits",new BasicDBObject("$gt",2000));
        //查询指定文档
        FindIterable<Document> documents = spit.find(bson);
        //输出结果
        printDocuments(documents);

    }

    //条件查询：模糊查询2
    @Test
    public void testFuzzyQuery2() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        //构建查询条件
        Document bson = new Document("nickname",new Document("$regex","骚"));
        bson.put("visits",new Document("$gt",15000));
        //查询指定文档
        FindIterable<Document> documents = spit.find(bson);
        //输出结果
        printDocuments(documents);

    }

    //条件查询：模糊查询3
    @Test
    public void testFuzzyQuery3() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        //构建查询条件
        List list = new ArrayList();
        list.add(new Document("visits",new Document("$gt",1000)));
        list.add(new Document("visits",new Document("$lt",50000)));
        Document bson = new Document("$and",list);
        //查询指定文档
        FindIterable<Document> documents = spit.find(bson);
        //输出结果
        printDocuments(documents);
    }

    //新增文档
    @Test
    public void testAdd() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        //创建文档对象
        Document bson = new Document("content","4棒强运打者");
        bson.put("visits",300000);
        bson.put("userid","1006");
        bson.put("nickname","独领风骚");
        bson.put("thumbup",100);
        bson.put("share",100);
        bson.put("comment",100);
        bson.put("parentid","100");
        //添加文档
        spit.insertOne(bson);
        //查询数据库所有文档
        //testQuery();
        //查询数据库文档数
        System.out.println(spit.count());
    }

    //删除文档
    @Test
    public void testDelete() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        Bson bson = new BasicDBObject("userid","1004");
        spit.deleteOne(bson);
        //查询数据库所有文档
        testQuery();
    }

    //修改文档
    @Test
    public void testUpdate() {
        //获得Collection(表)
        MongoCollection<Document> spit = getCollection();
        FindIterable<Document> documents = spit.find();
        for (Document bson : documents) {
            Document bson1 = new Document("$set",new Document("parentid",100));
            spit.updateOne(bson,bson1);
        }
//        Document bson = new Document("_id", new ObjectId("5d4ba0f4fec66563af5f639a"));
//        Document bson1 = new Document("$set",new Document("thumbup",100));
//        spit.updateOne(bson,bson1);
    }

    //获得MongoDB数据库对象
    private static MongoCollection<Document> getCollection() {
        //构建MongoDB客户端对象，连接指定的host
        MongoClient mongoClient = new MongoClient("192.168.25.130");
        //通过客户端获得指定名称的数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("spitdb");
        //获得数据库中指定名称的Collection（表）
        MongoCollection<Document> collection = mongoDatabase.getCollection("spit");
        return collection;
    }

    //遍历文档信息
    private void printDocuments(FindIterable<Document> documents) {
        for (Document document : documents) {
            //获得一个文档的键的集合
            Set<String> keySet = document.keySet();
            System.out.println("##########################");
            //依次取出每一个文档的key对应的value
            for (String key : keySet) {
                System.out.println(key + " = " + document.get(key));
            }
        }
        System.out.println("##########################");
    }

}
