package com.example.demo.config;

import com.example.demo.entity.Teacher;
import com.example.demo.service.TeacherService;
import com.example.demo.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:启动时将数据加载到内存
 * @author: chenhao
 * @create:2021/3/2 15:58
 **/

@Configuration
public class AutoLoad implements InitializingBean {

    @Autowired
    private TeacherService teacherService;

//    public static List<Teacher> teachers = new ArrayList<>();

    @Override
    public void afterPropertiesSet(){
        TeacherServiceImpl.teachers = teacherService.list();
    }
}
