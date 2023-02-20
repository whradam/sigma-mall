package com.whradam.sigmamall.security.authentication.service;

import com.whradam.sigmamall.security.authentication.entity.AuthenticationRequest;
import com.whradam.sigmamall.security.authentication.entity.AuthenticationResponse;

/**
 * 用户认证登录服务
 * 返回token的封装类
 */
public interface IAuthenticationService {
    /**
     *  认证前给用户名加入相对应的前缀，以便区分customer/vendor/admin角色权限
     *  前端请求只需提供raw username，数据库也只存储raw username
     *  经过处理后，将使用"前缀+用户名"从UserDetailService调用认证信息
     */
    AuthenticationResponse authenticateCustomer(AuthenticationRequest request);
    AuthenticationResponse authenticateVendor(AuthenticationRequest request);
    AuthenticationResponse authenticateAdmin(AuthenticationRequest request);


}
