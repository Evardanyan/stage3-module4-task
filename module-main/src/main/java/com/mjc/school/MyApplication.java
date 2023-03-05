package com.mjc.school;

import com.mjc.school.utils.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EntityScan(basePackages = "com.mjc.school.repository")
public class MyApplication implements CommandLineRunner {

    @Autowired
    private Menu menu;

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        menu.start();
    }


}
