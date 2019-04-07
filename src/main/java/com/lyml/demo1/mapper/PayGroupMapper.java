package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.PayGroup;

import java.util.List;

public interface PayGroupMapper extends SuperMapper<PayGroup> {
    List<PayGroup> listByObj(Pagination page, PayGroup payGroup);
}
