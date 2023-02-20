package com.whradam.sigmamall.security.user.dao;

import com.whradam.sigmamall.security.user.entity.ApplicationUser;

import java.util.Optional;

/**
 * 调用对应的dao，
 * 根据用户名调出相应的用户(customer/vendor/admin)
 * 转为ApplicationUser返回
 */
public interface ApplicationUserDao {

    Optional<ApplicationUser> findCustomerByUsername(String username);
    Optional<ApplicationUser> findVendorByUsername(String username);
    Optional<ApplicationUser> findAdminByUsername(String username);


}
