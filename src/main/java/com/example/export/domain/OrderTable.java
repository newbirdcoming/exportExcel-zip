package com.example.export.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 * @TableName order
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_table")
public class OrderTable implements Serializable {



    /**
     * 订单ID
     */
    @ColumnWidth(20)
    @ExcelProperty(value={"${titleMonth}","订单ID"})
    private String id;

    /**
     * 用户ID
     */

    @ExcelIgnore
    private String userId;

    /**
     * 下单时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value={"","下单时间"})
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime orderDate;

    /**
     * 订单总金额
     */
    @ColumnWidth(20)
    @ExcelProperty(value={"","订单总金额"})
    private BigDecimal totalAmount;

    /**
     * 订单状态
     */
    @ColumnWidth(20)
    @ExcelProperty(value={"","订单状态"})
    private String status;

    private static final long serialVersionUID = 1L;
}