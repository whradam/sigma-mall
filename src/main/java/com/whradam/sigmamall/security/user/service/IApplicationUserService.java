package com.whradam.sigmamall.security.user.service;

/**
 * 给业务模块使用的服务，如调取当前登录的用户名/id等
 */
public interface IApplicationUserService {
    /**
     * 返回当前登录的用户名
     */
    public String getCurrentUserAccount();
    /**
     * 返回当前登录的买家id(当调用customer专属的方法时候可使用)
     */
    public Integer getCurrentCustomerId();
}
