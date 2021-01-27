package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @description: 表-实体
 * @author: chenhao
 * @create:2020/8/17 13:12
 **/
@Slf4j
public class ExcelUtil {

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();

        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);//加上上下居中，文本会变成受保护状态

        //字体
        Font font = wb.createFont();
        //字体
        font.setFontName("宋体");
        //字号
        font.setFontHeightInPoints((short) 16);
        //加粗
        font.setBold(true);
        style.setFont(font);

        //声明列对象
        HSSFCell cell = null;
        //取消受保护试图  无效
        sheet.setActive(true);
        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * 设置表格宽高
     *
     * @param datas
     * @param height
     * @param width
     * @param row
     * @param sheet
     */
    public static void setHeightWidth(String[][] datas, Integer height, Integer width, HSSFRow row, HSSFSheet sheet) {

        if (width == null) {
            width = 24;
        }
        if (height == null) {
            height = 30;
        }
        if (datas == null || datas.length <= 0) {
            row = sheet.getRow(0);
            row.setHeightInPoints(height);
            for (int i = 0; i < 100; i++) {
                sheet.setColumnWidth(i, 256 * width + 184);
            }
            return;
        }
        //创建表格之后设置行高与列宽
        for (int i = 0; i < datas.length; i++) {
            row = sheet.getRow(i);
            if (i == 0) {
                //设置表头行高
                row.setHeightInPoints(height);
            }
            //设置行高
            else {
                row.setHeightInPoints(20);
            }
        }
        for (int j = 0; j < datas[0].length; j++) {
            //设置列宽
            //输入的是excel的散列值
            sheet.setColumnWidth(j, 256 * width + 184);
        }
    }

    //发送响应流方法 解决老版浏览器文件名乱码
    public static void setResponseHeader(HttpServletResponse response, HttpServletRequest request, String fileName) {
        try {
            try {
                // 设置文件名在不同主流浏览器上的编码
                String agent = request.getHeader("USER-AGENT").toLowerCase();
                response.setContentType("application/vnd.ms-excel");
                String codedFileName = URLEncoder.encode(fileName, "UTF-8");
                if (agent.contains("firefox")) {
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
                } else {
                    response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
