package com.pdx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pdx.entity.Ebook;
import com.pdx.entity.req.EbookQueryReq;
import com.pdx.entity.req.EbookSaveReq;
import com.pdx.entity.resp.EbookResp;
import com.pdx.entity.resp.ResultData;
import com.pdx.service.EbookService;
import com.pdx.util.CopyUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

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

    @GetMapping("/list")
    public ResultData list(@Valid EbookQueryReq reqVo){
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

    @PostMapping("/saveOrUpdate")
    public ResultData saveOrUpdate(@Valid @RequestBody(required = false) EbookSaveReq req){
        ResultData resultData = new ResultData();
        Ebook ebook = new Ebook();
        BeanUtils.copyProperties(req,ebook);
        //如果ID为空则说明是新增
        if (ObjectUtils.isEmpty(req.getId())){
            ebookService.save(ebook);
        }else {
            ebookService.updateById(ebook);
        }
        resultData.setSuccess(true);
        resultData.setMessage("修改成功");
        return resultData;
    }

    @DeleteMapping("/delete/{id}")
    public ResultData deleteById(@PathVariable("id")String id){
        ResultData resultData = new ResultData();
        ebookService.removeById(id);
        resultData.setMessage("删除成功");
        resultData.setSuccess(true);
        return resultData;
    }



}
