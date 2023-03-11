package com.mjc.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EntityScan(basePackages = "com.mjc.school.repository")
public class MyRESTApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyRESTApplication.class, args);
    }

}
