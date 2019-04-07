package com.lyml.demo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.plugins.Page;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Message;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {
    @Autowired
    private IMessageService messageService;

    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        Page<Message> page = new Page<>(getIntPar("page"), getIntPar("limit"));
        Message message = JSONObject.parseObject(getStringPar("data"), new TypeReference<Message>() {
        });
        if (message == null) message = new Message();
        message.setUserId(getUser().getId());
        page = messageService.listByObj(page, message);
        return renderSuccess(page);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@ModelAttribute Message message) {
        User user = getUser();
        message.setUserId(user.getId());
        message.setUserName(user.getUsername());
        message.setPid(0L);
        message.setCreateTime(new Date());

        if (messageService.insertOrUpdate(message)) {
            return renderSuccess(message);
        } else {
            return renderError("保存失败");
        }
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        if (messageService.deleteBatchIds(Arrays.asList(ids.split(";")))) {
            return renderSuccess("删除成功");
        } else {
            return renderError("删除失败");
        }
    }
}
