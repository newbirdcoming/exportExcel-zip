package com.example.export.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.export.domain.OrderTable;
import com.example.export.service.OrderTableService;
import com.example.export.mapper.OrderTableMapper;
import org.springframework.stereotype.Service;

/**
* @author Admin
* @description 针对表【order_table(订单表)】的数据库操作Service实现
* @createDate 2025-01-09 19:24:57
*/
@Service
public class OrderTableServiceImpl extends ServiceImpl<OrderTableMapper, OrderTable>
    implements OrderTableService {

}




