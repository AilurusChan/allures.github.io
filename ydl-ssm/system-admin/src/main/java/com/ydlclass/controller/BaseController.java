package com.ydlclass.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ydlclass.constant.Constants;
import com.ydlclass.core.RedisTemplate;
import com.ydlclass.entity.YdlLoginUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

/**
 * @author itnanls(微信)
 * 我们的服务： 一路陪跑，顺利就业
 */
public class BaseController {

    @Resource
    RedisTemplate redisTemplate;

    protected YdlLoginUser getLoginUser(){

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取首部信息的token
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);

        if (token == null) {
            throw new RuntimeException("当前用户未登录！");
        }
        Set<String> keys = redisTemplate.keys(Constants.TOKEN_PREFIX + "*" + token);
        if (keys== null || keys.size() == 0){
            throw new RuntimeException("当前用户未登录！");
        }
        String tokenKey = (String)keys.toArray()[0];
        // 3、使用token去redis中查看，有没有对应的loginUser
        return redisTemplate.getObject(tokenKey, new TypeReference<>() {});
    }

}
