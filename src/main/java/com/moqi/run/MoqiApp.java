package com.moqi.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.moqi.controller",
        "com.moqi.config",
        "com.moqi.service",
})
public class MoqiApp {

    public static void main(String[] args) {
        SpringApplication.run(MoqiApp.class, args);
    }

}
