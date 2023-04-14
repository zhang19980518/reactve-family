package com.joert.dbcio.config;

import org.springframework.security.core.GrantedAuthority;


/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
public class SimpleGrantedAuthority implements GrantedAuthority {
    private String authority;

    public SimpleGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

