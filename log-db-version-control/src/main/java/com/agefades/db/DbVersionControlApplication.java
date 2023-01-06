package com.agefades.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 数据库版本控制应用启动类
 *
 * @author DuChao
 * @since 2023/1/5 10:48
 */
@SpringBootApplication
public class DbVersionControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbVersionControlApplication.class, args);
    }
}
