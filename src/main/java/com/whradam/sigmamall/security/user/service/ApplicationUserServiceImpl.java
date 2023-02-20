package com.whradam.sigmamall.security.user.service;

import com.whradam.sigmamall.dao.CustomerDao;
import com.whradam.sigmamall.entity.entityGenerated.Customer;
import com.whradam.sigmamall.security.config.ApplicationUserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * 给业务模块使用的服务，如调取当前登录的用户名/id等
 */
@Service
public class ApplicationUserServiceImpl implements IApplicationUserService {
    @Autowired
    ApplicationUserConfig applicationUserConfig;
    @Autowired
    CustomerDao customerDao;

    /**
     * 返回当前登录的用户名
     */
    @Override
    public String getCurrentUserAccount(){
        String username;
        //Get username with user type identifier
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        username = authentication.getName();

        //Get raw username
        username = applicationUserConfig.getRawUsername(username);
        return username;
    }

    /**
     * 返回当前登录的买家id(当调用customer专属的方法时候可使用)
     */
    @Override
    public Integer getCurrentCustomerId() {
        String account;
        String usernameInitial;
        //Get username with user type identifier
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        account = authentication.getName();
        //Get username initial
        usernameInitial = applicationUserConfig.getUsernameInitial(account);
        if (usernameInitial!=applicationUserConfig.getCustomerInitial()){
            return null;
        }
        //Get raw username
        account = applicationUserConfig.getRawUsername(account);

        Customer customer = customerDao.selectByAccount(account);
        return customer.getId();
    }
}
