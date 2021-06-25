package com.example.demo.util;

import com.example.demo.entity.Class;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description: poi导入导出excel
 * @author: chenhao
 * @create:2020/8/17 13:12
 **/
@Slf4j
public class ExcelPoiUtil {

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


    /**
     * 导入Excel
     * @param file
     * @return
     */
    public static List<Object[]> importExcel(MultipartFile file) {
        log.info("导入解析开始，file:{}", file.toString());
        try {
            List<Object[]> list = new ArrayList<>();
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[row.getPhysicalNumberOfCells()];
                int index = 0;
                for (Cell cell : row) {
                    if (Objects.equals(cell.getCellType(), CellType.NUMERIC)) {
                        objects[index] = (int) cell.getNumericCellValue();
                    }
                    if (Objects.equals(cell.getCellType(), CellType.STRING)) {
                        objects[index] = cell.getStringCellValue();
                    }
                    if (Objects.equals(cell.getCellType(), CellType.BOOLEAN)) {
                        objects[index] = cell.getBooleanCellValue();
                    }
                    if (Objects.equals(cell.getCellType(), CellType.ERROR)) {
                        objects[index] = cell.getErrorCellValue();
                    }
                    index++;
                }
                list.add(objects);
            }
            log.info("导入文件解析成功！");
            return list;
        } catch (Exception e) {
            log.info("导入文件解析失败！");
            e.printStackTrace();
        }
        return null;
    }

    //测试导入
    public static void main(String[] args) {
        List<Class> res = new ArrayList<>();
        try {
            final String fileName = "D:/class模板.xls";
            InputStream inputStream = new FileInputStream(fileName);

            MultipartFile file = new MultipartFile() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public String getOriginalFilename() {
                    return null;
                }

                @Override
                public String getContentType() {
                    return null;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public long getSize() {
                    return 0;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return new byte[0];
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return  new FileInputStream(fileName);
                }

                @Override
                public void transferTo(File dest) throws IOException, IllegalStateException {

                }
            };
            List<Object[]> list = importExcel(file);
            for (int i = 0; i < list.size(); i++) {
                Class param = new Class();
                param.setId((int)(list.get(i)[0]));
                param.setName(String.valueOf(list.get(i)[1]));
                param.setTid((int)(list.get(i)[2]));
                System.out.println(param.toString());
                res.add(param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(1);
    }
}
