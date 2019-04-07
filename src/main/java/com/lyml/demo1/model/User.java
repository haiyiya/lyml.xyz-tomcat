package com.lyml.demo1.model;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//表名
@TableName("user")
public class User extends SuperEntity<User> implements UserDetails {
    private String username;
    private String password;
    private String header;

    @TableField(exist = false)
    private Collection<GrantedAuthority> auths;

    @TableField(exist = false)
    private String group;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Collection<GrantedAuthority> getAuths() {
        return auths;
    }

    public void setAuths(Collection<GrantedAuthority> auths) {
        this.auths = auths;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    public void setAuthorities(Collection<GrantedAuthority> auths){
        this.auths = auths;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
