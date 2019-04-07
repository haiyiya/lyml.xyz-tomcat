package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.RoleUser;

import java.util.List;

public interface RoleUserMapper extends SuperMapper<RoleUser> {
    List<RoleUser> listByObj(Pagination page, RoleUser roleUser);
}
