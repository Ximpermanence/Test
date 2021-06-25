package com.example.demo.service.excel.impl;

import com.example.demo.entity.Class;
import com.example.demo.service.ClassService;
import com.example.demo.service.excel.ExcelExportPoiService;
import com.example.demo.util.ExcelPoiUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @description: poi导出服务实现类
 * @author: chenhao
 * @create:2020/8/17 11:40
 **/
@Service
public class ExcelExportPoiServiceImpl implements ExcelExportPoiService {

    @Autowired
    private ClassService classService;

    /**
     * 导出班级
     *
     * @param request
     * @param response
     */
    @Override
    public void exportClassExcel(HttpServletRequest request, HttpServletResponse response) {

        //1。获取数据
        List<Class> list = classService.list();


        //2.创建模板
        String[] title = {"ID", "班级名称", "班级编号"};
        String fileName = "班级表2.xls";
        String sheetName = "sheet1";

        //3.填写内容
        String[][] content = new String[list.size()][title.length];
        for (int i = 0; i < list.size(); i++) {
//            ClassExcelVO vo =list.get(i);
            content[i][0] = String.valueOf(list.get(i).getId());
            content[i][1] = list.get(i).getName();
            content[i][2] = String.valueOf(list.get(i).getTid());

        }
        HSSFWorkbook workbook = ExcelPoiUtil.getHSSFWorkbook(sheetName, title, content, null);
        //设置样式
        ExcelPoiUtil.setHeightWidth(content, null, null, null, workbook.getSheet(sheetName));

        try {
            ExcelPoiUtil.setResponseHeader(response, request, fileName);
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
