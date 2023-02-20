package com.whradam.sigmamall.common.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 记录Info级的日志，描述各个部件调用的起始，参数与结果
 * Error级日志由GlobalExceptionHandler统一处理
 */

@Component
@Aspect
//@Async
public class AopLogger {
    //将logger所在的类设定为当前类
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    //设定切入点为controller, service, dao中的所有方法
    @Pointcut("execution(* com.whradam.sigmamall.controller.*.*(..)) " +
            "|| execution(* com.whradam.sigmamall.service.impl.*.*(..)) " +
            "|| execution(* com.whradam.sigmamall.dao.*.*(..))")
    public void pointCut(){}

    //记录方法执行开始与参数
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        logger.info("Logger from: " + joinPoint.getTarget().getClass().getName());
        logger.info("Executing: " + joinPoint.getSignature().getName());
        logger.info("Params: " + Arrays.toString(joinPoint.getArgs()));
    }

    //记录方法执行结束与结果
    @AfterReturning(value = "pointCut()", returning = "result")
    public void after(JoinPoint joinPoint, Object result){
        //当结果为空或者方法为void
        String message = result==null? "":result.toString();
        logger.info("Success: " + joinPoint.getTarget().getClass().getName());
        logger.info("Result: "+ message);
    }

}
