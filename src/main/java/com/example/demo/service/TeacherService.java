package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Teacher;

/**
 * <p>
 * 教师表 服务类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 获取所给age的教师姓名
     * @param age
     * @return
     */
    String getTeacherNameByAge(Integer age);

}
