package com.pdx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pdx.entity.Ebook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:26
 * @Description
 */
@Mapper
public interface EbookMapper extends BaseMapper<Ebook> {

}
