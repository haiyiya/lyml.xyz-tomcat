package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.Message;

import java.util.List;

public interface IMessageService extends IService<Message> {
    public Page<Message> listByObj(Page<Message> page, Message message);
}
