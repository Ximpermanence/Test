package com.example.demo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Class;
import com.example.demo.pojo.vo.ClassStudentVO;
import com.example.demo.service.ClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@RestController
@RequestMapping(value = "/class")
@Api(tags = "班级相关")
public class ClassController {

    @Autowired
    private ClassService classService;



    @GetMapping("/list_class")
    @ApiOperation("class列表")
    public List<Class> listClass(){
       return classService.list();
    }

    @GetMapping("/classStudent")
    @ApiOperation("class和student关联")
    public List<ClassStudentVO> classStudent(Page page){
        return classService.pageClassStudent(page);
    }

    @GetMapping("/insertClass")
    @ApiOperation("添加新班级")
    // TODO: 2021/6/29 @陈浩 这里要修改 要写个globalException进行处理
    public String insertClass(@Valid Class param){
        return classService.insertClass(param);
    }
}

