package com.whradam.sigmamall.security.user.entity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色类
 */
public enum ApplicationUserRole {
    CUSTOMER(List.of(ApplicationUserAuthorities.AUTHORITY_CUSTOMER)),
    VENDOR(List.of(ApplicationUserAuthorities.AUTHORITY_VENDOR)),
    ADMIN(List.of(ApplicationUserAuthorities.AUTHORITY_ADMIN));


    private final List<ApplicationUserAuthorities> permissions;

    ApplicationUserRole(List<ApplicationUserAuthorities> permissions) {
        this.permissions = permissions;
    }


    public List<ApplicationUserAuthorities> getPermissions() {
        return permissions;
    }

    /**
     * 加了这个后 每个role都有authorities了
     */
    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getAuthorities()))
                .collect(Collectors.toList());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
