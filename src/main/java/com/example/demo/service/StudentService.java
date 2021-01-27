package com.example.demo.service;

import com.example.demo.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
public interface StudentService extends IService<Student> {

    List<Student> findAll();

    Student findBySid(Integer sid);

    int del(Integer sid);

    int modify(Student student);

    int add(Student student);

}
