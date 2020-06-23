package com.ratta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.ratta.mapper"})
public class SpringbootShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroApplication.class, args);
    }

}
