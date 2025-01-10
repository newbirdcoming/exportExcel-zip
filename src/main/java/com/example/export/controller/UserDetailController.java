package com.example.export.controller;

import com.example.export.domain.Result;
import com.example.export.domain.User;
import com.example.export.domain.UserDetail;
import com.example.export.service.UserDetailService;
import com.example.export.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName UserDetailController$
 * @description TODO
 * @date 2025/1/8 22:31
 **/

@RestController
@RequestMapping("/userdetails")
@Api(tags = "用户信息接口")
public class UserDetailController {



    @Autowired
    UserService userService;

    @Autowired
    UserDetailService userDetailService;

    @PostMapping("/add")
    @ApiOperation(tags = "用户信息接口",value = "新增用户信息")
    Result<?> add(@RequestBody UserDetail userDetail){
        String userId = userDetail.getUserId();
        User byId = userService.getById(userId);
        if(byId==null)
            return Result.error("用户不存在");
        userDetailService.save(userDetail);
        return Result.success("新增成功");
    }



}
