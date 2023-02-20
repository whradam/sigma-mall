package com.whradam.sigmamall.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回结果统一封装
 */
@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice {
    //返回true, 调用beforeBodyWrite()方法
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        //如果响应结果是JSON数据类型
        if(selectedContentType.equalsTypeAndSubtype(MediaType.APPLICATION_JSON)){
            //配置HTTP响应结果，使之与ResponseEntity的状态码统一
            //处理异常情况下GlobalExceptionHandler的返回值
            if (body instanceof ResponseEntity){
                serverHttpResponse.setStatusCode(HttpStatus.valueOf(((ResponseEntity) body).getStatus()));
            }
            //统一封装返回结果，controller不需要返回ResponseEntity
            //如果出错，将由全局异常进行处理
            //使用security的情况下，404将变为403因为无论存在与否都要authenticate
            else {
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                return ResponseEntityFactory.success(body);
            }
        }
        return body;
    }
}