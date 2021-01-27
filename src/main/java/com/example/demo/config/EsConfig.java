package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: chenhao
 * @create:2020/10/14 17:21
 **/
//@Configuration
public class EsConfig {

    public RestHighLevelClient restHighLevelClient(){

        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200)));
    }
}
