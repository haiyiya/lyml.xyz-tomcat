package com.lyml.demo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.plugins.Page;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.model.Medicine;
import com.lyml.demo1.service.IMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.Arrays;

@Controller
@RequestMapping("/medicine")
public class MedicineController extends BaseController {

    @Autowired
    IMedicineService medicineService;


    @RequestMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/medicine");
        return modelAndView;
    }

    @RequestMapping(value="/list")
    @ResponseBody
    public Object list() {
        Page<Medicine> page = new Page<Medicine>(getIntPar("page"), getIntPar("limit"));
        Medicine medicine = JSONObject.parseObject(getStringPar("data"), new TypeReference<Medicine>() {});
        Page<Medicine> list =  medicineService.listByObj(page, medicine);
        return renderSuccess(list.getTotal(),list.getRecords());
    }

    @RequestMapping(value="/searchList")
    @ResponseBody
    public Object searchList(@RequestParam String search) throws Exception {
        Page<Medicine> page = new Page<Medicine>(getIntPar("page"), getIntPar("limit", 10));
        Medicine medicine = new Medicine();
        medicine.setSearch(search);
        page =  medicineService.listByObj(page, medicine);
        return renderSuccess(page.getTotal(), page.getRecords());
    }

    @RequestMapping(value="/details")
    @ResponseBody
    public Object details(@RequestParam Long id) {
        Medicine medicine = medicineService.selectById(id);
        return renderSuccess(medicine);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@ModelAttribute Medicine medicine) {
        if(medicineService.insertOrUpdate(medicine)){
            return renderSuccess("添加成功");
        }else{
            return renderError("添加失败");
        }
    }

    @RequestMapping(value="/del")
    @ResponseBody
    public Object del() {
        String ids = getStringPar("ids");
        medicineService.deleteBatchIds(Arrays.asList(ids.split(";")));
        return renderSuccess("删除成功");
    }
}