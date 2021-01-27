package com.example.demo.controller;


import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/student")
    public List<Student> findAll(){
        return studentService.findAll();
    }

    @GetMapping("/findById")
    public Student findById(Integer id){
        return studentService.findBySid(id);
    }

    @PostMapping("/student/{id}")
    public int del(@PathVariable("sid") Integer id){
        return studentService.del(id);
    }

    @PutMapping("/student")
    public int modify(Student student){
        return studentService.modify(student);
    }

    @GetMapping("/student_noRedis")
    public List<Student> listStudent(){
        return studentService.list();
    }
}

