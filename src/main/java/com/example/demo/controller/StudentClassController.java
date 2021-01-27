package com.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 班级学生关联表 前端控制器
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/studentClass")
public class StudentClassController {

    @GetMapping("/ac")
    public String acc(){
        return "aaa";
    }

}

