package com.pdx.entity.req;

import lombok.Data;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 16:38
 * @Description
 */
@Data
public class EbookQueryReq extends PageReq{

    private Long id;

    private String name;
}
