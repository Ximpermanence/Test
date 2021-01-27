package com.example.demo.entity.vo.dataDemo.write;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 * @description:
 * @author: chenhao
 * @create:2020/8/25 13:53
 **/
public class ComplexHeadData {
    @ExcelProperty({"主标题","字符串标题"})
    private String string;
    @ExcelProperty({"主标题","日期标题"})
    private Date date;
    @ExcelProperty(value = {"主标题","数字标题"},index = 4)
    private Double doubleData;
}
