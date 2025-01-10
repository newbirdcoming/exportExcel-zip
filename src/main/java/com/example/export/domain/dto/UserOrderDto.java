package com.example.export.domain.dto;

import com.example.export.domain.OrderTable;
import com.example.export.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName UserOrderDto$
 * @description TODO
 * @date 2025/1/8 22:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDto {
    User user;

    List<OrderTable> orders;
}
