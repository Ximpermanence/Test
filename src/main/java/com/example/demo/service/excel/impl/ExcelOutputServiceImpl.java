package com.example.demo.service.excel.impl;

import com.example.demo.entity.Class;
import com.example.demo.entity.vo.ClassExcelVO;
import com.example.demo.service.ClassService;
import com.example.demo.service.excel.ExcelOutputService;
import com.example.demo.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 使用poi导出
 * @author: chenhao
 * @create:2020/8/17 11:40
 **/
@Service
public class ExcelOutputServiceImpl implements ExcelOutputService {

    @Autowired
    private ClassService classService;

    @Override
    public void exportClassExcel(HttpServletRequest request, HttpServletResponse response) {

        List<Class> list = classService.list();
        List<ClassExcelVO> classExcelVOList = new ArrayList<>();
        list.forEach(l->{
            ClassExcelVO excelVO = new ClassExcelVO();
            BeanUtils.copyProperties(l,excelVO);
            classExcelVOList.add(excelVO);
        });
        String[] title = {"ID", "班级名称", "班级编号"};
        String fileName = "班级表2.xls";
        String sheetName = "sheet1";
        String[][] content = new String[list.size()][title.length];

        for (int i = 0; i < classExcelVOList.size(); i++) {
//            ClassExcelVO vo =list.get(i);
            content[i][0] = String.valueOf(classExcelVOList.get(i).getId());
            content[i][1] = classExcelVOList.get(i).getName();
            content[i][2] = String.valueOf(classExcelVOList.get(i).getTid());

        }
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //设置样式
        ExcelUtil.setHeightWidth(content, null, null, null, workbook.getSheet(sheetName));

        try {
            ExcelUtil.setResponseHeader(response, request, fileName);
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
