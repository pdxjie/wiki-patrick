package com.pdx.entity.req;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class EbookSaveReq implements Serializable {

    private Long id;

    private String name;

    private Integer categoryId;

    private Integer categoryPid;

    private String description;

    private String cover;

    private Integer docCount;

    private Integer viewCount;

    private Integer voteCount;

}
