package com.example.demo.service.excel;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description:poi导入服务类
 * @author: chenhao
 * @create:2021/6/25 11:14
 **/
public interface ExcelImportPoiService {

    /**
     * 导入班级
     * @param file
     * @return
     */
    String importClass(MultipartFile file);
}
