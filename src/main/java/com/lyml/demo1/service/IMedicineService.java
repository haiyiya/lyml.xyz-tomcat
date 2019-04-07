package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.Medicine;

public interface IMedicineService extends IService<Medicine> {
    public Page<Medicine> listByObj(Page<Medicine> page, Medicine medicine);
}
