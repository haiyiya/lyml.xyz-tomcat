package com.lyml.demo1.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lyml.demo1.model.Dic;

import java.io.Serializable;
import java.util.List;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IDicService extends IService<Dic> {
    public Page<Dic> listByObj(Page<Dic> page, Dic dic);
    public List<Dic> listByPid(String pid);
    public String getNameById(Serializable id);
    public String getIdByName(String name);
    public boolean deleteById(Serializable id);
    public boolean deleteBatchIds(List<? extends Serializable> ids);
    public boolean deleteBatchIds(Serializable[] ids);
}