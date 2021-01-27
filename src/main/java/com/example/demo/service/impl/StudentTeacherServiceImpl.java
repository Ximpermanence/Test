package com.example.demo.service.impl;

import com.example.demo.entity.StudentTeacher;
import com.example.demo.mapper.StudentTeacherMapper;
import com.example.demo.service.StudentTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生教师关联表 服务实现类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Service
public class StudentTeacherServiceImpl extends ServiceImpl<StudentTeacherMapper, StudentTeacher> implements StudentTeacherService {

}
