package com.whradam.sigmamall.common.response;

/**
 * 统一封装返回结果
 */
public class ResponseEntity {
    //HTTP状态码
    private int status;
    //系统定义的错误码或成功状态码
    String code;
    //给前端返回的信息
    String message;
    //具体结果
    Object data;

    //不带data
    public ResponseEntity(int httpStatus, String code, String message) {
        this.status = httpStatus;
        this.code = code;
        this.message = message;
    }
    //All argument constructor
    public ResponseEntity(int httpStatus, String code, String message, Object data) {
        this.status = httpStatus;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //pojo少了getter/setter, 返回成response body时 会出现406 org.springframework.web.HttpMediaTypeNotAcceptableException:
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) { this.status = status;  }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
