package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    public static List<Teacher> teachers = new ArrayList<>();

    /**
     * 查看每次调用这个方法时会查询几次数据库
     */
    @Override
    public String getTeacherNameByAge(Integer age) {
        final String[] a = new String[1];
        teachers.stream().filter(t -> t.getAge().equals(age)).findFirst().ifPresent(t -> a[0] = t.getName());
        return a[0];
    }
}
