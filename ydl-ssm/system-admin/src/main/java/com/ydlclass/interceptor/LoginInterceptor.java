package com.ydlclass.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ydlclass.configuration.CustomObjectMapper;
import com.ydlclass.constant.Constants;
import com.ydlclass.core.RedisTemplate;
import com.ydlclass.entity.YdlLoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @author itnanls(微信)
 * 我们的服务： 一路陪跑，顺利就业
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CustomObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断有没有Authorization这个请求头，拿到首部信息的Authorization的值
        ResponseEntity<String> res = ResponseEntity.status(401).body("Bad Credentials!");
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);

        if (token == null) {
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(res));
            return false;
        }
        //        String tokenKey = Constants.TOKEN_PREFIX + request.getHeader("username")+":"+token;
        Set<String> keys = redisTemplate.keys(Constants.TOKEN_PREFIX + "*" + token);
        if (keys== null || keys.size() == 0){
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(res));
            return false;
        }
        String tokenKey = (String)keys.toArray()[0];
        // 3、使用token去redis中查看，有没有对应的loginUser
        YdlLoginUser ydlLoginUser = redisTemplate.getObject(tokenKey, new TypeReference<>() {
        });
        if (ydlLoginUser == null) {
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(res));
            return false;
        }
        // 给token续命
        redisTemplate.expire(tokenKey,Constants.TOKEN_TIME);

        return true;
    }
}
