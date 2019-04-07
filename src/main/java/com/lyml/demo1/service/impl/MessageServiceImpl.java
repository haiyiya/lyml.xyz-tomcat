package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.MessageMapper;
import com.lyml.demo1.mapper.UserMapper;
import com.lyml.demo1.model.Message;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//服务实现类需要Service注解
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public Page<Message> listByObj(Page<Message> page, Message message) {
        page.setRecords(messageMapper.listByObj(page,message));
        return page;
    }
}
