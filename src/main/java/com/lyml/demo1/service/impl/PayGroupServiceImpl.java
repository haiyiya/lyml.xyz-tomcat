package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.PayGroupMapper;
import com.lyml.demo1.model.PayGroup;
import com.lyml.demo1.service.IPayGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//服务实现类需要Service注解
@Service
public class PayGroupServiceImpl extends ServiceImpl<PayGroupMapper, PayGroup> implements IPayGroupService {
    @Autowired
    PayGroupMapper roleMapper;

    @Override
    public Page<PayGroup> listByObj(Page<PayGroup> page, PayGroup role) {
        page.setRecords(roleMapper.listByObj(page,role));
        return page;
    }
}
