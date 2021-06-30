package com.example.demo.pojo.TestJs.res;


import com.example.demo.pojo.TestJs.PagIng;
import lombok.Data;

/**
 * @description:
 * @author: chenhao
 * @create:2020/10/19 16:36
 **/
@Data
public class GetLabourerListPageRes {

    private Boolean isSuccess;

    private String message;

    private String data;

    private Integer resultType;

    private PagIng pagIng;

}
