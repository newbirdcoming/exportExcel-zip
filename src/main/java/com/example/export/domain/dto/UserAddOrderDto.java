package com.example.export.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.example.export.domain.OrderTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName UserAddOrderDto$
 * @description TODO
 * @date 2025/1/8 22:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddOrderDto extends OrderTable {
    /**
     * 用户ID
     */
    @ColumnWidth(10)
    @ExcelProperty(value={"","id"})
    private String id;

    /**
     * 用户名
     */
    @ColumnWidth(20)
    @ExcelProperty(value={"","用户名"})
    private String username;

    /**
     * 邮箱
     */
    @ColumnWidth(20)
    @ExcelProperty(value={"","邮箱"})
    private String email;

    /**
     * 创建时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value={"${titleDate}","创建时间"})
    private Date createdAt;
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */

}
