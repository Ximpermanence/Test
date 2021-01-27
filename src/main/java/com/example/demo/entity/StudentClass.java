package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 班级学生关联表
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StudentClass对象", description="班级学生关联表")
public class StudentClass implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cid;

    private Integer sid;


}
