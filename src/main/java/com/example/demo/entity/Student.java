package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Student对象", description = "学生表")
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Student implements Serializable {

    private static final long serialVersionUID = 5138577323438599496L;
    @TableId(value = "sid", type = IdType.AUTO)
    private Integer sid;

    private String name;

    private Integer age;

    @ApiModelProperty(value = "1、男，2、女", hidden = true)
    private String gender;

    private Integer nationCode;

}
