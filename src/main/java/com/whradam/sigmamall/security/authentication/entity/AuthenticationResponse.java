package com.whradam.sigmamall.security.authentication.entity;

/**
 * 登录成功给前端的返回值
 * token的封装类
 */

public class AuthenticationResponse {
  private String token;

  public AuthenticationResponse() {
  }

  public AuthenticationResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
