package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: chenhao
 * @create:2020/9/7 17:18
 **/
@Data
public class Structure implements Serializable {

    private static final long serialVersionUID = 6567486553209187949L;
//    private String fieldName;
//
//    private Boolean blank;
//
//    private Integer sort;
//
//    private String fieldComment;
//
//    private String fieldType;
    private String columnName;
    private String dataType;
}
