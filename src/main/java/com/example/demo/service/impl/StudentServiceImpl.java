package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //缓存系列
    @Override
    public List<Student> findAll() {
        String key = "student";
        ListOperations<String, Student> operations = redisTemplate.opsForList();

        //缓存存在
        if (redisTemplate.hasKey(key)) {
            return operations.range(key, 0, -1);
        } else {
            //得到学生集合
            List<Student> list = studentMapper.findAll();
            operations.leftPushAll(key, list);
            return list;
        }

    }

    @Override
    public Student findBySid(Integer sid) {
        String key = "student_"+ sid;
        ValueOperations<String,Student> operations = redisTemplate.opsForValue();

        //缓存存在
        if(redisTemplate.hasKey(key)){
            System.out.println("redis-siddata");
            return operations.get(key);
        }else {
            //得到学生对象
            Student student = studentMapper.findBySid(sid);
            //添加到缓存
            operations.set(key,student);
            System.out.println("student-siddata");
            return student;
        }
    }

    @Override
    public int del(Integer sid) {
        //删除数据库中的数据
        int count = studentMapper.del(sid);

        //缓存存在
        String key = "student_"+sid;
        if(redisTemplate.hasKey(key)){
            //删除对应缓存
            redisTemplate.delete(key);
        }
    return count;
    }

    @Override
    public int modify(Student student) {
        //修改数据库中的数据
        int count = studentMapper.modify(student);
        ValueOperations operations = redisTemplate.opsForValue();
        //缓存存在
        String key = "student_"+student.getSid();

        if(redisTemplate.hasKey(key)){
            //更新缓存
            Student stu = studentMapper.findBySid(student.getSid());
            operations.set(key,stu);
        }
        return count;
    }

    @Override
    public int add(Student student) {
        //添加数据
        int count = studentMapper.add(student);
        return count;
    }
}
