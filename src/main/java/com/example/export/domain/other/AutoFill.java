package com.example.export.domain.other;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName AutoFill$
 * @description TODO
 * @date 2025/1/9 16:18
 **/
@Slf4j
@Component
public class AutoFill implements MetaObjectHandler {


    //insert注入
    @Override
    public void insertFill(MetaObject metaObject) {
//        createdAt为String类型
//        this.strictInsertFill(metaObject, "createdAt", String.class, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        createdAt为LocalDateTime类型
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "orderDate", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "flag", Integer.class, 0);
    }


    //update注入
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "number", Integer.class, 10);
    }
}