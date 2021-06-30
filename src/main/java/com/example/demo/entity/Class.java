package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.pojo.param.BaseImportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Class对象", description="班级表")
@Accessors(chain = true)
public class Class extends BaseImportParam implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull(message = "请传班级名称")
    private String name;

    @ApiModelProperty(value = "班主任id")
    @NotNull(message = "请传班主任id")
    private Integer tid;


    @Override
    public String getAll() {
        return "tid,name,id";
    }
}
