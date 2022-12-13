package com.example.demo5bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class Demo5botApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo5botApplication.class, args);
    }

}