package com.example.export.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户详情表
 * @TableName user_detail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements Serializable {
    /**
     * 用户详情ID，等于用户ID
     */
    private String id;

    /**
     * 用户ID，唯一且等于用户表ID
     */
    private String userId;

    /**
     * 地址
     */
    private String address;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private LocalDateTime birthday;

    /**
     * 联系电话
     */
    private String phone;

    private static final long serialVersionUID = 1L;


}