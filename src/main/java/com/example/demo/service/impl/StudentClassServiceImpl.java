package com.example.demo.service.impl;

import com.example.demo.entity.StudentClass;
import com.example.demo.mapper.StudentClassMapper;
import com.example.demo.service.StudentClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级学生关联表 服务实现类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Service
public class StudentClassServiceImpl extends ServiceImpl<StudentClassMapper, StudentClass> implements StudentClassService {

}
