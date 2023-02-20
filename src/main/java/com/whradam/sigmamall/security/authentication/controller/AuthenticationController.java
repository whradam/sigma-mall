package com.whradam.sigmamall.security.authentication.controller;


import com.whradam.sigmamall.security.authentication.entity.AuthenticationRequest;
import com.whradam.sigmamall.security.authentication.entity.AuthenticationResponse;
import com.whradam.sigmamall.security.authentication.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证登录接口，此接口无需认证
 * 分Customer买家, Vendor卖家, Admin管理员三个接口
 * 返回token
 */
@RestController
@RequestMapping("/api/v1/login")
public class AuthenticationController {

  @Autowired
  private IAuthenticationService authenticationService;

  @PostMapping("/customer")
  public AuthenticationResponse authenticateCustomer(@RequestBody AuthenticationRequest request) {
    return authenticationService.authenticateCustomer(request);
  }

  @PostMapping("/vendor")
  public AuthenticationResponse authenticateVendor(@RequestBody AuthenticationRequest request) {
    return authenticationService.authenticateVendor(request);
  }

  @PostMapping("/admin")
  public AuthenticationResponse authenticateAdmin(@RequestBody AuthenticationRequest request) {
    return authenticationService.authenticateAdmin(request);
  }



}
