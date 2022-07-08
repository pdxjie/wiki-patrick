//package com.pdx.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.support.spring.PropertyPreFilters;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author 派 大 星
// * @website https://blog.csdn.net/Gaowumao
// * @Date 2022-07-08 12:25
// * @Description
// */
//@Component
//@Aspect
//@Slf4j
//public class LogAspect {
//
//    /**
//     * 定义一个切点
//     */
//    @Pointcut("execution(public * com.pdx.*.controller..*Controller.*(..))")
//    public void controllerPointCut(){}
//
//    @Before("controllerPointCut()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable{
//        //开始打印请求日志
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        Signature signature = joinPoint.getSignature();
//        String name = signature.getName();
//
//        //打印请求信息
//        log.info("---------LogFilter is Start---------");
//        log.info("请求地址：{}{}",request.getRequestURL().toString(),request.getMethod());
//        log.info("远程地址：{}",request.getRemoteAddr());
//
//        long startTime = System.currentTimeMillis();
//
//
//        //打印请求参数
//        Object[] args = joinPoint.getArgs();
//        Object[] arguments = new Object[args.length];
//        for (int i = 0; i < args.length; i++) {
//            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile){
//                continue;
//            }
//            arguments[i] = args[i];
//        }
//        //排除字段，敏感字段或者太长的字段不显示
//        String[] excludeProperties ={"password","file"};
//        PropertyPreFilters filters = new PropertyPreFilters();
//        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
//        excludefilter.addExcludes(excludeProperties);
//        log.info("请求参数：{}", JSONObject.toJSONString(arguments,excludefilter));
//    }
//
//    @Around("controllerPointCut()")
//    public Object adAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        long startTime = System.currentTimeMillis();
//        Object proceed = proceedingJoinPoint.proceed();
//        //排除敏感字段
//        String[] excludeProperties = {"password","file"};
//        PropertyPreFilters filters = new PropertyPreFilters();
//        PropertyPreFilters.MySimplePropertyPreFilter preFilter = filters.addFilter();
//        preFilter.addExcludes(excludeProperties);
//        log.info("返回结果：{}",JSONObject.toJSONString(proceed,preFilter));
//        log.info("-----------------LogFilter is End 耗时：{} ms-----------------",System.currentTimeMillis() - startTime);
//        return proceed;
//    }
//}
