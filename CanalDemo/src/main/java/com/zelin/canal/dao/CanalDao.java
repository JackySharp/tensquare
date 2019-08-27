package com.zelin.canal.dao;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zelin.canal.pojo.Product;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现数据库与索引库同步
 */
public class CanalDao {

    //抓取binaryLog中的数据
    public void fetchData() throws IOException, SolrServerException {
        String destination = "example";
        //得到 CanalConnector 连接器对象
        CanalConnector canalConnector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("192.168.25.130", 11111),//此处的port指的是Canal服务器的端口
                destination, "", ""
        );
        //与 Canal 建立连接
        canalConnector.connect();
        //发布连接，主要监控指定数据库（“canaldb”）下的所有表，数据库在配置文件中指定
        //canalConnector.subscribe(".*\\..*");
        canalConnector.subscribe();
        //定义抓取的数据条数
        int batchSize = 5 * 1024;
        while (true) {
            //获取指定数量的数据
            Message message = canalConnector.getWithoutAck(batchSize);
            //得到 message id
            long batchId = message.getId();
            //得到 message 的条数
            int size = message.getEntries().size();
            //如果当前 message id 为-1或者 message 条数为空，则说明无数据可抓取
            if (batchId == -1 || size == 0) {
                //try {
                //    Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
            } else {
                //依据当前数据条目集合来抓取数据
                printEntry(message.getEntries());
            }
            //提交确认
            canalConnector.ack(batchId);
        }
    }

    private void printEntry(List<CanalEntry.Entry> entries) throws IOException, SolrServerException {
        //创建Solr服务器对象
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection2");
        //遍历数据条目集合
        for (CanalEntry.Entry entry : entries) {
            CanalEntry.EntryType entryType = entry.getEntryType();
            System.out.println("entryType=" + entryType);
            //得到数据条目类型，如果该类型是ROWDATA，说明正是要遍历的数据
            if (entryType == CanalEntry.EntryType.ROWDATA) {
                CanalEntry.RowChange rowChange = null;
                //反序列化数据，得到数据有变动的行
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                //得到造成行的数据变动的事件对象
                CanalEntry.EventType eventType = rowChange.getEventType();
                //得到该行的每一列的数据的集合
                List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
                //遍历这个列的数据集合
                for (CanalEntry.RowData rowData : rowDatasList) {
                    //如果造成该行数据变动的操作是新增或更新，则获取执行操作后的数据列表
                    if (eventType == CanalEntry.EventType.INSERT || eventType == CanalEntry.EventType.UPDATE) {
                        //得到新增或修改后的那行数据
                        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                        //将变动后的行数据变换为Map
                        Map<String, Object> map = convertToMap(afterColumnsList);
                        //构造Product对象
                        Product product = createProduct(map);
                        //将数据更新到Solr索引库
                        solrServer.addBean(product);
                        //提交更新索引库事物
                        solrServer.commit();
                    } else if (eventType == CanalEntry.EventType.DELETE) {
                        //得到被删除的那行数据
                        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
                        //将删除前的行数据变换为Map
                        Map<String, Object> map = convertToMap(beforeColumnsList);
                        //依据 product id 删除Solr索引库对应的记录
                        solrServer.deleteById(map.get("pid") + "");
                        //提交更新索引库事物
                        solrServer.commit();
                    }
                }
            }
        }
    }

    private Product createProduct(Map<String, Object> map) {
        Product product = new Product();
        product.setPid(Integer.parseInt(map.get("pid") + ""));
        product.setName(map.get("name") + "");
        if (null != map.get("catalog_name") && !"".equals("catalog_name")) {
            product.setCatalog_name(map.get("catalog_name") + "");
        }
        if (null != map.get("price") && !"".equals(map.get("price"))) {
            product.setPrice(Float.parseFloat(map.get("price") + ""));
        }
        product.setPicture(map.get("picture") + "");
        product.setDescription(map.get("description") + "");
        return product;
    }

    private Map<String, Object> convertToMap(List<CanalEntry.Column> afterColumnsList) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName() + " " + column.getValue());
            map.put(column.getName(), column.getValue());
        }
        return map;
    }

}
