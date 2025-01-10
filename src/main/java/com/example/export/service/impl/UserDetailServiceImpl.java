package com.example.export.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.export.domain.UserDetail;
import com.example.export.service.UserDetailService;
import com.example.export.mapper.UserDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author Admin
* @description 针对表【user_detail(用户详情表)】的数据库操作Service实现
* @createDate 2025-01-09 19:24:57
*/
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail>
    implements UserDetailService{

}




