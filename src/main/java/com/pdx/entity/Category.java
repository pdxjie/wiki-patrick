package com.pdx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-09 10:42
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category {

    private String id;

    private String pid;

    private String name;

    private Integer sort;
}
