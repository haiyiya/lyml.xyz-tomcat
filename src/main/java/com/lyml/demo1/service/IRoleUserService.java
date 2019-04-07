package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.RoleUser;

public interface IRoleUserService extends IService<RoleUser> {
    public Page<RoleUser> listByObj(Page<RoleUser> page, RoleUser roleUser);
}
