package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.RoleUserMapper;
import com.lyml.demo1.model.RoleUser;
import com.lyml.demo1.service.IRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//服务实现类需要Service注解
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser> implements IRoleUserService {
    @Autowired
    RoleUserMapper roleUserMapper;

    @Override
    public Page<RoleUser> listByObj(Page<RoleUser> page, RoleUser roleUser) {
        page.setRecords(roleUserMapper.listByObj(page,roleUser));
        return page;
    }
}
