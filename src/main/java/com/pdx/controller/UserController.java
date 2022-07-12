package com.pdx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pdx.entity.User;
import com.pdx.entity.req.UserQueryReq;
import com.pdx.entity.req.UserSaveReq;
import com.pdx.entity.resp.UserResp;
import com.pdx.entity.resp.ResultData;
import com.pdx.exception.BusinessException;
import com.pdx.exception.BusinessExceptionCode;
import com.pdx.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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
        if ((reqVo.getName() != null)){
            wrapper.like("name",reqVo.getName());
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
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        BeanUtils.copyProperties(req,user);
        //如果ID为空则说明是新增
        if (ObjectUtils.isEmpty(req.getId())){
            User byLoginName = selectUserByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(byLoginName)) {
                user.setStatus(true);
                user.setAvatar("https://portrait.gitee.com/uploads/avatars/user/2829/8488080_gao-wumao_1651141916.png!avatar60");
                user.setCreateTime(new Date());
                userService.save(user);
            }else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        }else {
            user.setUpdateTime(new Date());
            user.setLoginName(null);
            user.setPassword(null);
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
    //修改用户状态
    @GetMapping("/state/{id}")
    public ResultData updateState(@PathVariable("id")String id){
        ResultData resultData = new ResultData();
        User user = userService.getById(id);
        user.setStatus(!user.getStatus());
        userService.updateById(user);
        resultData.setSuccess(true);
        resultData.setMessage("修改成功");
        return resultData;
    }



    // 根据用户登录名获取用户信息
    private User selectUserByLoginName(String loginName){
        User user = userService.getOne(new QueryWrapper<User>().eq("login_name", loginName));
        if (user == null){
            return null;
        }else {
            return user;
        }
    }

}
