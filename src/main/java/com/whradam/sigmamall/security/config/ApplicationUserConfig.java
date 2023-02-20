package com.whradam.sigmamall.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用户名前缀配置类
 * 为用户角色设置不同的前缀
 * 定义前缀与原用户名的分隔符
 */
@ConfigurationProperties("security.user")
public class ApplicationUserConfig {
    private String usernameSplitter;
    private String customerInitial;
    private String vendorInitial;
    private String adminInitial;

    public ApplicationUserConfig() {
    }

    //getter & setter
    public String getUsernameSplitter() {
        return usernameSplitter;
    }

    public void setUsernameSplitter(String usernameSplitter) {
        this.usernameSplitter = usernameSplitter;
    }

    public String getCustomerInitial() {
        return customerInitial;
    }

    public void setCustomerInitial(String customerInitial) {
        this.customerInitial = customerInitial;
    }

    public String getVendorInitial() {
        return vendorInitial;
    }

    public void setVendorInitial(String vendorInitial) {
        this.vendorInitial = vendorInitial;
    }

    public String getAdminInitial() {
        return adminInitial;
    }

    public void setAdminInitial(String adminInitial) {
        this.adminInitial = adminInitial;
    }

    //工具辅助方法
    public String getCustomerIdentifier(){
        return customerInitial+usernameSplitter;
    }

    public String getVendorIdentifier(){
        return vendorInitial + usernameSplitter;
    }

    public String getAdminIdentifier(){
        return adminInitial + usernameSplitter;
    }


    public String getUsernameInitial(String username){
        return username.split(usernameSplitter)[0];
    }

    public String getRawUsername(String username){
        String usernameInitial = getUsernameInitial(username);
        return username.substring(usernameInitial.length()+usernameSplitter.length());
    }




}
