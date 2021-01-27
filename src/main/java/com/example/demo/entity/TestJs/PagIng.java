package com.example.demo.entity.TestJs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: chenhao
 * @create:2020/10/19 16:43
 **/
public class PagIng {

    @Getter
    @Setter
    @ApiModelProperty(value = "当前页数")
    private Integer pageIndex;

    @Getter
    @Setter
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    @Getter
    @Setter
    @ApiModelProperty(value = "总条数")
    private Long total;

    public PagIng() {
    }

    public PagIng(Integer pageIndex, Integer pageSize, Long total) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static  PagIng newPagIng(Integer pageIndex,Integer pageSize,Long total) {
        return new PagIng(pageIndex,pageSize,total);
    }
}
