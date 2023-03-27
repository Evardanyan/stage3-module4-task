package com.mjc.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication

@EntityScan(basePackages = "com.mjc.school.repository")
public class NewsRESTApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsRESTApplication.class, args);
    }

}
