package com.lyml.demo1.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.Dic;

public interface DicMapper extends SuperMapper<Dic> {
    List<Dic> listByObj(Pagination page,Dic dic);

}