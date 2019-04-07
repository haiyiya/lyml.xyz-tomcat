package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.DicMapper;
import com.lyml.demo1.model.Dic;
import com.lyml.demo1.service.IDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, Dic> implements IDicService {

    @Autowired
    DicMapper mapper;

    public Page<Dic> listByObj(Page<Dic> page, Dic dic) {
        page.setRecords(mapper.listByObj(page, dic));
        return page;
    }

    public List<Dic> listByPid(String pid){
        List<Dic> list = mapper.selectList(new EntityWrapper<Dic>().eq("pid", pid).orderBy("sort"));
        for(Dic dc :list){
            if(mapper.selectCount(new EntityWrapper<Dic>().eq("pid", dc.getId()))>0){
                dc.setIsParent("true");
            }else{
                dc.setIsParent("false");
            }
        }
        return list;
    }

    public String getNameById(Serializable id){
        if(null==mapper.selectById(id)){
            return "";
        }else{
            return mapper.selectById(id).getName();
        }
    }


    @Override
    public String getIdByName(String name) {
        Dic dc = selectOne(new EntityWrapper<Dic>().eq("name", name));
        if(null!=dc){
            return ""+dc.getId();
        }else{
            return "0";
        }
    }

    @Override
    public boolean deleteById(Serializable id) {
        if(selectCount(new EntityWrapper<Dic>().eq("pid",id))>0){
            for(Dic dc : selectList(new EntityWrapper<Dic>().eq("pid",id))){
                deleteById(dc.getId());
            }
        }
        return super.deleteById(Long.parseLong(id.toString()));
    }

    @Override
    public boolean deleteBatchIds(List<? extends Serializable> ids) {
        for(Serializable id : ids){
            deleteById(id);
        }
        return true;
    }

    @Override
    public boolean deleteBatchIds(Serializable[] ids) {
        for(Serializable id : ids){
            deleteById(id);
        }
        return true;
    }
}