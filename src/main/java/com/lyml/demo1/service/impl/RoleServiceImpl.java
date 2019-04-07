package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.RoleMapper;
import com.lyml.demo1.model.Role;
import com.lyml.demo1.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

//服务实现类需要Service注解
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Page<Role> listByObj(Page<Role> page, Role role) {
        page.setRecords(roleMapper.listByObj(page,role));
        return page;
    }

    @Override
    public List<Role> listByUserId(Serializable userId) {
        return roleMapper.listByUserId(userId);
    }

    @Override
    public Role selectOneByUserId(Serializable userId) {
        return roleMapper.selectOneByUserId(userId);
    }
}
