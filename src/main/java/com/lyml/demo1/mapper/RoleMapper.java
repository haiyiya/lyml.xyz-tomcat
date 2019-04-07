package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface RoleMapper extends SuperMapper<Role> {
    List<Role> listByObj(Pagination page, Role role);
    List<Role> listByUserId(@Param(value="userId") Serializable userId);
    Role selectOneByUserId(@Param(value="userId") Serializable userId);
}
