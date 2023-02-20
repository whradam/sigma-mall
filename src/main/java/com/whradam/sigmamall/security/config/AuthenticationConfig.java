package com.whradam.sigmamall.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配置AuthenticationManager以及AuthenticationProvider
 */
@Configuration
public class AuthenticationConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 配置AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        //配置认证信息从数据库抓取
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //配置加密器
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        //配置用户调用服务
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    /**
     * 配置AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
