package com.example.demo;

import lombok.Data;

/**
 * @description: 通用result1
 * @author: chenhao
 * @create:2021/2/4 14:39
 **/
@Data
public class Result1<T, F> {
    private T data;
    private F data2;
}
