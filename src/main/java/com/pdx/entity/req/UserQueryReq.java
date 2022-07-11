package com.pdx.entity.req;

import lombok.Data;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-11 17:17
 * @Description
 */
@Data
public class UserQueryReq extends PageReq{

    private String loginName;

}
