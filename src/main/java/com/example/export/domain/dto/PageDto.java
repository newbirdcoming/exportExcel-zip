package com.example.export.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName PageDto$
 * @description TODO
 * @date 2025/1/8 23:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {

        // 当前页码
        private int currentPage;

        // 每页显示的记录数
        private int pageSize;

        // 总记录数
        private long totalRecords;

        // 总页数
        private int totalPages;

        // 当前页的数据列表
        private List<T> records;
}
