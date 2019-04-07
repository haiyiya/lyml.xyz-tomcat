package com.lyml.demo1.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lyml.demo1.SuperMapper;
import com.lyml.demo1.model.Role;
import com.lyml.demo1.model.User;

import java.util.List;

public interface UserMapper extends SuperMapper<User> {
    List<Role> listByObj(Pagination page, Role role);
}
