package com.pdx.mapper;

import com.pdx.entity.Ebook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:26
 * @Description
 */
@Mapper
public interface EbookMapper {
    List<Ebook> list();

}
