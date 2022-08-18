package com.rokai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.rokai.crm.settings.dao")
@SpringBootApplication
public class CRMProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CRMProjectApplication.class, args);

    }

}
