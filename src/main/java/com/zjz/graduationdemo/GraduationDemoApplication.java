package com.zjz.graduationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GraduationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDemoApplication.class, args);
    }

}
