package com.example.demo.controller;


import com.example.demo.entity.Class;
import com.example.demo.service.ClassService;
import com.example.demo.service.excel.ExcelOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ClassController {

    @Autowired
    private ExcelOutputService excelOutputService;

    @Autowired
    private ClassService classService;

    @GetMapping("/classExcel")
    public void classExcel(Model model) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        excelOutputService.exportClassExcel(request, response);
    }

    @GetMapping("/list_class")
    public List<Class> listClass(){
       return classService.list();
    }

}

