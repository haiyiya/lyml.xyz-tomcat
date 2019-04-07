package com.lyml.demo1;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lyml.demo1.model.Role;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IRoleService;
import com.lyml.demo1.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TestUserDetailsService implements UserDetailsService {
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;

    private static final Logger logger = LogManager.getLogger(TestUserDetailsService.class);

    /**
     * 根据用户名获取用户 - 用户的角色、权限等信息，由spring security完成验证
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            userDetails = userService.selectOne(new EntityWrapper<User>().eq("username", username));
            //Collection<GrantedAuthority> authList = getAuthorities();
            if (userDetails == null) {
                throw new UsernameNotFoundException("找不到该账户信息！");
            } else {
                User user = (User) userDetails;
                user.setAuthorities(getAuthorities(user.getId()));
                return userDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

    /**
     * 获取角色和权限
     *
     * @return
     */
    private Collection<GrantedAuthority> getAuthorities(Serializable id) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        List<Role> roles = roleService.listByUserId(id);
        for (Role r : roles) {
            authList.add(new SimpleGrantedAuthority("ROLE_" + r.getCode()));
        }
        return authList;
    }
}
