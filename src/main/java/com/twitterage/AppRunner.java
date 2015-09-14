package com.twitterage;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class AppRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppRunner.class, args);
    }
}