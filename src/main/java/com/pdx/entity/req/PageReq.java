package com.pdx.entity.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-08 17:43
 * @Description
 */
@Data
public class PageReq {

    private Integer page;
    
    @NotNull(message = "每页条数不能为空")
    @Max(value = 100,message = "每页天数不能超过100")
    private Integer size;
}
