package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from student")
    List<Student> findAll();

    @Select("select * from student where id = #{sid}")
    Student findBySid(Integer sid);

    @Select("delete from student where id=#{sid}")
    int del(Integer sid);

    @Update("update student set name = #{name},age=#{age},gender=#{gender},nationCode=#{nationCode}")
    int modify(Student student);

    @Insert("insert into student(name,age,gender,nationCode)")
    int add(Student student);

    /**
     * 分页获取学生数量
     * @param wrapper
     * @return
     */
    List<Student> pageStudent(@Param(Constants.WRAPPER) QueryWrapper wrapper);
}
