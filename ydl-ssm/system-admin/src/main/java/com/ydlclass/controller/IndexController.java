package com.ydlclass.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ydlclass.annotation.Repeat;
import com.ydlclass.entity.YdlLoginUser;
import com.ydlclass.entity.YdlOperLog;
import com.ydlclass.entity.YdlUser;
import com.ydlclass.service.YdlUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author itnanls(微信)
 * 我们的服务： 一路陪跑，顺利就业
 */
@RestController
@Slf4j
public class IndexController {

    @Resource
    private YdlUserService userService;

    @PostMapping("login")
    @Repeat(5)
    public ResponseEntity<YdlLoginUser> login(@RequestBody @Validated YdlUser ydlUser,BindingResult bindingResult){
        // 1、处理不合法的数据
        System.out.println(ydlUser.getUserName()+"< > ＜＞");
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        allErrors.forEach( error -> log.error("登陆时用户校验失败：{}",error.getDefaultMessage()));
        if(allErrors.size() > 0){
            return ResponseEntity.status(500).build();
        }

        // 2、 执行登陆逻辑
        YdlLoginUser ydlLoginUser = null;
        try {
            ydlLoginUser = userService.login(ydlUser.getUserName(),ydlUser.getPassword());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().body(ydlLoginUser);
    }


    @GetMapping("logout")
    public ResponseEntity<String> logout(){
        try {
            userService.logout();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().body("退出成功");
    }

}
