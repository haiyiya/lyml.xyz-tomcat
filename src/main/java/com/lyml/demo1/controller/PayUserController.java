package com.lyml.demo1.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.PayGroup;
import com.lyml.demo1.model.PayUser;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IPayGroupService;
import com.lyml.demo1.service.IPayUserService;
import com.lyml.demo1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/payUser")
public class PayUserController extends BaseController {
    @Autowired
    private IPayUserService payUserService;
    @Autowired
    private IPayGroupService payGroupService;
    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        return renderSuccess(payUserService.selectList(new EntityWrapper<PayUser>().eq("groupId",getLongPar("groupId"))));
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@ModelAttribute PayUser payUser) {
        if (payUserService.selectCount(new EntityWrapper<PayUser>().eq("groupId", payUser.getGroupId()).eq("userId",payUser.getUserId())) > 0) {
            renderError("该用户已添加");
        }

        if (payUser.getId() == null) {
            PayGroup payGroup = payGroupService.selectById(payUser.getGroupId());
            User user = userService.selectById(payUser.getUserId());
            payUser.setGroupName(payGroup.getName());
            payUser.setUserName(user.getUsername());
        }

        if (payUserService.insertOrUpdate(payUser)) {
            return renderSuccess(payUser);
        } else {
            return renderError("保存失败");
        }
    }

    @RequestMapping("/edit{id}")
    @ResponseBody
    public Object save(@PathVariable Long id) {
        return renderSuccess(payUserService.selectById(id));
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        if (payUserService.deleteBatchIds(Arrays.asList(ids.split(";")))) {
            return renderSuccess("删除成功");
        } else {
            return renderError("删除失败");
        }
    }
}
