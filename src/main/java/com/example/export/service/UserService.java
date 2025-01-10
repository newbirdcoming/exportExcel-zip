package com.example.export.service;

import com.example.export.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.export.domain.dto.UserAddOrderDto;

import java.util.List;

/**
* @author Admin
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-01-09 19:24:57
*/
public interface UserService extends IService<User> {

    List<UserAddOrderDto> pageList();
}
