package com.ydlclass.aspect;

import com.ydlclass.annotation.Log;
import com.ydlclass.constant.Constants;
import com.ydlclass.core.RedisTemplate;
import com.ydlclass.entity.YdlOperLog;
import com.ydlclass.service.YdlOperLogService;
import com.ydlclass.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author itnanls(微信)
 * 我们的服务： 一路陪跑，顺利就业
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Resource
    private YdlOperLogService ydlOperLogService;

    @Resource
    private RedisTemplate redisTemplate;

//    @Resource
//    private ExecutorService executorService;

//    private BeanFactory beanFactory;
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        this.beanFactory = beanFactory;
//    }


    @AfterReturning("@annotation(operLog)")
    public void afterReturning(JoinPoint joinPoint, Log operLog) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        System.out.println("main----" + Thread.currentThread().getName());
        YdlOperLog ydlOperLog = createOperLog(joinPoint, request, operLog, null);
        //这个方法是异步的
        ydlOperLogService.insert(ydlOperLog);
        log.info("{},执行了【{}】，操作",ydlOperLog.getOperName(),ydlOperLog.getTitle());

//        LogAspect logAspect = beanFactory.getBean(this.getClass());
//        logAspect.logHandler(ydlOperLog);
    }

    @AfterThrowing(value = "@annotation(operLog)", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Log operLog, Exception exception) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        YdlOperLog ydlOperLog = createOperLog(joinPoint, request, operLog, exception);
        ydlOperLogService.insert(ydlOperLog);
        log.error("{},执行了【{}】，操作",ydlOperLog.getOperName(),ydlOperLog.getTitle(),exception);
    }


    /**
     * 真正执行添加日志的方法
     *
     * @param joinPoint 了解点
     * @param request   请求
     * @param log       日志注解
     * @param exception 异常信息
     */
//    private void logHandler(JoinPoint joinPoint, HttpServletRequest request, Log log, Exception exception) {
//        当大量的请求，会导致线程数突增
//        new Thread(()->{
//
//        }).start();
//        // 不行，每次请求，都会创建一个线程池
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.execute(()->{
//
//        });
    // 提交任务
//        // 1、根据现场信息，封装日志实例
//        YdlOperLog ydlOperLog = new YdlOperLog();
//        ydlOperLog.setTitle(log.title());
//        ydlOperLog.setBusinessType(log.businessType());
//        if (exception != null) {
//            ydlOperLog.setErrormsg(exception.getMessage().length() > 1000 ?
//                    exception.getMessage().substring(0, 1000) : exception.getMessage());
//            ydlOperLog.setStatus(500);
//        } else {
//            ydlOperLog.setStatus(200);
//        }
//        // request当中获取的一些信息
//        ydlOperLog.setOperIp(request.getRemoteAddr());
//        ydlOperLog.setRequestMethod(request.getMethod());
//        // 注意空指针的问题
//        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);
//        System.out.println("---" + token);
//        if (AuthUtil.getLoginUser(redisTemplate) != null && AuthUtil.getLoginUser(redisTemplate).getYdlUser() != null) {
//            ydlOperLog.setOperName(AuthUtil.getLoginUser(redisTemplate).getYdlUser().getUserName());
//        }
//        ydlOperLog.setOperUrl(request.getRequestURI());
//
//        //获取执行的方法
//        String methodName = joinPoint.getSignature().getName();
//        ydlOperLog.setMethod(methodName);
//        ydlOperLog.setOpertime(new Date());
//        executorService.execute(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            // 保存日志对象
//            ydlOperLogService.insert(ydlOperLog);
//        });
//    }

    /**
     * 真正执行添加日志的方法
     */
//    @Async("ydl-logger")
//    public void logHandler(YdlOperLog ydlOperLog) {
//        ydlOperLogService.insert(ydlOperLog);
//
//    }
    private YdlOperLog createOperLog(JoinPoint joinPoint, HttpServletRequest request, Log log, Exception exception) {
        System.out.println("log----" + Thread.currentThread().getName());
        // 1、根据现场信息，封装日志实例
        YdlOperLog ydlOperLog = new YdlOperLog();
        ydlOperLog.setTitle(log.title());
        ydlOperLog.setBusinessType(log.businessType());
        if (exception != null) {
            ydlOperLog.setErrormsg(exception.getMessage().length() > 1000 ?
                    exception.getMessage().substring(0, 1000) : exception.getMessage());
            ydlOperLog.setStatus(500);
        } else {
            ydlOperLog.setStatus(200);
        }
        // request当中获取的一些信息
        ydlOperLog.setOperIp(request.getRemoteAddr());
        ydlOperLog.setRequestMethod(request.getMethod());
        // 注意空指针的问题
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);
        System.out.println("---" + token);
        if (AuthUtil.getLoginUser(redisTemplate) != null && AuthUtil.getLoginUser(redisTemplate).getYdlUser() != null) {
            ydlOperLog.setOperName(AuthUtil.getLoginUser(redisTemplate).getYdlUser().getUserName());
        }
        ydlOperLog.setOperUrl(request.getRequestURI());

        //获取执行的方法
        String methodName = joinPoint.getSignature().getName();
        ydlOperLog.setMethod(methodName);
        ydlOperLog.setOpertime(new Date());
        return ydlOperLog;
    }


}
