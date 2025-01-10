package com.example.export.controller;

import com.example.export.domain.OrderTable;
import com.example.export.domain.Result;
import com.example.export.domain.dto.PageDto;
import com.example.export.domain.dto.UserAddOrderDto;
import com.example.export.service.OrderTableService;
import com.example.export.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName OrderController$
 * @description TODO
 * @date 2025/1/8 22:31
 **/



@RestController
@RequestMapping("/user")
@Api(tags = "订单接口")
public class OrderController {

    @Autowired
    private UserService userService;


    @Autowired
    private OrderTableService orderService;



    @ApiOperation(tags = "订单接口", value = "新增订单")
    @PostMapping("/addOrder")
    @Transactional
    Result<?>  addOrder(@RequestBody OrderTable order){
        //ID自动生成
        order.setId(null);
        orderService.save(order);
        return Result.success("插入成功");
    }



    @ApiOperation(tags = "订单接口",value = "分页查询")
    @GetMapping("/pageList")
    @Transactional
    Result pageList(Integer pageSize,Integer pageNumber){
        List<UserAddOrderDto> list=userService.pageList();
        PageDto<UserAddOrderDto> userAddOrderDtoPageDto = new PageDto<>();
        userAddOrderDtoPageDto.setPageSize(pageSize);
        userAddOrderDtoPageDto.setCurrentPage(pageNumber);
        int size = list.size();
        int totalPage=size/pageSize+1;
        int start=(pageNumber-1)*pageSize;
        int end=pageNumber*pageSize-1;
        if(end>=size){
            end=size-1;
            if(start>=size) {
                userAddOrderDtoPageDto.setRecords(null);
                userAddOrderDtoPageDto.setTotalPages(totalPage);
                userAddOrderDtoPageDto.setTotalRecords(size);
                return Result.success(userAddOrderDtoPageDto);
            }
        }
        userAddOrderDtoPageDto.setRecords(list.subList(start,end));
        userAddOrderDtoPageDto.setTotalPages(totalPage);
        userAddOrderDtoPageDto.setTotalRecords(size);
        return Result.success(userAddOrderDtoPageDto);
    }


    @ApiOperation(tags = "订单接口",value = "查询所有")
    @GetMapping("/getAll")
    @Transactional
    Result getAll() {
        List<UserAddOrderDto> list=userService.pageList();
        return Result.success(list);
    }

}
