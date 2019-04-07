package com.lyml.demo1.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lyml.demo1.mapper.UserMapper;
import com.lyml.demo1.model.User;
import com.lyml.demo1.service.IUserService;
import org.springframework.stereotype.Service;

//服务实现类需要Service注解
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
