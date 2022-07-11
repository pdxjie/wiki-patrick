package com.pdx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pdx.entity.User;
import com.pdx.entity.req.UserQueryReq;
import com.pdx.entity.req.UserSaveReq;
import com.pdx.entity.resp.UserResp;
import com.pdx.entity.resp.ResultData;
import com.pdx.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:28
 * @Description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResultData list(@Valid UserQueryReq reqVo){
        ResultData<UserResp<User>> resultData = new ResultData<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //条件查询
        if ((reqVo.getLoginName() != null)){
            wrapper.eq("login_name",reqVo.getLoginName());
        }
        Page<User> page = new Page<>(reqVo.getPage(),reqVo.getSize());

        Page<User> userPage = userService.page(page, wrapper);
        UserResp<User> userResp = new UserResp<>();
        userResp.setTotal(userPage.getTotal());
        userResp.setList(userPage.getRecords());
        resultData.setData(userResp);
        resultData.setMessage("操作成功");
        return resultData;
    }

    @PostMapping("/saveOrUpdate")
    public ResultData saveOrUpdate(@Valid @RequestBody(required = false) UserSaveReq req){
        ResultData resultData = new ResultData();
        User user = new User();
        BeanUtils.copyProperties(req,user);
        //如果ID为空则说明是新增
        if (ObjectUtils.isEmpty(req.getId())){
            user.setStatus(false);
            userService.save(user);
        }else {
            userService.updateById(user);
        }
        resultData.setSuccess(true);
        resultData.setMessage("修改成功");
        return resultData;
    }

    @DeleteMapping("/delete/{id}")
    public ResultData deleteById(@PathVariable("id")String id){
        ResultData resultData = new ResultData();
        userService.removeById(id);
        resultData.setMessage("删除成功");
        resultData.setSuccess(true);
        return resultData;
    }

    @GetMapping("/find-user/{id}")
    public ResultData findUserById(@PathVariable("id")String id){
        ResultData resultData = new ResultData();
        User user = userService.getById(id);
        userService.updateById(user);
        resultData.setData(user);
        resultData.setSuccess(true);
        return resultData;
    }



}
