package com.example.demo.entity.vo.dataDemo.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: chenhao
 * @create:2020/8/25 13:48
 **/
@Data
public class IndexData {

    @ExcelProperty(value = "字符串标题",index = 0)
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty(value = "数字标题",index = 4)
    private Double doubleData;
}
