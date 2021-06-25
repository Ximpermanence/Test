package com.example.demo.controller;

import com.example.demo.service.excel.ExcelImportPoiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:导入前端控制器
 * @author: chenhao
 * @create:2021/6/25 10:00
 **/
@RestController
@RequestMapping(value = "/import")
@Api(tags = "导入")
public class ExcelImportController {

    @Autowired
    private ExcelImportPoiService excelImportPoiService;

    @GetMapping("/toHtml")
    String test(HttpServletRequest request) {

        return "excelImport";

    }

    //处理文件上传
    @ResponseBody//返回json数据
    @RequestMapping(value = "/class", method = RequestMethod.POST)
    @ApiOperation("导入班级")
    public String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return excelImportPoiService.importClass(file);

    }

}
