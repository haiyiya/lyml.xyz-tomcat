package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.PayUser;

public interface IPayUserService extends IService<PayUser> {
    public Page<PayUser> listByObj(Page<PayUser> page, PayUser payUser);
}
