package com.example.demo.service.excel.impl;

import com.example.demo.entity.Class;
import com.example.demo.service.excel.ExcelImportPoiService;
import com.example.demo.util.ExcelPoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: poi导入服务实现类
 * @author: chenhao
 * @create:2021/6/25 11:14
 **/
@Service
public class ExcelImportPoiServiceImpl implements ExcelImportPoiService {

    /**
     * 导入班级
     * @param file
     * @return
     */
    @Override
    public String importClass(MultipartFile file) {
        List<Object[]> list = ExcelPoiUtil.importExcel(file);
        List<Class> classList = new ArrayList<>();
//        for (Object[] objects : list) {
//            Class param = new Class();
//            param.setId((Integer) objects[0]);
//            param.setName(String.valueOf(objects[1]));
//            param.setTid((Integer) (objects[2]));
//            System.out.println(param.toString());
//            classList.add(param);
//        }
        list.iterator().forEachRemaining(t-> {
            Class param = new Class();
            Object[] array  = t;
            param.setId((Integer) array[0]);
            param.setName(String.valueOf(array[1]));
            param.setTid((Integer) (array[2]));
            classList.add(param);
        });
        return "导入的数据为 :"+classList.toString();
    }
}
