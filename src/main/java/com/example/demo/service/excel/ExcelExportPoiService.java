package com.example.demo.service.excel;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: poi导出服务类
 * @author: chenhao
 * @create:2020/8/17 11:19
 **/
@Service
public interface ExcelExportPoiService {

    /**
     * 导出班级
     * @param request
     * @param response
     */
    void exportClassExcel(HttpServletRequest request, HttpServletResponse response);
}
