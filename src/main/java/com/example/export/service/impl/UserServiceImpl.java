package com.example.export.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.export.domain.User;
import com.example.export.domain.dto.UserAddOrderDto;
import com.example.export.service.UserService;
import com.example.export.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Admin
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-01-09 19:24:57
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public List<UserAddOrderDto> pageList() {
        List<UserAddOrderDto> list=userMapper.pageList();
        return list;
    }
}




