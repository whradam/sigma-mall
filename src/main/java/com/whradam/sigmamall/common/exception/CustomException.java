package com.whradam.sigmamall.common.exception;

import com.whradam.sigmamall.common.response.ResponseCode;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {
  private final int httpStatus;
  private final String errorCode;
  private final String message;

  /**
   * 编写代码抛出异常时应由此方法构建自定义异常抛出
   * @param responseCode 异常信息由ResponseCode所规定
   */
  public CustomException(ResponseCode responseCode) {
    this.httpStatus = responseCode.getHttpStatus();
    this.errorCode = responseCode.getCode();
    this.message = responseCode.getMessage();
  }

  /**
   * 全构造函数
   * 用于配置非手动抛出的的异常
   * @param httpStatus 返回给前端的http状态码
   * @param errorCode 错误码
   * @param message 错误信息
   */
  public CustomException(int httpStatus, String errorCode, String message){
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
    this.message = message;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }


}
