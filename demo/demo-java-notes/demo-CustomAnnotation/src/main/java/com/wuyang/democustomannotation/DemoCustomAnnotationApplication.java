package com.wuyang.democustomannotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.wuyang.democustomannotation.mapper")
@EnableAspectJAutoProxy
public class DemoCustomAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCustomAnnotationApplication.class, args);
    }

}