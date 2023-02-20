package com.whradam.sigmamall.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 加密器配置类
 * 定义加密长度
 * 配置加密算法: 创建账户时，需要调用passwordEncoder进行加密
 */
@Configuration
@ConfigurationProperties("security.password")
public class PasswordEncoderConfig {
    private Integer passwordEncoderLength;

    public PasswordEncoderConfig() {
    }

    public Integer getPasswordEncoderLength() {
        return passwordEncoderLength;
    }

    public void setPasswordEncoderLength(Integer passwordEncoderLength) {
        this.passwordEncoderLength = passwordEncoderLength;
    }


    /**
     * 配置加密算法
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordEncoderLength);
    }
}
