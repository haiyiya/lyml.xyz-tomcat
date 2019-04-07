package com.lyml.demo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Dic;
import com.lyml.demo1.model.PayNote;
import com.lyml.demo1.model.PayUser;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payNote")
public class PayNoteController extends BaseController {
    @Autowired
    private IPayNoteService payNoteService;
    @Autowired
    private IPayUserService payUserService;
    @Autowired
    private IPayGroupService payGroupService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDicService dicService;

    @RequestMapping("")
    public ModelAndView user(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mobile/payNote");

        User user = (User) authentication.getPrincipal();
        List<PayUser> payUsers = payUserService.selectList(new EntityWrapper<PayUser>().eq("userId", user.getId()));
        if(payUsers.size() > 0){
            modelAndView.addObject("payGroup", payGroupService.selectById(payUsers.get(0).getGroupId()));
        }

        modelAndView.addObject("user", getUser());
        modelAndView.addObject("types", dicService.selectList(new EntityWrapper<Dic>().eq("pid", 36L).orderBy("sort")));
        return modelAndView;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        Page<PayNote> page = new Page<>(getIntPar("page"), getIntPar("limit"));
        PayNote payNote = JSONObject.parseObject(getStringPar("data"), new TypeReference<PayNote>() {
        });
        if (payNote == null) payNote = new PayNote();
        payNote.setUserId(getUser().getId());
        page = payNoteService.listByObj(page, payNote);
        return renderSuccess(page);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@ModelAttribute PayNote payNote) {
        User user = getUser();

        if (payNote.getId() == null) {
            payNote.setUserId(user.getId());
            payNote.setUserName(user.getUsername());
            payNote.setCreateTime(new Date());
        }

        if (payNoteService.insertOrUpdate(payNote)) {
            payNote.setUser(userService.selectById(payNote.getUserId()));
            return renderSuccess(payNote);
        } else {
            return renderError("保存失败");
        }
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        if (payNoteService.deleteBatchIds(Arrays.asList(ids.split(";")))) {
            return renderSuccess("删除成功");
        } else {
            return renderError("删除失败");
        }
    }

    @RequestMapping(value = "/calc/{payGroupId}")
    @ResponseBody
    public Object calc(@PathVariable Long payGroupId) {
        List<Map<String, Object>> calcList = payNoteService.calcByPayGroupId(payGroupId);
        if (calcList.size() == 2) {
            float diff = (Float.valueOf(calcList.get(0).get("payOut").toString()) + Float.valueOf(calcList.get(0).get("payTo").toString()) * 2
                    - Float.valueOf(calcList.get(1).get("payOut").toString()) - Float.valueOf(calcList.get(1).get("payTo").toString()) * 2) / 2;

            if(diff > 0.01){
                calcList.get(0).put("payShould", "应付 " + String.valueOf(new BigDecimal(diff).setScale(1, RoundingMode.UP)));
            }else if(diff < 0.01){
                calcList.get(1).put("payShould", "应付 " + String.valueOf(new BigDecimal(- diff).setScale(1, RoundingMode.UP)));
            }
        }
        return renderSuccess(calcList);
    }
}
