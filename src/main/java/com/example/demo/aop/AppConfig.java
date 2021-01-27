package com.example.demo.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description: 表-实体
 * @author: chenhao
 * @create:2020/8/19 13:54
 **/
@Configuration
@ComponentScan("com.example.demo.aop")
@EnableAspectJAutoProxy
public class AppConfig {
}
