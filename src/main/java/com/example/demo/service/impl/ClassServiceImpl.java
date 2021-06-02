package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Class;
import com.example.demo.entity.vo.ClassStudentVO;
import com.example.demo.mapper.ClassMapper;
import com.example.demo.service.ClassService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {


    /**
     * 获取班级以及对应的学生
     *
     * @param page
     * @return
     */
    @Override
    public List<ClassStudentVO> pageClassStudent(Page page) {
//        Page page1 = new Page(1,2);

        return baseMapper.listClassStudent();
    }
}
