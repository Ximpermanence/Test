package com.example.demo.controller;

import com.example.demo.service.excel.ExcelImportPoiService;
import com.example.demo.service.excel.ExcelExportPoiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: chenhao
 * @create:2021/6/25 10:00
 **/
@RestController
@RequestMapping(value = "/export")
@Api(tags = "导出")
public class ExcelExportController {

    @Autowired
    private ExcelImportPoiService excelImportPoiService;
    @Autowired
    private ExcelExportPoiService excelExportPoiService;

    @GetMapping("/toHtml")
    String test(HttpServletRequest request) {

        return "excelExport";

    }

    @GetMapping("/class")
    @ApiOperation("excel班级导出")
    public void classExcel() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        excelExportPoiService.exportClassExcel(request, response);
    }

}
