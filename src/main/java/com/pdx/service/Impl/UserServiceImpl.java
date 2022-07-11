package com.pdx.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pdx.entity.Ebook;
import com.pdx.entity.User;
import com.pdx.mapper.EbookMapper;
import com.pdx.mapper.UserMapper;
import com.pdx.service.EbookService;
import com.pdx.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 00:27
 * @Description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
