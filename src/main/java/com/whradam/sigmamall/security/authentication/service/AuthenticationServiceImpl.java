package com.whradam.sigmamall.security.authentication.service;


import com.whradam.sigmamall.security.authentication.entity.AuthenticationRequest;
import com.whradam.sigmamall.security.authentication.entity.AuthenticationResponse;
import com.whradam.sigmamall.security.config.ApplicationUserConfig;
import com.whradam.sigmamall.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
/**
 * 用户认证登录服务
 * 返回token的封装类
 */
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  ApplicationUserConfig applicationUserConfig;

  /**
   * 认证用户
   * @param request 认证请求
   * @return 认证结果
   */
  private AuthenticationResponse authenticateUser(AuthenticationRequest request){
    //调用authenticationManager，根据用户名密码产生的token认证用户
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
    //认证成功后根据当前登录的用户资料生成jwtToken
    //由于认证用户前需要为用户名加入前缀，因此不可直接使用request中的用户名
    String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(request.getUsername()));
    return new AuthenticationResponse(jwtToken);
  }

  /**
   *  认证前给用户名加入相对应的前缀，以便区分customer/vendor/admin角色权限
   *  前端请求只需提供raw username，数据库也只存储raw username
   *  经过处理后，将使用"前缀+用户名"从UserDetailService调用认证信息
   */
  @Override
  public AuthenticationResponse authenticateCustomer(AuthenticationRequest request) {
    //Set customer username identifier
    request.setUsername(applicationUserConfig.getCustomerIdentifier()+request.getUsername());
    //Authenticate user
    return authenticateUser(request);
  }

  @Override
  public AuthenticationResponse authenticateVendor(AuthenticationRequest request) {
    //Set vendor username identifier
    request.setUsername(applicationUserConfig.getVendorIdentifier() +request.getUsername());
    //Authenticate user
    return authenticateUser(request);
  }

  @Override
  public AuthenticationResponse authenticateAdmin(AuthenticationRequest request) {
    //Set admin username identifier
    request.setUsername(applicationUserConfig.getAdminIdentifier() +request.getUsername());
    //Authenticate user
    return authenticateUser(request);
  }
}
