package com.example.demo.entity.TestJs.res;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.TestJs.PagIng;
import com.example.demo.entity.TestJs.dto.GetLabourerDTO;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: chenhao
 * @create:2020/10/19 16:36
 **/
@Data
public class GetLabourerListPageRes {

    private Boolean isSuccess;

    private String message;

    private String data;

    private Integer resultType;

    private PagIng pagIng;

}
