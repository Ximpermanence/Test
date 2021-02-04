package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.Result1;
import com.example.demo.entity.Class;
import com.example.demo.entity.Student;
import com.example.demo.entity.vo.TestVO;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.ClassService;
import com.example.demo.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @description:测试controller
 * @author: chenhao
 * @create:2021/2/3 9:25
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;


    @GetMapping("1")
    private List<TestVO> test1() {
        List<Class> classList = classService.list();
        List<TestVO> collect = classList.stream().map(t -> {
            TestVO testVO = new TestVO();
            BeanUtils.copyProperties(t, testVO);
            return testVO;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 尝试多线程产生的问题
     *
     * @return
     */
    @GetMapping("2")
    private Result1<Integer, List<Student>> test2() {
        QueryWrapper<Student> qr = new QueryWrapper<>();
        qr.eq("name", "a");
        CompletableFuture<List<Student>> thread1 = CompletableFuture.supplyAsync(() -> studentMapper.pageStudent(qr));
        qr.eq("gender", "男");
        CompletableFuture<Integer> thread2 = CompletableFuture.supplyAsync(() -> studentService.count(qr));
        int count = thread2.join();
        List<Student> students = thread1.join();
        Result1<Integer, List<Student>> result1 = new Result1<>();
        result1.setData(count);
        result1.setData2(students);
        return result1;
    }
}
