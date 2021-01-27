package com.example.demo.mapper;

import com.example.demo.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 教师表 Mapper 接口
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    /**
     * 获取指定性别的老师
     * @param gender
     * @return
     */
//    @Select("  select *from teacher where teacher.gender = #{gender}")
    List<Teacher> getSameSexTeacher(@Param("gender") String gender);
}
