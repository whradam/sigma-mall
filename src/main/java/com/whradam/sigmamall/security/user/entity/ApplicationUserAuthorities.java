package com.whradam.sigmamall.security.user.entity;

/**
 * 权限类
 */
public enum ApplicationUserAuthorities {
    AUTHORITY_CUSTOMER("customer"),
    AUTHORITY_VENDOR("vendor"),
    AUTHORITY_ADMIN("admin");

    private final String authorities;

    ApplicationUserAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getAuthorities() {
        return authorities;
    }
}
