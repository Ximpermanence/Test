package com.example.demo.aop;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @description: 表-实体
 * @author: chenhao
 * @create:2020/8/19 13:49
 **/
@Component
public class UserDao implements Serializable {
    private static final long serialVersionUID = 9145050820584130038L;

    public void query(){
        System.out.println("query");
    }
}
