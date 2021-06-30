package com.example.demo.pojo.TestJs.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: chenhao
 * @create:2020/10/19 16:40
 **/
@Data
public class GetLabourerDTO {

    @ApiModelProperty("no")
    private Integer no;

    @ApiModelProperty("身份证号")
    private String idCardNo;

    @ApiModelProperty("民工唯一标识")
    private Long lid;

    @ApiModelProperty("民工唯一标识 - 字符串")
    private String lidF;

    @ApiModelProperty("联系电话")
    private String contactNumber;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("工种")
    private String workTypeName;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("就职状态")
    private Boolean vocationState;

    @ApiModelProperty("不良行为")
    private Boolean badBehavior;

    @ApiModelProperty("所属项目")
    private String projectName;
}
