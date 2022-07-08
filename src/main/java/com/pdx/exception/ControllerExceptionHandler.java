package com.pdx.exception;

import com.pdx.entity.resp.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-09 01:17
 * @Description 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultData validExceptionHandler(BindException e){
        ResultData resultData = new ResultData();
        log.warn("参数校验失败：{}",e.getBindingResult().getAllErrors()
        .get(0).getDefaultMessage());
        resultData.setSuccess(false);
        resultData.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return resultData;
    }

}
