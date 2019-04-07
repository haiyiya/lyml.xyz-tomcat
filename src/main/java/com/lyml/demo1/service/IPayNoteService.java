package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.PayNote;

import java.util.List;
import java.util.Map;

public interface IPayNoteService extends IService<PayNote> {
    Page<PayNote> listByObj(Page<PayNote> page, PayNote payNote);
    List<Map<String, Object>> calcByPayGroupId(Long payGroupId);
}
