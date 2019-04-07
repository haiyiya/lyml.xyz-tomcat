package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.MedicineMapper;
import com.lyml.demo1.model.Medicine;
import com.lyml.demo1.service.IMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//服务实现类需要Service注解
@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements IMedicineService {
    @Autowired
    MedicineMapper roleUserMapper;

    @Override
    public Page<Medicine> listByObj(Page<Medicine> page, Medicine medicine) {
        page.setRecords(roleUserMapper.listByObj(page,medicine));
        return page;
    }
}
