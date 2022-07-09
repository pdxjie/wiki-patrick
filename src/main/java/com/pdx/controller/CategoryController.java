package com.pdx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pdx.entity.Category;
import com.pdx.entity.Ebook;
import com.pdx.entity.req.CategoryQueryReq;
import com.pdx.entity.req.CategorySaveReq;
import com.pdx.entity.resp.CategoryResp;
import com.pdx.entity.resp.EbookResp;
import com.pdx.entity.resp.ResultData;
import com.pdx.service.CategoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:28
 * @Description
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultData list(@Valid CategoryQueryReq reqVo){
        ResultData<CategoryResp<Category>> resultData = new ResultData<>();
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        //条件查询
        if (!StringUtils.isEmpty(reqVo.getName())){
            wrapper.like("name", reqVo.getName());
        }
        Page<Category> page = new Page<>(reqVo.getPage(),reqVo.getSize());

        Page<Category> ebookPage = categoryService.page(page, wrapper);
        CategoryResp<Category> categoryResp = new CategoryResp<>();
        categoryResp.setTotal(ebookPage.getTotal());
        categoryResp.setList(ebookPage.getRecords());
        resultData.setData(categoryResp);
        resultData.setMessage("操作成功");
        return resultData;
    }

    @PostMapping("/saveOrUpdate")
    public ResultData saveOrUpdate(@Valid @RequestBody(required = false) CategorySaveReq req){
        ResultData resultData = new ResultData();
        Category category = new Category();
        BeanUtils.copyProperties(req,category);
        //如果ID为空则说明是新增
        if (ObjectUtils.isEmpty(req.getId())){
            categoryService.save(category);
        }else {
            categoryService.updateById(category);
        }
        resultData.setSuccess(true);
        resultData.setMessage("修改成功");
        return resultData;
    }

    @DeleteMapping("/delete/{id}")
    public ResultData deleteById(@PathVariable("id")String id){
        ResultData resultData = new ResultData();
        categoryService.removeById(id);
        resultData.setMessage("删除成功");
        resultData.setSuccess(true);
        return resultData;
    }



}
