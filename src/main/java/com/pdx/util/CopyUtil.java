package com.pdx.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-07 17:56
 * @Description 复制工具类
 */
public class CopyUtil {


    /**
     * 单体复制
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source,Class<T> clazz){
        if (source == null){
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /**
     * 列表复制
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List source,Class<T> clazz){
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)){
            for (Object c : source) {
                T obj = copy(c,clazz);
                target.add(obj);
            }
        }
        return target;
    }



}
