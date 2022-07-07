package com.pdx.service.Impl;

import com.pdx.entity.Ebook;
import com.pdx.mapper.EbookMapper;
import com.pdx.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:27
 * @Description
 */
@Service
public class EbookServiceImpl implements EbookService {

    @Autowired
    private EbookMapper bookMapper;

    @Override
    public List<Ebook> list() {
        return bookMapper.list();
    }
}
