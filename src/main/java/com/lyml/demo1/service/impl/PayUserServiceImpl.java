package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.PayUserMapper;
import com.lyml.demo1.model.PayUser;
import com.lyml.demo1.service.IPayUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//服务实现类需要Service注解
@Service
public class PayUserServiceImpl extends ServiceImpl<PayUserMapper, PayUser> implements IPayUserService {
    @Autowired
    PayUserMapper roleUserMapper;

    @Override
    public Page<PayUser> listByObj(Page<PayUser> page, PayUser payUser) {
        page.setRecords(roleUserMapper.listByObj(page,payUser));
        return page;
    }
}
