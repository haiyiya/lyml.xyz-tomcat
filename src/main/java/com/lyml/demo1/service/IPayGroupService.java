package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.PayGroup;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface IPayGroupService extends IService<PayGroup> {
    public Page<PayGroup> listByObj(Page<PayGroup> page, PayGroup payGroup);
}
