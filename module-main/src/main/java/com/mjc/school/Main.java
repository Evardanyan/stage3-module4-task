package com.mjc.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.format.DateTimeFormatter;

@EnableWebMvc
@SpringBootApplication
//@EnableJpaAuditing
@EntityScan(basePackages = "com.mjc.school.repository")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


}
