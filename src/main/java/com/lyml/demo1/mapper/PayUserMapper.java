package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.PayUser;

import java.util.List;

public interface PayUserMapper extends SuperMapper<PayUser> {
    List<PayUser> listByObj(Pagination page, PayUser payUser);
}
