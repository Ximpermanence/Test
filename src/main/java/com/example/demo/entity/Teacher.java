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
 * 教师表
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Teacher对象", description="教师表")
public class Teacher implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    @ApiModelProperty(value = "男，女")
    private String gender;


}
