package com.whradam.sigmamall.common.response;

public enum ResponseCode {

    /**
     * 通用成功状态码
     * 状态码: 200
     * 自定义成功状态码: 00000
     */
    SUCCESS(200,"00000","SUCCESS"),

    /**
     * Controller层异常
     * 状态码: 400
     * 错误码: 10000-19999
     */
    //Controller模块，由框架捕获的用户参数错误，用于全局异常处理中统一封装
    ERROR_INVALID_REQUEST(400,"10000","INVALID REQUEST"),
    //手动捕获的具体异常，可直接返回给前端
    ERROR_RUD_FAIL_ID_NULL(400,"10001","ID CANNOT BE NULL"),
    ERROR_DELETE_FAIL_ID_FORMAT_INVALID(400,"10002","ERROR_DELETE_FAIL_ID_FORMAT_INVALID"), //删除失败，
    ERROR_UPDATE_FAIL_PASSWORD_NULL(400,"10003","ERROR_UPDATE_FAIL_PASSWORD_NULL"),
    /**
     * Service层异常
     * 状态码: 500
     * 错误码: 20000-29999
     */
    //Service模块未捕获的异常/系统未知异常错误，用于全局异常处理中统一封装
    ERROR_INTERNAL_SERVER(500,"20000","INTERNAL SERVER ERROR"),
    //手动捕获的具体异常，可直接返回给前端
    ERROR_ADD_FAIL_USERNAME_EXISTS(500,"20001", "ERROR_ADD_FAIL_USERNAME_EXISTS"),
    ERROR_DELETE_UPDATE_FAIL_ID_NOT_FOUND(500,"20002","ERROR_DELETE_UPDATE_FAIL_ID_NOT_FOUND"),

    /**
     * 用于debug、需要后期处理的的异常
     * 状态码: 555
     * 错误码: 30000-39999
     */
    //通用特殊异常
    ERROR_INTERNAL_SERVER_SPECIAL(555,"30000","ERROR_INTERNAL_SERVER_SPECIAL"),
    //手动捕获的具体异常
    //对象转换异常
    ERROR_UTILS_ENTITY_CONVERT_FAIL(555,"30001","ERROR_UTILS_ENTITY_CONVERT_FAIL");


    private final int httpStatus;
    private final String code;
    private final String message;


    ResponseCode(int httpStatus, String code, String message) {
        this.httpStatus=httpStatus;
        this.code=code;
        this.message=message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
