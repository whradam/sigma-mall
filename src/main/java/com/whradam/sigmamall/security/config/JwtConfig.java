package com.whradam.sigmamall.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * JWT配置类
 * 定义密钥
 * 定义token过期时间(毫秒)
 */
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
    private String secretKey;
    private Integer tokenExpirationAfterMillis;

    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getTokenExpirationAfterMillis() {
        return tokenExpirationAfterMillis;
    }

    public void setTokenExpirationAfterMillis(Integer tokenExpirationAfterMillis) {
        this.tokenExpirationAfterMillis = tokenExpirationAfterMillis;
    }



}
