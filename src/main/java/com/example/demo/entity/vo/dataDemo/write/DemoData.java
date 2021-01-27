package com.example.demo.entity.vo.dataDemo.write;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: chenhao
 * @create:2020/8/25 10:33
 **/
@Data
public class DemoData {

    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;

    /**
     * 忽略该字段
     */
    @ExcelIgnore
    private String ignore;
}
