package com.example.demo.pojo.vo;

import com.example.demo.entity.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:班级以及对应的学生VO
 * @author: chenhao
 * @create: 2021/6/2
 **/

@Data
public class ClassStudentVO {

    @ApiModelProperty("class的id")
    private Integer cid;

    @ApiModelProperty("class的name")
    private String name;

    @ApiModelProperty(value = "班主任id")
    private Integer tid;

    @ApiModelProperty("班主任姓名")
    private String teacherName;

    @ApiModelProperty("班级所属学生")
    private List<Student> students;
}
