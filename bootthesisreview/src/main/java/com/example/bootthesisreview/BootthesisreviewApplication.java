package com.example.bootthesisreview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.example.bootthesisreview.mapper")
public class BootthesisreviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootthesisreviewApplication.class, args);
    }

}
