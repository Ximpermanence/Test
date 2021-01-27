package com.example.demo.service.excel;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 表-实体
 * @author: chenhao
 * @create:2020/8/17 11:19
 **/
@Service
public interface ExcelOutputService {
    void exportClassExcel(HttpServletRequest request, HttpServletResponse response);
}
