package com.example.export.mapper;

import com.example.export.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.export.domain.dto.UserAddOrderDto;

import java.util.List;

/**
* @author Admin
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-01-09 19:24:57
* @Entity com.example.export.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    List<UserAddOrderDto> pageList();
}




