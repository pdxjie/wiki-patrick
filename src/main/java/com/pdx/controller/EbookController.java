package com.pdx.controller;

import com.pdx.entity.Ebook;
import com.pdx.entity.resp.ResultData;
import com.pdx.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public ResultData<List<Ebook>> list(){
        ResultData<List<Ebook>> resultData = new ResultData<>();
        List<Ebook> list = ebookService.list();
        resultData.setData(list);
        return resultData;
    }
}
