package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.PayNoteMapper;
import com.lyml.demo1.model.PayNote;
import com.lyml.demo1.service.IPayNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//服务实现类需要Service注解
@Service
public class PayNoteServiceImpl extends ServiceImpl<PayNoteMapper, PayNote> implements IPayNoteService {
    @Autowired
    PayNoteMapper messageMapper;

    @Override
    public Page<PayNote> listByObj(Page<PayNote> page, PayNote payNote) {
        page.setRecords(messageMapper.listByObj(page,payNote));
        return page;
    }

    @Override
    public List<Map<String, Object>> calcByPayGroupId(Long payGroupId) {
        return messageMapper.calcByPayGroupId(payGroupId);
    }
}
