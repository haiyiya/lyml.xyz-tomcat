package com.lyml.demo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.plugins.Page;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Dic;
import com.lyml.demo1.service.IDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dic")
public class DicController extends BaseController {

    @Autowired
    IDicService dicService;


    @RequestMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/dic");
        return modelAndView;
    }

    @RequestMapping(value="/list")
    @ResponseBody
    public Object list() {
        Page<Dic> page = new Page<Dic>(getIntPar("page"), getIntPar("limit"));
        Dic dic = JSONObject.parseObject(getStringPar("data"), new TypeReference<Dic>() {});
        Page<Dic> list =  dicService.listByObj(page, dic);
        return renderSuccess(list.getTotal(),list.getRecords());
    }

    @RequestMapping(value="/tree")
    @ResponseBody
    public Object tree() {
        String id = getStringPar("id");
        if(id==null||id.length()==0){
            return dicService.listByPid("0");
        }else{
            return dicService.listByPid(id);
        }
    }

    @RequestMapping(value="/add")
    @ResponseBody
    public Object add() {
        Dic dic = JSONObject.parseObject(getStringPar("data"), new TypeReference<Dic>() {});
        if(dicService.insertOrUpdate(dic)){
            return renderSuccess("添加成功");
        }else{
            return renderError("添加失败");
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@ModelAttribute Dic dic) {
        if(dicService.insertOrUpdate(dic)){
            return renderSuccess("添加成功");
        }else{
            return renderError("添加失败");
        }
    }

    @RequestMapping(value="/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        dicService.deleteBatchIds(ids.split(";"));
        return renderSuccess("删除成功");
    }
}