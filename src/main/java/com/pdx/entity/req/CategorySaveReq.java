package com.pdx.entity.req;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-09 10:42
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveReq {

    private String id;

    private String pid;

    @NotNull(message = "名称不能为空")
    private String name;

    private Integer sort;
}
