package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.EsConfig;
import com.example.demo.entity.Class;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: chenhao
 * @create:2020/10/14 17:36
 **/
@SpringBootTest
public class EsTest {

    @Resource
    private RestHighLevelClient client;

//    @Autowired
//    private EsConfig client;

    @Resource
    RestClient restClient;

    public final static String TEST_INDEX = "test_class";


    public final static String ADD_INDEX_ID_ONE = "1";

    public final static String DELETE_INDEX_ID = "2";

    public final static String UPDATE_INDEX_ID = "2";

    public final static String SEARCH_INDEX_ID = "1";


    /**
     * 使用IndexRequest增或改
     *
     * @throws IOException
     */
    @Test
    void test1() throws IOException {

        Class clas1 = new Class(1, "一班非一", 1);
        String JsonString = JSON.toJSONString(clas1);
        IndexRequest indexRequest = new IndexRequest(TEST_INDEX).id("1").source(JsonString, XContentType.JSON);
        IndexResponse bulkItemResponses = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(0);
    }


    /**
     * 使用bulkrequest增或改
     */
    @Test
    void test2() throws IOException {
        BulkRequest bulkRequest = new BulkRequest(TEST_INDEX);
        Class clas2 = new Class(2, "二班", 2);
        bulkRequest.add(new IndexRequest(TEST_INDEX).id("2").source(JSON.toJSONString(clas2), XContentType.JSON));
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(2);
    }

    /**
     * 删
     */
    @Test
    void test3() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(TEST_INDEX);
        deleteRequest.id(DELETE_INDEX_ID);
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(1);
    }

    /**
     * 改
     */
    @Test
    void test4() throws IOException {

        Class clas1 = new Class(2, "2班非224", 2);
        String JsonString = JSON.toJSONString(clas1);
        UpdateRequest request = new UpdateRequest(TEST_INDEX, UPDATE_INDEX_ID)
                .doc(JsonString, XContentType.JSON);
        try {
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            //文档没有找到
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("文档没有找到");
            }
        }
        System.out.println(2);
    }


    /**
     * 查_get(需要id)
     * @throws IOException
     */
    @Test
    void test5() throws IOException {
        GetRequest getRequest = new GetRequest(TEST_INDEX,SEARCH_INDEX_ID);
        GetResponse getResponse = client.get(getRequest,RequestOptions.DEFAULT);
        String jsonString = getResponse.getSourceAsString();
        System.out.println(jsonString);
    }

    /**
     * 查_serach
     *
     * @throws Exception
     */
    @Test
    void test6() throws IOException {
        //构建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(TEST_INDEX);
        //构建搜索源对象
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        //搜索源搜索方式
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        //向搜索请求对象中设置搜索源
//        searchRequest.source(searchSourceBuilder);
        //执行搜索,向ES发起Http请求,获得结果对象
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //获得匹配总记录
        Long totalHits = hits.getTotalHits().value;
        //得到匹配度高的文档
        SearchHit[] searchHits = hits.getHits();

        System.out.println(searchHits[0].getSourceAsString());
        String jsonString = searchHits[0].getSourceAsString();
        Class cla = JSON.parseObject(jsonString,Class.class);
        System.out.println(2);
//        JSONArray.parse(searchHits);
//        for (SearchHit searchHit : searchHits) {
//            System.out.println(searchHit.toString());
//        }
    }
}
