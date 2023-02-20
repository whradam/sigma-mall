package com.whradam.sigmamall.security.user.service;

import com.whradam.sigmamall.security.config.ApplicationUserConfig;
import com.whradam.sigmamall.security.user.dao.ApplicationUserDao;
import com.whradam.sigmamall.security.user.entity.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 实现UserDetailService
 * 定义查找用户的方式
 */
@Service("ApplicationUserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationUserDao applicationUserDao;

    @Autowired
    ApplicationUserConfig applicationUserConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        //不同角色的前缀
        final String customerInitial = applicationUserConfig.getCustomerInitial();
        final String vendorInitial = applicationUserConfig.getVendorInitial();
        final String adminInitial = applicationUserConfig.getVendorInitial();
        //由AuthenticationService中传传输过来的用户名的前缀
        final String usernameInitial = applicationUserConfig.getUsernameInitial(username);
        //去除了前缀的原用户名
        final String rawUsername = applicationUserConfig.getRawUsername(username);

        //根据前缀调用dao中不同的方法，返回UserDetails
        //招不到用户名则抛出异常
        if (usernameInitial.equals(vendorInitial)){
            ApplicationUser applicationUser = applicationUserDao
                    .findVendorByUsername(rawUsername)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(String.format("Username %s not found", username))
                    );
            applicationUser.setUsername(username);
            userDetails = applicationUser;
        }else if(usernameInitial.equals(customerInitial)){
            ApplicationUser applicationUser = applicationUserDao
                    .findCustomerByUsername(rawUsername)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(String.format("Username %s not found", username))
                    );
            applicationUser.setUsername(username);
            userDetails = applicationUser;
        }else if(usernameInitial.equals(adminInitial)){
            ApplicationUser applicationUser = applicationUserDao
                    .findVendorByUsername(rawUsername)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(String.format("Username %s not found", username))
                    );
            applicationUser.setUsername(username);
            userDetails = applicationUser;
        }else{
            throw new UsernameNotFoundException("Unknown Error Occur in Authentication");
        }
        return userDetails;
    }



}
