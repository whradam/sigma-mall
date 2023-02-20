package com.whradam.sigmamall.common.response;

import com.whradam.sigmamall.common.exception.CustomException;

import static com.whradam.sigmamall.common.response.ResponseCode.SUCCESS;

public class ResponseEntityFactory {

    //请求成功的响应，不带查询数据（用于删除、修改、新增接口）
    public static ResponseEntity success(){
        return new ResponseEntity(SUCCESS.getHttpStatus(), SUCCESS.getCode(), SUCCESS.getMessage());
    }

    //请求成功的响应，带有查询数据（用于数据查询接口）
    public static ResponseEntity success(Object result){
        return new ResponseEntity(SUCCESS.getHttpStatus(), SUCCESS.getCode(), SUCCESS.getMessage(),result);
    }
    //请求出现异常时的响应数据封装
    public static ResponseEntity error(CustomException e) {
        return new ResponseEntity(e.getHttpStatus(),e.getErrorCode(),e.getMessage());
    }
    //请求出现异常时的响应数据封装
    public static ResponseEntity error(int httpStatus, String errorCode, String errorMessage) {
        return new ResponseEntity(httpStatus, errorCode,errorMessage);
    }







}
