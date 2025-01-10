package com.example.export.controller;

import com.example.export.domain.Result;
import com.example.export.domain.User;
import com.example.export.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName UserController$
 * @description TODO
 * @date 2025/1/8 22:30
 **/



@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/add")
    @ApiOperation(tags = "用户接口",value = "新增用户")
    Result<?> add(@RequestBody User user) throws Exception {
        try {
            userService.save(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return Result.success(user,"新增成功");
    }

    @ApiOperation(tags = "用户接口",value = "根据id获取")
    @GetMapping("/getById/{id}")
    Result<?> getById(@PathVariable(value ="id")Integer id){
        User byId = userService.getById(id);
        if(byId!=null)
            return Result.success(userService.getById(id));
        return Result.error("500","不存在");
    }



}
