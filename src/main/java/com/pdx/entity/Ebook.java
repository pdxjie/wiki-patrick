package com.pdx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:24
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("e_book")
public class Ebook implements Serializable {

    private String id;
    @NotNull(message = "【名称】不能为空")
    private String name;

    private String categoryId;

    private String categoryPid;

    private String description;

    private String cover;

    private Integer docCount;

    private Integer viewCount;

    private Integer voteCount;

}
