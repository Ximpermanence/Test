package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.vo.ClassStudentVO;

import java.util.List;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
public interface ClassService extends IService<Class> {

    /**
     * 获取班级以及对应的学生
     * @param page
     * @return
     */
    List<ClassStudentVO> pageClassStudent(Page page);

    String insertClass(Class param);
}
