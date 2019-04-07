package com.lyml.demo1.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.PayGroup;
import com.lyml.demo1.service.IPayGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/payGroup")
public class PayGroupController extends BaseController {
    @Autowired
    private IPayGroupService payGroupService;

    @RequestMapping("")
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/payGroup");
        return modelAndView;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        return renderSuccess(payGroupService.selectList(new EntityWrapper<PayGroup>().orderBy("sort")));
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@ModelAttribute PayGroup payGroup) {
        if (payGroupService.selectCount(new EntityWrapper<PayGroup>().eq("name", payGroup.getName())) > 0) {
            return renderError("名称重复");
        }

        if (payGroup.getId() == null) {
            payGroup.setCreateTime(new Date());
        }

        if (payGroupService.insertOrUpdate(payGroup)) {
            return renderSuccess(payGroup);
        } else {
            return renderError("保存失败");
        }
    }

    @RequestMapping("/edit{id}")
    @ResponseBody
    public Object save(@PathVariable Long id) {
        return renderSuccess(payGroupService.selectById(id));
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        if (payGroupService.deleteBatchIds(Arrays.asList(ids.split(";")))) {
            return renderSuccess("删除成功");
        } else {
            return renderError("删除失败");
        }
    }
}
