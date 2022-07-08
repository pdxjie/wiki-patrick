package com.pdx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pdx.entity.Ebook;
import com.pdx.entity.req.EbookReq;
import com.pdx.entity.resp.EbookResp;
import com.pdx.entity.resp.ResultData;
import com.pdx.service.EbookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:28
 * @Description
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @PostMapping("/list")
    public ResultData list(@RequestBody(required = false) EbookReq reqVo){
        ResultData<EbookResp<Ebook>> resultData = new ResultData<>();
        QueryWrapper<Ebook> wrapper = new QueryWrapper<>();
        //条件查询
        if ((reqVo.getId() != null)){
            wrapper.eq("id",reqVo.getId());
        }
        if (!StringUtils.isEmpty(reqVo.getName())){
            wrapper.like("name", reqVo.getName());
        }
        Page<Ebook> page = new Page<>(reqVo.getPage(),reqVo.getSize());

        Page<Ebook> ebookPage = ebookService.page(page, wrapper);
        EbookResp<Ebook> ebookResp = new EbookResp<>();
        ebookResp.setTotal(ebookPage.getTotal());
        ebookResp.setList(ebookPage.getRecords());
        resultData.setData(ebookResp);
        resultData.setMessage("操作成功");
        return resultData;
    }
}
