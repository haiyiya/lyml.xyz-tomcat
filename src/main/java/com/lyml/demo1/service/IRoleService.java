package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface IRoleService extends IService<Role> {
    public Page<Role> listByObj(Page<Role> page, Role role);
    List<Role> listByUserId(@Param(value="userId") Serializable userId);
    Role selectOneByUserId(@Param(value="userId") Serializable userId);
}
