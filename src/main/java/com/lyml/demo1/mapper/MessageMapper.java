package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.Message;

import java.util.List;

public interface MessageMapper extends SuperMapper<Message> {
    List<Message> listByObj(Pagination page, Message message);
}
