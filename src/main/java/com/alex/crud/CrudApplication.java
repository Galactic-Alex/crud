package com.alex.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
        SpringApplication.run(CrudApplication.class, args);
    }

}
