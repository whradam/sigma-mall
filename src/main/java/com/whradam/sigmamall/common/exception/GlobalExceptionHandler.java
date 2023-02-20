package com.whradam.sigmamall.common.exception;

import com.whradam.sigmamall.common.response.ResponseEntity;
import com.whradam.sigmamall.common.response.ResponseEntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.whradam.sigmamall.common.response.ResponseCode.*;

//todo: 测试并找出未捕捉异常，尤其security相关
/**
 * 全局异常配置
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 处理程序员抛出的自定义异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity customException(CustomException e) {
        //400, 500 由业务造成已妥善处理的异常不需要持久化
        //将用于debug、需要后期处理的异常信息持久化处理，并统一封装给前端
        int serviceErrorHttpStatus = ERROR_INTERNAL_SERVER_SPECIAL.getHttpStatus();
        if(e.getHttpStatus()==serviceErrorHttpStatus){
            //异常信息记录在日志
            logger.error(e.toString());
            //将具体异常信息隐藏包装返回给前端
            return ResponseEntityFactory.error(new CustomException(ERROR_INTERNAL_SERVER));
        }
        return ResponseEntityFactory.error(e);
    }

    /**
     * 处理程序员在程序中未能捕获的/未知异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity exception(Exception e) {
        //将未知异常信息持久化处理，方便运维人员处理
        logger.error(e.toString());
        //将具体异常信息隐藏包装返回给前端
        return ResponseEntityFactory.error(new CustomException(ERROR_INTERNAL_SERVER));
    }

    /**
     * Annotation validation 捕获的异常处理
     * @param e BindException (super)或 MethodArgumentNotValidException (sub)
     * @return ResponseEntity
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        return ResponseEntityFactory.error(new CustomException(
                ERROR_INVALID_REQUEST.getHttpStatus(),
                ERROR_INVALID_REQUEST.getCode(),
                ERROR_INVALID_REQUEST.getMessage()+fieldError.getDefaultMessage()
        ));
    }

    /**
     * HttpMessageNotReadableException 捕获的异常处理
     * 如空param等
     * @param e HttpMessageNotReadableException
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity handleInvalidRequest(HttpMessageNotReadableException e){
        return ResponseEntityFactory.error(new CustomException(
                ERROR_INVALID_REQUEST.getHttpStatus(),
                ERROR_INVALID_REQUEST.getCode(),
                ERROR_INVALID_REQUEST.getMessage()+e.getMessage()
        ));
    }

    /**
     * 不支持的参数类型异常
     * HttpMediaTypeNotSupportedException
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity handleTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ResponseEntityFactory.error(
                new CustomException(
                        ERROR_INVALID_REQUEST.getHttpStatus(),
                        ERROR_INVALID_REQUEST.getCode(),
                        ERROR_INVALID_REQUEST.getMessage()+e.getMessage()
                )
        );
    }

    /** org.springframework.utils.Assert 捕获的异常
     *  Assert.isTrue(input >= 0,"您输入的数据不符合业务逻辑，请确认后重新输入！");
     *  如果不满足条件就抛出IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntityFactory.error(
                new CustomException(
                        ERROR_INVALID_REQUEST.getHttpStatus(),
                        ERROR_INVALID_REQUEST.getCode(),
                        ERROR_INVALID_REQUEST.getMessage()+e.getMessage()
                )
        );
    }

}
