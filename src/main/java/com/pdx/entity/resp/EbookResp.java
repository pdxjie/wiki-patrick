package com.pdx.entity.resp;

import lombok.Data;

import java.util.List;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-08 17:52
 * @Description
 */
@Data
public class EbookResp<T> {
    private Long total;

    private List<T> list;


}